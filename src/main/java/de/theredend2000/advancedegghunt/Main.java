package de.theredend2000.advancedegghunt;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.cryptomorin.xseries.XMaterial;
import de.theredend2000.advancedegghunt.bstats.Metrics;
import de.theredend2000.advancedegghunt.commands.AdvancedEggHuntCommand;
import de.theredend2000.advancedegghunt.configurations.PluginConfig;
import de.theredend2000.advancedegghunt.listeners.*;
import de.theredend2000.advancedegghunt.managers.*;
import de.theredend2000.advancedegghunt.managers.eggmanager.EggDataManager;
import de.theredend2000.advancedegghunt.managers.eggmanager.EggHidingManager;
import de.theredend2000.advancedegghunt.managers.eggmanager.EggManager;
import de.theredend2000.advancedegghunt.managers.eggmanager.PlayerEggDataManager;
import de.theredend2000.advancedegghunt.managers.inventorymanager.eggrewards.RarityManager;
import de.theredend2000.advancedegghunt.managers.inventorymanager.eggrewards.global.GlobalPresetDataManager;
import de.theredend2000.advancedegghunt.managers.inventorymanager.eggrewards.individual.IndividualPresetDataManager;
import de.theredend2000.advancedegghunt.util.embed.EmbedCreator;
import de.theredend2000.advancedegghunt.placeholderapi.PlaceholderExtension;
import de.theredend2000.advancedegghunt.util.*;
import de.theredend2000.advancedegghunt.util.enums.LeaderboardSortTypes;
import de.theredend2000.advancedegghunt.util.messages.MessageManager;
import de.theredend2000.advancedegghunt.util.saveinventory.DatetimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class Main extends JavaPlugin {

    private static Main plugin;
    private DatetimeUtils datetimeUtils;
    private Map<String, Long> refreshCooldown;
    private ArrayList<Player> placeEggsPlayers;
    private HashMap<Player, Integer> playerAddCommand;
    private ArrayList<ArmorStand> showedArmorstands;
    public YamlConfiguration messages;
    public File messagesData;
    private HashMap<Player, LeaderboardSortTypes> sortTypeLeaderboard;
    private PluginConfig pluginConfig;
    private CooldownManager cooldownManager;
    private EggDataManager eggDataManager;
    private EggManager eggManager;
    private SoundManager soundManager;
    private ExtraManager extraManager;
    private PlayerEggDataManager playerEggDataManager;
    private RequirementsManager requirementsManager;
    private PermissionManager permissionManager;
    private IndividualPresetDataManager individualPresetDataManager;
    private GlobalPresetDataManager globalPresetDataManager;
    private MessageManager messageManager;
    private RarityManager rarityManager;
    private EmbedCreator embedCreator;
    private ProtocolManager protocolManager;
    private EggHidingManager eggHidingManager;
    public static String PREFIX = "";
    public static boolean setupDefaultCollection;
    @Override
    public void onEnable() {
        plugin = this;
        setupConfigs();
        setupDefaultCollection = false;
        PREFIX = HexColor.color(ChatColor.translateAlternateColorCodes('&', pluginConfig.getPrefix()));
        Metrics metrics = new Metrics(this, 19495);
        refreshCooldown = new HashMap<String, Long>();
        placeEggsPlayers = new ArrayList<>();
        showedArmorstands = new ArrayList<>();
        playerAddCommand = new HashMap<>();
        sortTypeLeaderboard = new HashMap<>();
        initManagers();
        getCommand("advancedegghunt").setExecutor(new AdvancedEggHuntCommand());
        initListeners();
        datetimeUtils = new DatetimeUtils();
        cooldownManager = new CooldownManager(this);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage(PREFIX + "§aAdvanced Egg Hunt detected PlaceholderAPI, enabling placeholders.");
            new PlaceholderExtension().register();
            Bukkit.getConsoleSender().sendMessage(PREFIX + "§2§lAll placeholders successfully enabled.");
        }
        getEggManager().convertEggData();
        initData();
        sendCurrentLanguage();
        if(setupDefaultCollection) {
            getRequirementsManager().changeActivity("default", true);
            getRequirementsManager().resetReset("default");
            getGlobalPresetDataManager().loadPresetIntoCollectionCommands(getPluginConfig().getDefaultGlobalLoadingPreset(),"default");
        }
        playerEggDataManager.checkReset();
        eggManager.spawnEggParticle();
        new Converter().convertAllSystems();

        for (Player player : Bukkit.getOnlinePlayers())
            Main.getInstance().getPlayerEggDataManager().createPlayerFile(player.getUniqueId());
    }

    @Override
    public void onDisable() {
        giveAllItemsBack();
        for(ArmorStand a : showedArmorstands){
            a.remove();
        }
    }

    private void initData(){
        List<String > eggCollections = eggDataManager.savedEggCollections();
        List<UUID> playerCollection = eggDataManager.savedPlayers();
        playerEggDataManager.initPlayers();
        Bukkit.getConsoleSender().sendMessage("§2§l" +
                "Loaded data of " + playerCollection.size() + " player(s).");
        eggDataManager.initEggs();
        Bukkit.getConsoleSender().sendMessage("§2§lLoaded data of " + eggCollections.size() + " collection(s).");
        for(String collection : eggCollections)
            eggManager.updateMaxEggs(collection);
    }

    private void initManagers(){
        individualPresetDataManager = new IndividualPresetDataManager(this);
        globalPresetDataManager = new GlobalPresetDataManager(this);
        messageManager = new MessageManager();
        eggDataManager = new EggDataManager(this);
        eggManager = new EggManager();
        soundManager = new SoundManager();
        extraManager = new ExtraManager();
        playerEggDataManager = new PlayerEggDataManager();
        requirementsManager = new RequirementsManager();
        permissionManager = new PermissionManager();
        rarityManager = new RarityManager();
        embedCreator = new EmbedCreator();
        protocolManager = ProtocolLibrary.getProtocolManager();
        eggHidingManager = new EggHidingManager();
        new Checker();
    }

    private void initListeners(){
        new InventoryClickEventListener();
        new InventoryCloseEventListener();
        new BlockPlaceEventListener();
        new BlockBreakEventListener();
        new PlayerInteractEventListener();
        new PlayerInteractItemEvent();
        new Updater(this);
        new PlayerChatEventListener();
        new PlayerConnectionListener();
        new EntityChangeListener();
    }

    private void giveAllItemsBack(){
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            if(placeEggsPlayers.contains(player)){
                eggManager.finishEggPlacing(player);
            }
        }
    }

    private void sendCurrentLanguage(){
        String lang = pluginConfig.getLanguage();
        Bukkit.getConsoleSender().sendMessage(PREFIX + "§7Language §6" + lang + " §7detected. File messages-" + lang + ".yml loaded.");
    }

    private void setupConfigs(){
        pluginConfig = PluginConfig.getInstance(plugin);
    }

    public void saveMessages() {
        try {
            this.messages.save(this.messagesData);
        } catch (IOException e) {
            getLogger().severe(e.getMessage());
        }
    }

    public static XMaterial getMaterial(String materialString) {
        try {
            XMaterial material = XMaterial.valueOf(materialString);
            if (material == null) {
                return XMaterial.BARRIER;
            }
            return material;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("§4Material Error: " + ex);
            return XMaterial.STONE;
        }
    }


    public static Main getInstance() {
        return plugin;
    }

    public PluginConfig getPluginConfig(){
        return pluginConfig;
    }

    public static String getTexture(String texture){
        String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
        texture = prefix + texture;
        return texture;
    }
    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    public static PlayerMenuUtility getPlayerMenuUtility(Player p) {
        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))) {

            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p, playerMenuUtility);

            return playerMenuUtility;
        } else {
            return playerMenuUtilityMap.get(p);
        }
    }

    public ArrayList<Player> getPlaceEggsPlayers() {
        return placeEggsPlayers;
    }
    public Map<String, Long> getRefreshCooldown() {
        return refreshCooldown;
    }

    public DatetimeUtils getDatetimeUtils() {
        return datetimeUtils;
    }

    public ArrayList<ArmorStand> getShowedArmorstands() {
        return showedArmorstands;
    }

    public HashMap<Player, Integer> getPlayerAddCommand() {
        return playerAddCommand;
    }

    public HashMap<Player, LeaderboardSortTypes> getSortTypeLeaderboard() {
        return sortTypeLeaderboard;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public EggDataManager getEggDataManager() {
        return eggDataManager;
    }

    public EggManager getEggManager() {
        return eggManager;
    }

    public ExtraManager getExtraManager() {
        return extraManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public PlayerEggDataManager getPlayerEggDataManager() {
        return playerEggDataManager;
    }

    public static HashMap<Player, PlayerMenuUtility> getPlayerMenuUtilityMap() {
        return playerMenuUtilityMap;
    }

    public RequirementsManager getRequirementsManager() {
        return requirementsManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public IndividualPresetDataManager getIndividualPresetDataManager() {
        return individualPresetDataManager;
    }

    public GlobalPresetDataManager getGlobalPresetDataManager() {
        return globalPresetDataManager;
    }

    public RarityManager getRarityManager() {
        return rarityManager;
    }

    public EmbedCreator getEmbedCreator() {
        return embedCreator;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public EggHidingManager getEggHidingManager() {
        return eggHidingManager;
    }
}
