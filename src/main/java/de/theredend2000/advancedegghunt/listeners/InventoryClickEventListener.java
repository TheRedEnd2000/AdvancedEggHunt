package de.theredend2000.advancedegghunt.listeners;

import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.managers.inventorymanager.common.IInventoryMenu;
import de.theredend2000.advancedegghunt.util.messages.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FireworkExplodeEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickEventListener implements Listener {

    private MessageManager messageManager;
    private Main plugin;

    public InventoryClickEventListener(){
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
        messageManager = Main.getInstance().getMessageManager();
        plugin = Main.getInstance();
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player) ||
                event.getCurrentItem() == null ||
                event.getCurrentItem().getItemMeta() == null) {
            return;
        }

        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof IInventoryMenu) {
            event.setCancelled(true);
            if(event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD)) event.setCancelled(true);
            if (event.getCurrentItem() == null) {
                return;
            }
            IInventoryMenu menu = (IInventoryMenu) holder;
            menu.handleMenu(event);
        }
    }
}
