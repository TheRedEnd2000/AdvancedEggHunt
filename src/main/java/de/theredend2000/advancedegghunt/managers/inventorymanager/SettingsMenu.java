package de.theredend2000.advancedegghunt.managers.inventorymanager;

import com.cryptomorin.xseries.XMaterial;
import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.managers.SoundManager;
import de.theredend2000.advancedegghunt.managers.inventorymanager.common.InventoryMenu;
import de.theredend2000.advancedegghunt.util.ItemBuilder;
import de.theredend2000.advancedegghunt.util.ItemHelper;
import de.theredend2000.advancedegghunt.util.PlayerMenuUtility;
import de.theredend2000.advancedegghunt.util.messages.MessageKey;
import de.theredend2000.advancedegghunt.util.messages.MessageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SettingsMenu extends InventoryMenu {
    private MessageManager messageManager;

    public SettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility, "Advanced Egg Settings", (short) 54, XMaterial.RED_STAINED_GLASS_PANE);
        messageManager = Main.getInstance().getMessageManager();
    }

    public void open() {
        super.addMenuBorder();
        addMenuBorderButtons();
        menuContent();

        playerMenuUtility.getOwner().openInventory(getInventory());
    }

    private void addMenuBorderButtons() {
        inventoryContent[49] = new ItemBuilder(XMaterial.BARRIER)
                .setDisplayName("§4Close")
                .setCustomId("settings.close")
                .build();
    }

    private void menuContent() {
        getInventory().setItem(10, new ItemBuilder(XMaterial.GOLD_INGOT)
                .setDisplayName("§3One egg found reward")
                .setLore("§7If this function is activated", "§7all commands entered in the config are executed.", "", Main.getInstance().getPluginConfig().getPlayerFoundOneEggRewards() ? "§a§l✔ Enabled" : "§c§l❌ Disabled", "§eClick to toggle.")
                .setCustomId("settings.foundoneegg")
                .withGlow(Main.getInstance().getPluginConfig().getPlayerFoundOneEggRewards())
                .build());
        getInventory().setItem(11, new ItemBuilder(XMaterial.EMERALD)
                .setDisplayName("§3All eggs found reward")
                .setLore("§7If this function is activated", "§7all commands entered in the config are executed.", "", Main.getInstance().getPluginConfig().getPlayerFoundAllEggsReward() ? "§a§l✔ Enabled" : "§c§l❌ Disabled", "§eClick to toggle.")
                .setCustomId("settings.foundalleggs")
                .withGlow(Main.getInstance().getPluginConfig().getPlayerFoundAllEggsReward())
                .build());
        getInventory().setItem(12, new ItemBuilder(XMaterial.CLOCK)
                .setDisplayName("§3Updater")
                .setLore("§7If this function is activated", "§7all operators will get an information", "§7if a new plugin version is out.", "", Main.getInstance().getPluginConfig().getUpdater() ? "§a§l✔ Enabled" : "§c§l❌ Disabled", "§eClick to toggle.")
                .setCustomId("settings.updater")
                .withGlow(Main.getInstance().getPluginConfig().getUpdater())
                .build());
        getInventory().setItem(13, new ItemBuilder(XMaterial.COMMAND_BLOCK)
                .setDisplayName("§3Command feedback")
                .setLore("§7If this function is activated", "§7no more commands are sent", "§7to the operators listed in the console.", "", "§c§l❌ Discontinued", "§eClick to toggle.")
                .setCustomId("settings.commandfeedback")
                .build());
        getInventory().setItem(14, new ItemBuilder(XMaterial.NOTE_BLOCK)
                .setDisplayName("§3Sound volume")
                .setLore("§7Change the volume of all sound of the plugin", "§7If volume equal 0 no sound will be played.", "", "§7Currently: §6" + Main.getInstance().getPluginConfig().getSoundVolume(), "§eLEFT-CLICK to add one.", "§eRIGHT-CLICK to remove one.")
                .setCustomId("settings.soundvolume")
                .withGlow(true)
                .build());
        getInventory().setItem(15, new ItemBuilder(XMaterial.COMPASS)
                .setDisplayName("§3Show coordinates when found")
                .setLore("§7If this function is activated", "§7players can see the coordinates", "§7in the progress menu.", "", "§2Info: §7The coordinates are only visible if", "§7the player has found the egg.", "", Main.getInstance().getPluginConfig().getShowCoordinatesWhenEggFoundInProgressInventory() ? "§a§l✔ Enabled" : "§c§l❌ Disabled", "§eClick to toggle.")
                .setCustomId("settings.showcoordinates")
                .withGlow(Main.getInstance().getPluginConfig().getShowCoordinatesWhenEggFoundInProgressInventory())
                .build());
        getInventory().setItem(16, new ItemBuilder(XMaterial.ARMOR_STAND)
                .setDisplayName("§3Armorstand glow")
                .setLore("§7Set how long the armorstands are", "§7visible for all players.", "", "§7Currently: §6" + Main.getInstance().getPluginConfig().getArmorstandGlow(), "§eLEFT-CLICK to add one.", "§eRIGHT-CLICK to remove one.")
                .setCustomId("settings.armorstandglow")
                .withGlow(true)
                .build());
        getInventory().setItem(19, new ItemBuilder(XMaterial.OAK_SIGN)
                .setDisplayName("§3Nearby title radius")
                .setLore("§7Change the radius of the egg nearby message for all players", "§7If radius equal 0 no title will be displayed.", "", "§7Currently: §6" + Main.getInstance().getPluginConfig().getShowEggsNearbyMessageRadius(), "§eLEFT-CLICK to add one.", "§eRIGHT-CLICK to remove one.")
                .setCustomId("settings.eggnearbyradius")
                .withGlow(true)
                .build());
        getInventory().setItem(20, new ItemBuilder(XMaterial.NAME_TAG)
                .setDisplayName("§3Show plugin prefix")
                .setLore("§7If enabled the plugin prefix", "§7will show on each message.", "§cThis will effect every message in the messages.yml file.", "", Main.getInstance().getPluginConfig().getPluginPrefixEnabled() ? "§a§l✔ Enabled" : "§c§l❌ Disabled", "§eClick to toggle.")
                .setCustomId("settings.pluginprefix")
                .withGlow(Main.getInstance().getPluginConfig().getPluginPrefixEnabled())
                .build());
        getInventory().setItem(21, new ItemBuilder(XMaterial.FIREWORK_ROCKET)
                .setDisplayName("§3Firework")
                .setLore("§7If this function is activated", "§7a firework will spawn if an egg is found.", "", Main.getInstance().getPluginConfig().getShowFireworkAfterEggFound() ? "§a§l✔ Enabled" : "§c§l❌ Disabled", "§eClick to toggle.")
                .setCustomId("settings.firework")
                .withGlow(Main.getInstance().getPluginConfig().getShowFireworkAfterEggFound())
                .build());
        getInventory().setItem(22, new ItemBuilder(XMaterial.CLOCK)
                .setDisplayName("§3Hint cooldown on fail")
                .setLore("§7Set if the cooldown of the hint function", "§7applies if the player fails.", "", Main.getInstance().getPluginConfig().getHintApplyCooldownOnFail() ? "§a§l✔ Enabled" : "§c§l❌ Disabled", "§eClick to toggle.")
                .setCustomId("settings.hintcooldown")
                .withGlow(Main.getInstance().getPluginConfig().getHintApplyCooldownOnFail())
                .build());
    }

    @Override
    public void handleMenu(InventoryClickEvent event) {
        if (!ItemHelper.hasItemId(event.getCurrentItem())) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        SoundManager soundManager = Main.getInstance().getSoundManager();

        switch (ItemHelper.getItemId(event.getCurrentItem())) {
            case "settings.close":
                player.closeInventory();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.foundoneegg":
                Main.getInstance().getPluginConfig().setPlayerFoundOneEggRewards(!Main.getInstance().getPluginConfig().getPlayerFoundOneEggRewards());
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.foundalleggs":
                Main.getInstance().getPluginConfig().setPlayerFoundAllEggsReward(!Main.getInstance().getPluginConfig().getPlayerFoundAllEggsReward());
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.updater":
                Main.getInstance().getPluginConfig().setUpdater(!Main.getInstance().getPluginConfig().getUpdater());
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.commandfeedback":
                player.sendMessage(messageManager.getMessage(MessageKey.SETTING_COMMANDFEEDBACK));
                break;
            case "settings.soundvolume":
                int currentVolume = Main.getInstance().getPluginConfig().getSoundVolume();
                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    if (currentVolume == 15) {
                        player.sendMessage(messageManager.getMessage(MessageKey.SOUND_VOLUME));
                        return;
                    }
                    Main.getInstance().getPluginConfig().setSoundVolume(currentVolume + 1);

                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentVolume == 0) {
                        player.sendMessage(messageManager.getMessage(MessageKey.SOUND_VOLUME));
                        return;
                    }
                    Main.getInstance().getPluginConfig().setSoundVolume(currentVolume - 1);
                }
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.armorstandglow":
                int currentTime = Main.getInstance().getPluginConfig().getArmorstandGlow();
                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    if (currentTime == 120) {
                        player.sendMessage(messageManager.getMessage(MessageKey.ARMORSTAND_GLOW));
                        return;
                    }
                    Main.getInstance().getPluginConfig().setArmorstandGlow(currentTime + 1);

                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentTime == 0) {
                        player.sendMessage(messageManager.getMessage(MessageKey.ARMORSTAND_GLOW));
                        return;
                    }
                    Main.getInstance().getPluginConfig().setArmorstandGlow(currentTime - 1);
                }
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.showcoordinates":
                Main.getInstance().getPluginConfig().setShowCoordinatesWhenEggFoundInProgressInventory(!Main.getInstance().getPluginConfig().getShowCoordinatesWhenEggFoundInProgressInventory());
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.eggnearbyradius":
                int currentRadius = Main.getInstance().getPluginConfig().getShowEggsNearbyMessageRadius();
                if (event.getAction() == InventoryAction.PICKUP_ALL) {
                    if (currentRadius == 50) {
                        player.sendMessage(messageManager.getMessage(MessageKey.EGG_RADIUS));
                        return;
                    }
                    Main.getInstance().getPluginConfig().setShowEggsNearbyMessageRadius(currentRadius + 1);

                } else if (event.getAction() == InventoryAction.PICKUP_HALF) {
                    if (currentRadius == 0) {
                        player.sendMessage(messageManager.getMessage(MessageKey.EGG_RADIUS));
                        return;
                    }
                    Main.getInstance().getPluginConfig().setShowEggsNearbyMessageRadius(currentRadius - 1);
                }
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.pluginprefix":
                Main.getInstance().getPluginConfig().setPluginPrefixEnabled(!Main.getInstance().getPluginConfig().getPluginPrefixEnabled());
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.firework":
                Main.getInstance().getPluginConfig().setShowFireworkAfterEggFound(!Main.getInstance().getPluginConfig().getShowFireworkAfterEggFound());
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
            case "settings.hintcooldown":
                Main.getInstance().getPluginConfig().setHintApplyCooldownOnFails(!Main.getInstance().getPluginConfig().getHintApplyCooldownOnFail());
                Main.getInstance().getPluginConfig().saveData();
                menuContent();
                player.playSound(player.getLocation(), soundManager.playInventorySuccessSound(), soundManager.getSoundVolume(), 1);
                break;
        }
    }
}
