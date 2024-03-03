package de.theredend2000.advancedegghunt.listeners;

import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.managers.eggmanager.EggManager;
import de.theredend2000.advancedegghunt.managers.extramanager.ExtraManager;
import de.theredend2000.advancedegghunt.managers.soundmanager.SoundManager;
import de.theredend2000.advancedegghunt.util.messages.MessageKey;
import de.theredend2000.advancedegghunt.util.messages.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractEventListener implements Listener {

    private MessageManager messageManager;

    public PlayerInteractEventListener() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
        messageManager = Main.getInstance().getMessageManager();
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteractEvent(PlayerInteractEvent event) {
        EggManager eggManager = Main.getInstance().getEggManager();
        SoundManager soundManager = Main.getInstance().getSoundManager();
        ExtraManager extraManager = Main.getInstance().getExtraManager();
        Player player = event.getPlayer();
        Action action = event.getAction();

        if((action.equals(Action.RIGHT_CLICK_BLOCK) && Main.getInstance().getConfig().getBoolean("Settings.RightClickEgg") && event.getHand() == EquipmentSlot.HAND) || (action.equals(Action.LEFT_CLICK_BLOCK) && Main.getInstance().getConfig().getBoolean("Settings.LeftClickEgg"))) {
            if (event.getClickedBlock() != null) {
                if (eggManager.containsEgg(event.getClickedBlock()) && !Main.getInstance().getPlaceEggsPlayers().contains(player)) {
                    for (String sections : Main.getInstance().getEggDataManager().savedEggCollections()) {
                        if (eggManager.getEggSection(event.getClickedBlock()).equals(sections)) {
                            String id = eggManager.getEggID(event.getClickedBlock(), sections);
                            FileConfiguration placedEggs = Main.getInstance().getEggDataManager().getPlacedEggs(sections);
                            if(!placedEggs.getBoolean("Enabled")){
                                player.sendMessage(messageManager.getMessage(MessageKey.COLLECTION_DISABLED));
                                return;
                            }
                            if (!eggManager.hasFound(player, id,sections)) {
                                if(!Main.getInstance().getRequirementsManager().canBeAccessed(sections)){
                                    player.sendMessage(messageManager.getMessage(MessageKey.EGG_NOT_ACCESSED));
                                    return;
                                }
                                if(Main.getInstance().getRequirementsManager().getOverallTime(sections) <= 0)
                                    Main.getInstance().getPlayerEggDataManager().setResetTimer(player.getUniqueId(),sections,id);
                                eggManager.saveFoundEggs(player, event.getClickedBlock(), id, sections);
                                Location loc = new Location(event.getClickedBlock().getWorld(), event.getClickedBlock().getLocation().getX(), event.getClickedBlock().getLocation().getY(), event.getClickedBlock().getLocation().getZ());
                                if (Main.getInstance().getConfig().getBoolean("Settings.ShowFireworkAfterEggFound"))
                                    extraManager.spawnFireworkRocket(loc.add(0.5, 1.5, 0.5));
                                if (eggManager.checkFoundAll(player, sections)) {
                                    player.playSound(player.getLocation(), soundManager.playAllEggsFound(), 1, 1);
                                    if (Main.getInstance().getConfig().getBoolean("Settings.PlayerFoundAllEggsReward")) {
                                        for (String key : placedEggs.getConfigurationSection("Rewards.").getKeys(false)) {
                                            boolean enabled = placedEggs.getBoolean("Rewards." + key + ".enabled");
                                            if (placedEggs.getInt("Rewards." + key + ".type") == 1 && enabled) {
                                                String cmd = placedEggs.getString("Rewards." + key + ".command");
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd.replace("%PLAYER%", player.getName()).replaceAll("&", "§").replaceAll("%EGGS_FOUND%", String.valueOf(eggManager.getEggsFound(player,sections))).replaceAll("%EGGS_MAX%", String.valueOf(eggManager.getMaxEggs(sections))).replaceAll("%PREFIX%", Main.PREFIX));
                                            }
                                        }
                                    }
                                } else {
                                    player.playSound(player.getLocation(), soundManager.playEggFoundSound(), soundManager.getSoundVolume(), 1);
                                    if (Main.getInstance().getConfig().getBoolean("Settings.PlayerFoundOneEggRewards")) {
                                        for (String key : placedEggs.getConfigurationSection("Rewards.").getKeys(false)) {
                                            boolean enabled = placedEggs.getBoolean("Rewards." + key + ".enabled");
                                            if (placedEggs.getInt("Rewards." + key + ".type") == 0 && enabled) {
                                                String cmd = placedEggs.getString("Rewards." + key + ".command").replaceAll("&", "§");
                                                Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), cmd.replace("%PLAYER%", player.getName()).replaceAll("&", "§").replaceAll("%EGGS_FOUND%", String.valueOf(eggManager.getEggsFound(player,sections))).replaceAll("%EGGS_MAX%", String.valueOf(eggManager.getMaxEggs(sections))).replaceAll("%PREFIX%", Main.PREFIX));
                                            }
                                        }
                                    }
                                }
                            } else {
                                player.sendMessage(messageManager.getMessage(MessageKey.EGG_ALREADY_FOUND));
                                player.playSound(player.getLocation(), soundManager.playEggAlreadyFoundSound(), soundManager.getSoundVolume(), 1);
                            }
                        }
                    }
                }
            }
        }
    }
}
