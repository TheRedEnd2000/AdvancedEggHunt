package de.theredend2000.advancedegghunt.versions.managers.inventorymanager.eggprogress;

import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.util.ConfigLocationUtil;
import de.theredend2000.advancedegghunt.util.ItemBuilder;
import de.theredend2000.advancedegghunt.versions.VersionManager;
import de.theredend2000.advancedegghunt.versions.managers.inventorymanager.paginatedMenu.ListPaginatedMenu;
import de.theredend2000.advancedegghunt.versions.managers.inventorymanager.paginatedMenu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.*;

public class EggProgressMenu extends ProgressPaginatedMenu {

    public EggProgressMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Egg progress";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<String> keys = new ArrayList<>();
        if(Main.getInstance().eggs.contains("Eggs.")){
            keys.addAll(Main.getInstance().eggs.getConfigurationSection("Eggs.").getKeys(false));
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
            new EggProgressMenu(Main.getPlayerMenuUtility(p)).open();
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
            }
        }
    }

    @Override
    public void setMenuItems(String playerUUID) {
        addMenuBorder();
        ArrayList<String> keys = new ArrayList<>();
        if(Main.getInstance().eggs.contains("Eggs.")){
            keys.addAll(Main.getInstance().eggs.getConfigurationSection("Eggs.").getKeys(false));
        }else
            inventory.setItem(22, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayname("§4§lNo Eggs Available").setLore("§7There are no eggs no find","§7please contact an admin.").build());

        if(keys != null && !keys.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= keys.size()) break;
                if (keys.get(index) != null){
                    boolean showcoordinates = Main.getInstance().getConfig().getBoolean("Settings.ShowCoordinatesWhenEggFoundInProgressInventory");
                    String x = Main.getInstance().eggs.getString("Eggs."+keys.get(index)+".X");
                    String y = Main.getInstance().eggs.getString("Eggs."+keys.get(index)+".Y");
                    String z = Main.getInstance().eggs.getString("Eggs."+keys.get(index)+".Z");
                    boolean hasFound = VersionManager.getEggManager().hasFound(playerMenuUtility.getOwner(), keys.get(index));
                    int timesFound = VersionManager.getEggManager().getTimesFound(keys.get(index));
                    int random = new Random().nextInt(7);
                    String date = VersionManager.getEggManager().getEggDateCollected(playerUUID,keys.get(index));
                    String time = VersionManager.getEggManager().getEggTimeCollected(playerUUID,keys.get(index));
                    if(showcoordinates && hasFound){
                        inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(VersionManager.getEggManager().getRandomEggTexture(random)).setDisplayname("§2§lEgg §7(ID#"+keys.get(index)+")").setLore("","§9Location:","§7X: §e"+x,"§7Y: §e"+y,"§7Z: §e"+z,"",(hasFound ? "§2§lYou have found this egg." : "§4§lYou haven't found this egg yet."),"","§9Collected:","§7Date: §6"+date,"§7Time: §6"+time,"").setLocalizedName(keys.get(index)).build());
                    }else if(hasFound && !showcoordinates) {
                        inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(VersionManager.getEggManager().getRandomEggTexture(random)).setDisplayname("§2§lEgg §7(ID#" + keys.get(index) + ")").setLore("", (hasFound ? "§2§lYou have found this egg." : "§4§lYou haven't found this egg yet."),"","§9Collected:","§7Date: §6"+date,"§7Time: §6"+time,"").setLocalizedName(keys.get(index)).build());
                    }else
                        inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(VersionManager.getEggManager().getRandomEggTexture(random)).setDisplayname("§2§lEgg §7(ID#" + keys.get(index) + ")").setLore("", (hasFound ? "§2§lYou have found this egg." : "§4§lYou haven't found this egg yet.")).setLocalizedName(keys.get(index)).build());
                }
            }
        }
    }
}

