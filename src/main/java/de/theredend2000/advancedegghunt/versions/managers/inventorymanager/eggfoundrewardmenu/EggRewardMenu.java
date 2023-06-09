package de.theredend2000.advancedegghunt.versions.managers.inventorymanager.eggfoundrewardmenu;

import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.util.ConfigLocationUtil;
import de.theredend2000.advancedegghunt.util.ItemBuilder;
import de.theredend2000.advancedegghunt.versions.VersionManager;
import de.theredend2000.advancedegghunt.versions.managers.inventorymanager.InventoryManager_1_19_R1;
import de.theredend2000.advancedegghunt.versions.managers.inventorymanager.egginformation.EggInformationMenu;
import de.theredend2000.advancedegghunt.versions.managers.inventorymanager.paginatedMenu.ListPaginatedMenu;
import de.theredend2000.advancedegghunt.versions.managers.inventorymanager.paginatedMenu.PlayerMenuUtility;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static org.spigotmc.AsyncCatcher.enabled;

public class EggRewardMenu extends RewardPaginatedMenu {

    public EggRewardMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Eggs found rewards";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<String> keys = new ArrayList<>();
        if(Main.getInstance().getConfig().contains("Rewards.")){
            keys.addAll(Main.getInstance().getConfig().getConfigurationSection("Rewards.").getKeys(false));
            for(String id : Main.getInstance().getConfig().getConfigurationSection("Rewards.").getKeys(false)){
                if(Objects.requireNonNull(e.getCurrentItem().getItemMeta()).getLocalizedName().equals(id)){;
                    VersionManager.getInventoryManager().createCommandSettingsMenu(p,id);
                    p.playSound(p.getLocation(),VersionManager.getSoundManager().playInventorySuccessSound(),VersionManager.getSoundManager().getSoundVolume(), 1);
                }
            }
        }

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            p.closeInventory();
            p.playSound(p.getLocation(),VersionManager.getSoundManager().playInventorySuccessSound(),VersionManager.getSoundManager().getSoundVolume(), 1);
        }else if (e.getCurrentItem().getType().equals(Material.EMERALD_BLOCK)) {
            if(Main.getInstance().getRefreshCooldown().containsKey(p.getName())){
                if(Main.getInstance().getRefreshCooldown().get(p.getName()) > System.currentTimeMillis()){
                    p.sendMessage(Main.getInstance().getMessage("RefreshWaitMessage"));
                    p.playSound(p.getLocation(),VersionManager.getSoundManager().playInventoryFailedSound(),VersionManager.getSoundManager().getSoundVolume(), 1);
                    return;
                }
            }
            Main.getInstance().getRefreshCooldown().put(p.getName(), System.currentTimeMillis()+ (3*1000));
            new EggRewardMenu(Main.getPlayerMenuUtility(p)).open();
            p.playSound(p.getLocation(),VersionManager.getSoundManager().playInventorySuccessSound(),VersionManager.getSoundManager().getSoundVolume(), 1);
        }else if(e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)){
            if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")){
                if (page == 0){
                    p.sendMessage(Main.getInstance().getMessage("AlreadyOnFirstPageMessage"));
                    p.playSound(p.getLocation(),VersionManager.getSoundManager().playInventoryFailedSound(),VersionManager.getSoundManager().getSoundVolume(), 1);
                }else{
                    page = page - 1;
                    super.open();
                    p.playSound(p.getLocation(),VersionManager.getSoundManager().playInventorySuccessSound(),VersionManager.getSoundManager().getSoundVolume(), 1);
                }
            }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")){
                if (!((index + 1) >= keys.size())){
                    page = page + 1;
                    super.open();
                    p.playSound(p.getLocation(),VersionManager.getSoundManager().playInventorySuccessSound(),VersionManager.getSoundManager().getSoundVolume(), 1);
                }else{
                    p.sendMessage(Main.getInstance().getMessage("AlreadyOnLastPageMessage"));
                    p.playSound(p.getLocation(),VersionManager.getSoundManager().playInventoryFailedSound(),VersionManager.getSoundManager().getSoundVolume(), 1);
                }
            }else if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Add command")){
                if(Main.getInstance().getPlayerAddCommand().containsKey(p)){
                    p.sendMessage(Main.getInstance().getMessage("OnlyOneCommandMessage"));
                    return;
                }
                p.closeInventory();
                Main.getInstance().getPlayerAddCommand().put(p,120);
                TextComponent c = new TextComponent("\n\n\n\n\n"+Main.getInstance().getMessage("EnterNewCommandMessage")+"\n\n");
                TextComponent clickme = new TextComponent("§9-----------§3§l[PLACEHOLDERS] §7(Hover)§9-----------");
                clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§2Available placeholders:\n§b- %PLAYER% --> Name of the player\n§b- & --> For color codes (&6=gold)\n§b- %EGGS_FOUND% --> How many eggs the player has found\n§b- %EGGS_MAX% --> How many eggs are placed\n§b- %PREFIX% --> The prefix of the plugin")));
                c.addExtra(clickme);
                p.spigot().sendMessage(c);
            }
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();
        ArrayList<String> keys = new ArrayList<>();
        if(Main.getInstance().getConfig().contains("Rewards.")){
            keys.addAll(Main.getInstance().getConfig().getConfigurationSection("Rewards.").getKeys(false));
        }else
            inventory.setItem(22, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayname("§4§lNo Commands").setLore("§7You can add commands by using","§e/egghunt placeEggs§7.").build());
        if(keys != null && !keys.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= keys.size()) break;
                if (keys.get(index) != null){
                    String command = Main.getInstance().getConfig().getString("Rewards."+keys.get(index)+".command").replaceAll("§","&");
                    boolean enabled = Main.getInstance().getConfig().getBoolean("Rewards."+keys.get(index)+".enabled");
                    int type = Main.getInstance().getConfig().getInt("Rewards."+keys.get(index)+".type");
                    inventory.addItem(new ItemBuilder(Main.getInstance().getMaterial(Main.getInstance().getConfig().getString("Settings.RewardInventoryMaterial"))).setDisplayname("§b§lCommand §7#"+keys.get(index)).setLore("","§9Information:","§7Command: §6"+command,"§7Command Enabled: "+(enabled ? "§atrue" : "§cfalse"),"§7Type: §6"+type,"","§a§lNote:","§2Type 0:","§7Type 0 means that this command will be","§7be executed if the player found §7§lone §7egg.","§2Type 1:","§7Type 1 means that this command will be","§7be executed if the player had found §7§lall §7egg.","","§eClick to configure the command.").setLocalizedName(keys.get(index)).build());
                }
            }
        }else{
            inventory.setItem(22, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayname("§4§lNo Commands").setLore("§7You can add commands by using","§e/egghunt placeEggs§7.").build());
        }
    }
}

