package de.theredend2000.advancedegghunt.versions.managers.inventorymanager.paginatedMenu;

import org.bukkit.entity.Player;

public class PlayerMenuUtility {

    private Player owner;

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner() {
        return owner;
    }
}

