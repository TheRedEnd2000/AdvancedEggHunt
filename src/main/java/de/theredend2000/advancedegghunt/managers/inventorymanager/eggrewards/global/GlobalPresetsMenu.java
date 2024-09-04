package de.theredend2000.advancedegghunt.managers.inventorymanager.eggrewards.global;

import com.cryptomorin.xseries.XMaterial;
import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.managers.inventorymanager.common.PaginatedInventoryMenu;
import de.theredend2000.advancedegghunt.util.ItemBuilder;
import de.theredend2000.advancedegghunt.util.PlayerMenuUtility;
import de.theredend2000.advancedegghunt.util.messages.MenuMessageKey;
import de.theredend2000.advancedegghunt.util.messages.MessageKey;
import de.theredend2000.advancedegghunt.util.messages.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class GlobalPresetsMenu extends PaginatedInventoryMenu {
    private MessageManager messageManager;
    private Main plugin;
    private String id;
    private String collection;
    public GlobalPresetsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility, "Global Presets", (short) 54);
        this.plugin = Main.getInstance();
        this.messageManager = this.plugin.getMessageManager();

        super.addMenuBorder();
        addMenuBorderButtons();
    }

    public void open(String id, String collection) {
        this.collection = collection;
        this.id = id;

        getInventory().setContents(inventoryContent);
        setMenuItems();

        playerMenuUtility.getOwner().openInventory(getInventory());
    }

    public void addMenuBorderButtons() {
        inventoryContent[49] = new ItemBuilder(XMaterial.BARRIER)
                .setDisplayName("§cClose")
                .build();
        inventoryContent[45] = new ItemBuilder(XMaterial.PLAYER_HEAD)
                .setSkullOwner(Main.getTexture("ODFjOTZhNWMzZDEzYzMxOTkxODNlMWJjN2YwODZmNTRjYTJhNjUyNzEyNjMwM2FjOGUyNWQ2M2UxNmI2NGNjZiJ9fX0="))
                .setDisplayName(menuMessageManager.getMenuItemName(MenuMessageKey.BACK_BUTTON))
                .setLore(menuMessageManager.getMenuItemLore(MenuMessageKey.BACK_BUTTON))
                .build();
    }

    public void setMenuItems() {
        getInventory().setItem(48, new ItemBuilder(XMaterial.PLAYER_HEAD)
                .setLore(menuMessageManager.getMenuItemLore(MenuMessageKey.PREVIOUS_PAGE_BUTTON,"%CURRENT_PAGE%",String.valueOf(page + 1),"%MAX_PAGES%",String.valueOf(getMaxPages())))
                .setDisplayName(menuMessageManager.getMenuItemName(MenuMessageKey.PREVIOUS_PAGE_BUTTON))
                .setSkullOwner(Main.getTexture("ZDU5YmUxNTU3MjAxYzdmZjFhMGIzNjk2ZDE5ZWFiNDEwNDg4MGQ2YTljZGI0ZDVmYTIxYjZkYWE5ZGIyZDEifX19"))
                .build());
        getInventory().setItem(50, new ItemBuilder(XMaterial.PLAYER_HEAD)
                .setLore(menuMessageManager.getMenuItemLore(MenuMessageKey.NEXT_PAGE_BUTTON,"%CURRENT_PAGE%",String.valueOf(page + 1),"%MAX_PAGES%",String.valueOf(getMaxPages())))
                .setDisplayName(menuMessageManager.getMenuItemName(MenuMessageKey.NEXT_PAGE_BUTTON))
                .setSkullOwner(Main.getTexture("NDJiMGMwN2ZhMGU4OTIzN2Q2NzllMTMxMTZiNWFhNzVhZWJiMzRlOWM5NjhjNmJhZGIyNTFlMTI3YmRkNWIxIn19fQ=="))
                .build());

        GlobalPresetDataManager presetDataManager = plugin.getGlobalPresetDataManager();
        ArrayList<String> keys = new ArrayList<>();
        if(presetDataManager.savedPresets().size() >= 1){
            keys.addAll(presetDataManager.savedPresets());
        }else
            getInventory().setItem(22, new ItemBuilder(XMaterial.RED_STAINED_GLASS)
                    .setDisplayName("§4§lNo Presets")
                    .setLore("§7Create new one to select them.")
                    .build());
        if (keys == null || keys.isEmpty()) {
            getInventory().setItem(22, new ItemBuilder(XMaterial.RED_STAINED_GLASS)
                    .setDisplayName("§4§lNo Presets")
                    .setLore("§7Create new one to select them.")
                    .build());
            return;
        }
        for(int i = 0; i < maxItemsPerPage; i++) {
            index = maxItemsPerPage * page + i;
            if(index >= keys.size()) break;
            if (keys.get(index) != null){
                String defaultPreset = plugin.getPluginConfig().getDefaultGlobalLoadingPreset();
                getInventory().addItem(new ItemBuilder(XMaterial.PAPER)
                        .setDisplayName("§b§l" + keys.get(index))
                        .setLore(presetDataManager.getAllCommandsAsLore(keys.get(index), keys.get(index).equals(defaultPreset)))
                        .setCustomId(keys.get(index))
                        .build());
            }
        }
    }

    public int getMaxPages(){
        GlobalPresetDataManager presetDataManager = plugin.getGlobalPresetDataManager();
        ArrayList<String> keys = new ArrayList<>(presetDataManager.savedPresets());
        if(keys.isEmpty()) return 1;
        return (int) Math.ceil((double) keys.size() / maxItemsPerPage);
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        GlobalPresetDataManager presetDataManager = plugin.getGlobalPresetDataManager();
        if(event.getCurrentItem() == null) return;

        for(String presetName : presetDataManager.savedPresets()){
            if (!ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equals(presetName)) {
                continue;
            }
            switch (event.getAction()) {
                case PICKUP_ALL:
                    player.sendMessage(messageManager.getMessage(MessageKey.PRESET_LOADED).replaceAll("%PRESET%", presetName));
                    presetDataManager.loadPresetIntoCollectionCommands(presetName, collection);
                    new GlobalEggRewardsMenu(Main.getPlayerMenuUtility(super.playerMenuUtility.getOwner())).open(id, collection);
                    break;
                case PICKUP_HALF:
                    if (!plugin.getPluginConfig().getDefaultGlobalLoadingPreset().equals(presetName)) {
                        presetDataManager.deletePreset(presetName);
                        player.sendMessage(messageManager.getMessage(MessageKey.PRESET_DELETE).replaceAll("%PRESET%", presetName));
                    } else
                        player.sendMessage(messageManager.getMessage(MessageKey.PRESET_NOT_DELETE_DEFAULT).replaceAll("%PRESET%", presetName));
                    open(id, collection);
                    break;
                case CLONE_STACK:
                    plugin.getPluginConfig().setDefaultGlobalLoadingPreset(presetName);
                    player.sendMessage(messageManager.getMessage(MessageKey.PRESET_DEFAULT).replaceAll("%PRESET%", presetName));
                    open(id, collection);
                    break;
            }
            player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
            return;
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(menuMessageManager.getMenuItemName(MenuMessageKey.PREVIOUS_PAGE_BUTTON))) {
            if (page == 0) {
                player.sendMessage(Main.getInstance().getMessageManager().getMessage(MessageKey.FIRST_PAGE));
                player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventoryFailedSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
            } else {
                page = page - 1;
                this.open(id,collection);
                player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(menuMessageManager.getMenuItemName(MenuMessageKey.NEXT_PAGE_BUTTON))) {
            if (!((index + 1) >= presetDataManager.savedPresets().size())) {
                page = page + 1;
                this.open(id,collection);
                player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
            } else {
                player.sendMessage(Main.getInstance().getMessageManager().getMessage(MessageKey.LAST_PAGE));
                player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventoryFailedSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(menuMessageManager.getMenuItemName(MenuMessageKey.BACK_BUTTON))) {
            player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
            new GlobalEggRewardsMenu(Main.getPlayerMenuUtility(super.playerMenuUtility.getOwner())).open(id, collection);
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(menuMessageManager.getMenuItemName(MenuMessageKey.CLOSE_BUTTON))) {
            player.closeInventory();
            player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
        }

        XMaterial material = XMaterial.matchXMaterial(event.getCurrentItem());
        switch (material) {
            case BARRIER:
                player.closeInventory();
                player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
                break;
            case PLAYER_HEAD:
                if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(menuMessageManager.getMenuItemName(MenuMessageKey.PREVIOUS_PAGE_BUTTON))) {
                    if (page == 0) {
                        player.sendMessage(Main.getInstance().getMessageManager().getMessage(MessageKey.FIRST_PAGE));
                        player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventoryFailedSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
                    } else {
                        page = page - 1;
                        this.open(id,collection);
                        player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
                    }
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(menuMessageManager.getMenuItemName(MenuMessageKey.NEXT_PAGE_BUTTON))) {
                    if (!((index + 1) >= presetDataManager.savedPresets().size())) {
                        page = page + 1;
                        this.open(id,collection);
                        player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
                    } else {
                        player.sendMessage(Main.getInstance().getMessageManager().getMessage(MessageKey.LAST_PAGE));
                        player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventoryFailedSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
                    }
                } else if (event.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(menuMessageManager.getMenuItemName(MenuMessageKey.BACK_BUTTON))) {
                    player.playSound(player.getLocation(), Main.getInstance().getSoundManager().playInventorySuccessSound(), Main.getInstance().getSoundManager().getSoundVolume(), 1);
                    new GlobalEggRewardsMenu(Main.getPlayerMenuUtility(super.playerMenuUtility.getOwner())).open(id, collection);
                }
                break;
        }
    }
}

