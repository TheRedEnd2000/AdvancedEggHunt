package de.theredend2000.advancedegghunt.managers.inventorymanager;

import com.cryptomorin.xseries.XMaterial;
import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.managers.SoundManager;
import de.theredend2000.advancedegghunt.managers.inventorymanager.collection.CollectionEditor;
import de.theredend2000.advancedegghunt.managers.inventorymanager.common.InventoryMenu;
import de.theredend2000.advancedegghunt.util.ItemBuilder;
import de.theredend2000.advancedegghunt.util.ItemHelper;
import de.theredend2000.advancedegghunt.util.PlayerMenuUtility;
import de.theredend2000.advancedegghunt.util.messages.MenuMessageKey;
import de.theredend2000.advancedegghunt.util.messages.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Random;

public class ResetMenu extends InventoryMenu {
    private MessageManager messageManager;
    private Main plugin;

    public ResetMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility, "Reset - Selection", (short) 54);
        messageManager = Main.getInstance().getMessageManager();
        this.plugin = Main.getInstance();
    }

    public void open(String collection) {
        super.addMenuBorder();
        addMenuBorderButtons(collection);
        getInventory().setContents(inventoryContent);
        menuContent(collection);

        playerMenuUtility.getOwner().openInventory(getInventory());
    }

    private void addMenuBorderButtons(String collection) {
        inventoryContent[4] =  new ItemBuilder(XMaterial.PLAYER_HEAD)
                .setCustomId("reset.collection")
                .setDisplayName("§6" + collection)
                .setSkullOwner(plugin.getEggManager().getRandomEggTexture(new Random().nextInt(7)))
                .build();

        inventoryContent[37] = new ItemBuilder(XMaterial.RED_TERRACOTTA)
                .setCustomId("reset.reset_all")
                .setDisplayName("§cReset all")
                .setLore("§eClick to reset all.")
                .build();

        inventoryContent[45] = new ItemBuilder(XMaterial.PLAYER_HEAD)
                .setCustomId("reset.back")
                .setDisplayName(menuMessageManager.getMenuItemName(MenuMessageKey.BACK_BUTTON))
                .setLore(menuMessageManager.getMenuItemLore(MenuMessageKey.BACK_BUTTON))
                .setSkullOwner(Main.getTexture("ODFjOTZhNWMzZDEzYzMxOTkxODNlMWJjN2YwODZmNTRjYTJhNjUyNzEyNjMwM2FjOGUyNWQ2M2UxNmI2NGNjZiJ9fX0="))
                .build();
        inventoryContent[49] = new ItemBuilder(XMaterial.BARRIER)
                .setCustomId("reset.close")
                .setDisplayName(menuMessageManager.getMenuItemName(MenuMessageKey.CLOSE_BUTTON))
                .setLore(menuMessageManager.getMenuItemLore(MenuMessageKey.CLOSE_BUTTON))
                .build();
    }

    private void menuContent(String collection) {
        FileConfiguration placedEggs = plugin.getEggDataManager().getPlacedEggs(collection);
        String overall = plugin.getRequirementsManager().getConvertedTime(collection);

        getInventory().setItem(10, new ItemBuilder(XMaterial.REDSTONE)
                .setCustomId("reset.reset_year")
                .setDisplayName("§6Reset - Year")
                .setLore("§7Current: §b" + placedEggs.getInt("Reset.Year") + "Y", "§7Overall: §6" + overall, "", "§eLEFT-CLICK add one.", "§eMIDDLE-CLICK reset it.", "§eRIGHT-CLICK remove one.")
                .build());
        getInventory().setItem(11, new ItemBuilder(XMaterial.REDSTONE)
                .setCustomId("reset.reset_month")
                .setDisplayName("§6Reset - Month")
                .setLore("§7Current: §b" + placedEggs.getInt("Reset.Month") + "M", "§7Overall: §6" + overall, "", "§eLEFT-CLICK add one.", "§eMIDDLE-CLICK reset it.", "§eRIGHT-CLICK remove one.")
                .build());
        getInventory().setItem(12, new ItemBuilder(XMaterial.REDSTONE)
                .setCustomId("reset.reset_day")
                .setDisplayName("§6Reset - Day")
                .setLore("§7Current: §b" + placedEggs.getInt("Reset.Day") + "d", "§7Overall: §6" + overall, "", "§eLEFT-CLICK add one.", "§eMIDDLE-CLICK reset it.", "§eRIGHT-CLICK remove one.")
                .build());
        getInventory().setItem(13, new ItemBuilder(XMaterial.REDSTONE)
                .setCustomId("reset.reset_hour")
                .setDisplayName("§6Reset - Hour")
                .setLore("§7Current: §b" + placedEggs.getInt("Reset.Hour") + "h", "§7Overall: §6" + overall, "", "§eLEFT-CLICK add one.", "§eMIDDLE-CLICK reset it.", "§eRIGHT-CLICK remove one.")
                .build());
        getInventory().setItem(14, new ItemBuilder(XMaterial.REDSTONE)
                .setCustomId("reset.reset_minute")
                .setDisplayName("§6Reset - Minute")
                .setLore("§7Current: §b" + placedEggs.getInt("Reset.Minute") + "m", "§7Overall: §6" + overall, "", "§eLEFT-CLICK add one.", "§eMIDDLE-CLICK reset it.", "§eRIGHT-CLICK remove one.")
                .build());
        getInventory().setItem(15, new ItemBuilder(XMaterial.REDSTONE)
                .setCustomId("reset.reset_second")
                .setDisplayName("§6Reset - Second")
                .setLore("§7Current: §b" + placedEggs.getInt("Reset.Second") + "s", "§7Overall: §6" + overall, "", "§eLEFT-CLICK add one.", "§eMIDDLE-CLICK reset it.", "§eRIGHT-CLICK remove one.")
                .build());
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (!event.getCurrentItem().getItemMeta().hasDisplayName()) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        SoundManager soundManager = Main.getInstance().getSoundManager();

        String collection = ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName());
        FileConfiguration placedEggs = plugin.getEggDataManager().getPlacedEggs(collection);

        switch (ItemHelper.getItemId(event.getCurrentItem())) {
            case "reset.close":
                player.closeInventory();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "reset.back":
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                new CollectionEditor(Main.getPlayerMenuUtility(player)).open(collection);
                break;
            case "reset.reset_year":
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                int currentYear = placedEggs.getInt("Reset.Year");

                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    placedEggs.set("Reset.Year", currentYear + 1);
                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentYear - 1 >= 0) {
                        placedEggs.set("Reset.Year", currentYear - 1);
                    }
                } else if (event.getClick() == ClickType.MIDDLE) {
                    placedEggs.set("Reset.Year", 0);
                }
                plugin.getEggDataManager().savePlacedEggs(collection, placedEggs);
                menuContent(collection);
                break;
            case "reset.reset_month":
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                int currentMonth = placedEggs.getInt("Reset.Month");

                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    placedEggs.set("Reset.Month", currentMonth + 1);
                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentMonth - 1 >= 0) {
                        placedEggs.set("Reset.Month", currentMonth - 1);
                    }
                } else if (event.getClick() == ClickType.MIDDLE) {
                    placedEggs.set("Reset.Month", 0);
                }
                plugin.getEggDataManager().savePlacedEggs(collection, placedEggs);
                menuContent(collection);
                break;
            case "reset.reset_day":
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                int currentDay = placedEggs.getInt("Reset.Day");

                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    placedEggs.set("Reset.Day", currentDay + 1);
                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentDay - 1 >= 0) {
                        placedEggs.set("Reset.Day", currentDay - 1);
                    }
                } else if (event.getClick() == ClickType.MIDDLE) {
                    placedEggs.set("Reset.Day", 0);
                }
                plugin.getEggDataManager().savePlacedEggs(collection, placedEggs);
                menuContent(collection);
                break;
            case "reset.reset_hour":
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                int currentHour = placedEggs.getInt("Reset.Hour");

                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    placedEggs.set("Reset.Hour", currentHour + 1);
                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentHour - 1 >= 0) {
                        placedEggs.set("Reset.Hour", currentHour - 1);
                    }
                } else if (event.getClick() == ClickType.MIDDLE) {
                    placedEggs.set("Reset.Hour", 0);
                }
                plugin.getEggDataManager().savePlacedEggs(collection, placedEggs);
                menuContent(collection);
                break;
            case "reset.reset_minute":
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                int currentMin = placedEggs.getInt("Reset.Minute");

                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    placedEggs.set("Reset.Minute", currentMin + 1);
                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentMin - 1 >= 0) {
                        placedEggs.set("Reset.Minute", currentMin - 1);
                    }
                } else if (event.getClick() == ClickType.MIDDLE) {
                    placedEggs.set("Reset.Minute", 0);
                }
                plugin.getEggDataManager().savePlacedEggs(collection, placedEggs);
                menuContent(collection);
                break;
            case "reset.reset_second":
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                int currentSec = placedEggs.getInt("Reset.Second");

                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    placedEggs.set("Reset.Second", currentSec + 1);
                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentSec - 1 >= 0) {
                        placedEggs.set("Reset.Second", currentSec - 1);
                    }
                } else if (event.getClick() == ClickType.MIDDLE) {
                    placedEggs.set("Reset.Second", 0);
                }
                plugin.getEggDataManager().savePlacedEggs(collection, placedEggs);
                menuContent(collection);
                break;
            case "reset.reset_all":
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                plugin.getRequirementsManager().resetReset(collection);
                menuContent(collection);
                break;
        }
    }
}
