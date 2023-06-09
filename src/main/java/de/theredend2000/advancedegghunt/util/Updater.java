package de.theredend2000.advancedegghunt.util;

import de.theredend2000.advancedegghunt.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Updater implements Listener {

    private Main plugin;
    private int key = 109085;

    public Updater(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
        isOutdated();
    }

    public boolean isOutdated(Player player) {
        try {
            HttpURLConnection c = (HttpURLConnection)new URL("https://api.spigotmc.org/legacy/update.php?resource="+key).openConnection();
            String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine();
            c.disconnect();
            String oldVersion = plugin.getDescription().getVersion();
            if(!newVersion.equals(oldVersion)) {
                player.sendMessage(Objects.requireNonNull(plugin.messages.getString("Prefix")).replaceAll("&","§")+"§aThere is a newer version available. Please update your plugin§a. §aVersion: §2§l"+oldVersion+"§6 --> §2§l"+newVersion);
                return true;
            }
        }
        catch(Exception e) {
            player.sendMessage(Objects.requireNonNull(plugin.messages.getString("Prefix")).replaceAll("&","§")+"§4§lERROR: §cCould not make connection to SpigotMC.org");
            e.printStackTrace();
        }
        return false;
    }
    public boolean isOutdated() {
        try {
            HttpURLConnection c = (HttpURLConnection)new URL("https://api.spigotmc.org/legacy/update.php?resource="+key).openConnection();
            String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine();
            c.disconnect();
            String oldVersion = plugin.getDescription().getVersion();
            if(!newVersion.equals(oldVersion)) {
                Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(plugin.messages.getString("Prefix")).replaceAll("&","§")+"§cYou do not have the most updated version of §eAdvancedEggHunt§c.");
                Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(plugin.messages.getString("Prefix")).replaceAll("&","§")+"§cPlease chance the version: §4"+oldVersion+"§6 --> §2§l"+newVersion);
                return true;
            }
        }
        catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(plugin.messages.getString("Prefix")).replaceAll("&","§")+"§4§lERROR: §cCould not make connection to SpigotMC.org");
            e.printStackTrace();
        }
        return false;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        boolean updates = plugin.getConfig().getBoolean("Settings.Updater");
        if(updates){
            if(!player.isOp()) return;
            if(isOutdated(player));
        }
    }

}
