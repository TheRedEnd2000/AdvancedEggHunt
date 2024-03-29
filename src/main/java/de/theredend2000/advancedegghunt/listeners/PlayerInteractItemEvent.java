package de.theredend2000.advancedegghunt.listeners;

import de.theredend2000.advancedegghunt.Main;
import de.theredend2000.advancedegghunt.managers.inventorymanager.EggPlaceMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractItemEvent implements Listener {

    public PlayerInteractItemEvent(){
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        if (event.getItemDrop().getItemStack().getItemMeta() == null ||
                !event.getItemDrop().getItemStack().getItemMeta().hasLocalizedName()) {
            return;
        }

        if (event.getItemDrop().getItemStack().getItemMeta().getLocalizedName().equals("egghunt.finish") &&
                Main.getInstance().getPlaceEggsPlayers().contains(player)) {
            event.getItemDrop().remove();
            Bukkit.dispatchCommand(player, "egghunt placeEggs");
            return;
        }

        if (event.getItemDrop().getItemStack().getItemMeta().getLocalizedName().equals("egghunt.eggs")){
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if (event.getItem() == null ||
                !event.getItem().getType().equals(Material.NETHER_STAR) ||
                event.getItem().getItemMeta() == null ||
                !event.getItem().getItemMeta().hasLocalizedName() ||
                !event.getItem().getItemMeta().getLocalizedName().equals("egghunt.eggs") ||
                !event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && !event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            return;
        }
        new EggPlaceMenu(Main.getPlayerMenuUtility(player)).open();
            /*Configs headsConfig = CustomHeads.getHeadsConfig();
                Inventory menu = CustomHeads.getLooks().getCreatedMenus().get(headsConfig.get().getString("mainMenu"));
                if (menu == null) {
                    return;
                }
                player.openInventory(cloneInventory(menu, CustomHeads.getLooks().getCreatedMenuTitles().get(headsConfig.get().getString("mainMenu")), player));*/
    }
}
