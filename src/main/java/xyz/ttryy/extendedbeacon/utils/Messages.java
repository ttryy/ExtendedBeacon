package xyz.ttryy.extendedbeacon.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import xyz.ttryy.extendedbeacon.main.ExtendedBeaconPlugin;

public enum Messages {

    NO_MOB_SPAWNING("NO_MOB_SPAWNING"),
    MOB_SPAWNING("MOB_SPAWNING"),
    NO_HUNGER("NO_HUNGER"),
    HUNGER("HUNGER"),
    MOB_SPAWNING_ITEMLORE("MOB_SPAWNING_ITEMLORE"),
    HUNGER_ITEMLORE("HUNGER_ITEMLORE");

    private String message;

    Messages(String configName) {
        try {
            message = ChatColor.translateAlternateColorCodes('&', ExtendedBeaconPlugin.getInstance().getConfig().getString("messages." + configName));
        } catch (Exception e){
            message = ChatColor.RED + "Could not load message \"" + configName + "\" from the config";
            e.printStackTrace();
        }
    }

    public void send(CommandSender sender){
        sender.sendMessage(message);
    }

    public String getMessage() {
        return message;
    }
}
