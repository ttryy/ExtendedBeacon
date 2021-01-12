package xyz.ttryy.extendedbeacon.utils;

import org.bukkit.entity.Player;

public enum Permissions {

    TOGGLE_MOB_SPAWNING("extendedbeacon.togglemobspawning"),
    TOOGLE_HUNGER("extendedbeacon.togglehunger"),
    CAN_BREAK_BEACON("extendedbeacon.canbreakbeacon");

    private String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public boolean hasPermission(Player player){
        return player.hasPermission(permission);
    }

    public String getPermission() {
        return permission;
    }
}
