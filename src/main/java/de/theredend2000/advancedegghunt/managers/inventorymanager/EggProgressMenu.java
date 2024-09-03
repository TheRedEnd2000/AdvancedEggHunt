package de.theredend2000.advancedegghunt.managers.inventorymanager;

import com.cryptomorin.xseries.XMaterial;
import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.managers.SoundManager;
import de.theredend2000.advancedegghunt.managers.inventorymanager.common.PaginatedInventoryMenu;
import de.theredend2000.advancedegghunt.util.ItemBuilder;
import de.theredend2000.advancedegghunt.util.PlayerMenuUtility;
import de.theredend2000.advancedegghunt.util.messages.MenuMessageKey;
import de.theredend2000.advancedegghunt.util.messages.MenuMessageManager;
import de.theredend2000.advancedegghunt.util.messages.MessageKey;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.Random;

public class EggProgressMenu extends PaginatedInventoryMenu {

    public EggProgressMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility, "Egg progress", (short) 54);

        super.addMenuBorder();
        addMenuBorderButtons();
    }

    public void open() {
        getInventory().setContents(inventoryContent);
        setMenuItems();

        playerMenuUtility.getOwner().openInventory(getInventory());
    }

    public void addMenuBorderButtons() {
        MenuMessageManager messageManager = Main.getInstance().getMenuMessageManager();
        inventoryContent[49] = new ItemBuilder(XMaterial.BARRIER).setDisplayName(messageManager.getMenuMessage(MenuMessageKey.CLOSE_BUTTON)).build();
        inventoryContent[53] = new ItemBuilder(XMaterial.EMERALD_BLOCK).setDisplayName(messageManager.getMenuMessage(MenuMessageKey.REFRESH_BUTTON)).build();
        String selectedSection = Main.getInstance().getPlayerEggDataManager().getPlayerData(playerMenuUtility.getOwner().getUniqueId()).getString("SelectedSection");
        inventoryContent[45] = new ItemBuilder(XMaterial.PAPER)
            .setDisplayName(messageManager.getMenuMessage(MenuMessageKey.SELECTED_COLLECTION_BUTTON))
            .setLore("§7Shows your currently selected collection.", "", "§7Current: §6" + selectedSection, "", "§eClick to change.")
            .build();
    }

    public void setMenuItems() {
        MenuMessageManager messageManager = Main.getInstance().getMenuMessageManager();
        getInventory().setItem(48, new ItemBuilder(XMaterial.PLAYER_HEAD)
                .setLore("§6Page: §7(§b" + (page + 1) + "§7/§b" + getMaxPages() + "§7)", "", "§eClick to scroll.")
                .setDisplayName(messageManager.getMenuMessage(MenuMessageKey.PREVIOUS_PAGE_BUTTON))
                .setSkullOwner(Main.getTexture("ZDU5YmUxNTU3MjAxYzdmZjFhMGIzNjk2ZDE5ZWFiNDEwNDg4MGQ2YTljZGI0ZDVmYTIxYjZkYWE5ZGIyZDEifX19")).build());
        getInventory().setItem(50, new ItemBuilder(XMaterial.PLAYER_HEAD)
                .setLore("§6Page: §7(§b" + (page + 1) + "§7/§b" + getMaxPages() + "§7)", "", "§eClick to scroll.")
                .setDisplayName(messageManager.getMenuMessage(MenuMessageKey.NEXT_PAGE_BUTTON))
                .setSkullOwner(Main.getTexture("NDJiMGMwN2ZhMGU4OTIzN2Q2NzllMTMxMTZiNWFhNzVhZWJiMzRlOWM5NjhjNmJhZGIyNTFlMTI3YmRkNWIxIn19fQ==")).build());

        String collection = Main.getInstance().getEggManager().getEggCollectionFromPlayerData(playerMenuUtility.getOwner().getUniqueId());
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        addMenuBorderButtons();
        ArrayList<String> keys = new ArrayList<>();
        if(placedEggs.contains("PlacedEggs.")){
            keys.addAll(placedEggs.getConfigurationSection("PlacedEggs.").getKeys(false));
        }else
            getInventory().setItem(22, new ItemBuilder(XMaterial.RED_STAINED_GLASS)
                .setDisplayName(messageManager.getMenuMessage(MenuMessageKey.NO_EGGS_AVAILABLE))
                .setLore("§7There are no eggs no find", "§7please contact an admin.").build());

        if (keys == null || keys.isEmpty()) {
            return;
        }

        for(int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if(index >= keys.size()) break;
            if (keys.get(index) == null) {
                continue;
            }
            int slotIndex = ((9 + 1) + ((i / 7) * 9) + (i % 7));

            boolean showcoordinates = Main.getInstance().getPluginConfig().getShowCoordinatesWhenEggFoundInProgressInventory();
            String x = placedEggs.getString("PlacedEggs." + keys.get(index) + ".X");
            String y = placedEggs.getString("PlacedEggs." + keys.get(index) + ".Y");
            String z = placedEggs.getString("PlacedEggs." + keys.get(index) + ".Z");
            boolean hasFound = Main.getInstance().getEggManager().hasFound(playerMenuUtility.getOwner(), keys.get(index), collection);
            int random = new Random().nextInt(7);
            String date = Main.getInstance().getEggManager().getEggDateCollected(playerMenuUtility.getOwner().getUniqueId().toString(), keys.get(index), collection);
            String time = Main.getInstance().getEggManager().getEggTimeCollected(playerMenuUtility.getOwner().getUniqueId().toString(), keys.get(index), collection);
            ItemBuilder eggItem = new ItemBuilder(XMaterial.PLAYER_HEAD)
                .setSkullOwner(Main.getInstance().getEggManager().getRandomEggTexture(random))
                .setDisplayName(messageManager.getMenuMessage(MenuMessageKey.EGG_ITEM).replace("{0}", keys.get(index)))
                .setCustomId(keys.get(index));
            
            ArrayList<String> lore = new ArrayList<>();
            lore.add("");
            if(showcoordinates && hasFound) {
                lore.add("§9Location:");
                lore.add("§7X: §e" + x);
                lore.add("§7Y: §e" + y);
                lore.add("§7Z: §e" + z);
                lore.add("");
            }
            lore.add(hasFound ? "§2§lYou have found this egg." : "§4§lYou haven't found this egg yet.");
            if(hasFound) {
                lore.add("");
                lore.add("§9Collected:");
                lore.add("§7Date: §6" + date);
                lore.add("§7Time: §6" + time);
            }
            eggItem.setLore(lore);
            getInventory().setItem(slotIndex, eggItem.build());
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

    public int getMaxPages(){
        String collection = Main.getInstance().getEggManager().getEggCollectionFromPlayerData(playerMenuUtility.getOwner().getUniqueId());
        int keys = Main.getInstance().getEggManager().getMaxEggs(collection);
        if(keys == 0) return 1;
        return (int) Math.ceil((double) keys / getMaxItemsPerPage());
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        String collection = Main.getInstance().getEggManager().getEggCollectionFromPlayerData(playerMenuUtility.getOwner().getUniqueId());
        SoundManager soundManager = Main.getInstance().getSoundManager();
        FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(collection);
        Player player = (Player) event.getWhoClicked();

        ArrayList<String> keys = new ArrayList<>();
        if (placedEggs.contains("PlacedEggs.")){
            keys.addAll(placedEggs.getConfigurationSection("PlacedEggs.").getKeys(false));
        }

        if (event.getCurrentItem().getType().equals(Material.PAPER) &&
                ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Selected Collection")){
            new CollectionSelectMenu(Main.getPlayerMenuUtility(player)).open();
            return;
        }

        XMaterial material = XMaterial.matchXMaterial(event.getCurrentItem());
        switch (material) {
            case BARRIER:
                player.closeInventory();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case EMERALD_BLOCK:
                if (Main.getInstance().getRefreshCooldown().containsKey(player.getName())) {
                    if (Main.getInstance().getRefreshCooldown().get(player.getName()) > System.currentTimeMillis()) {
                        player.sendMessage(Main.getInstance().getMessageManager().getMessage(MessageKey.WAIT_REFRESH));
                        player.playSound(player.getLocation(), soundManager.playInventoryFailedSound(), soundManager.getSoundVolume(), 1);
                        return;
                    }
                }
                Main.getInstance().getRefreshCooldown().put(player.getName(), System.currentTimeMillis() + (3 * 1000));
                open();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case PLAYER_HEAD:
                if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Left")) {
                    if (page == 0) {
                        player.sendMessage(Main.getInstance().getMessageManager().getMessage(MessageKey.FIRST_PAGE));
                        player.playSound(player.getLocation(), soundManager.playInventoryFailedSound(), soundManager.getSoundVolume(), 1);
                    } else {
                        page = page - 1;
                        open();
                        player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                    }
                } else if (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Right")) {
                    if (!((index + 1) >= keys.size())) {
                        page = page + 1;
                        open();
                        player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                    } else {
                        player.sendMessage(Main.getInstance().getMessageManager().getMessage(MessageKey.LAST_PAGE));
                        player.playSound(player.getLocation(), soundManager.playInventoryFailedSound(), soundManager.getSoundVolume(), 1);
                    }
                }
                break;
        }
    }
}

