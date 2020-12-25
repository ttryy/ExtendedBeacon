package xyz.ttryy.extendedbeacon.listener;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Beacon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;
import xyz.ttryy.extendedbeacon.main.ExtendedBeaconPlugin;
import xyz.ttryy.extendedbeacon.utils.Messages;
import xyz.ttryy.extendedbeacon.utils.Permissions;

public class InteractBeaconListener implements Listener {

    private ExtendedBeaconPlugin plugin;

    public InteractBeaconListener(ExtendedBeaconPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void interactBeacon(PlayerInteractEvent event){
        if(event.getAction() != Action.LEFT_CLICK_BLOCK){
            return;
        }

        if(event.getClickedBlock().getType() != Material.BEACON){
            return;
        }

        Beacon beacon = (Beacon) event.getClickedBlock().getState();

        // Decides if mobs spawn in beacon radius
        if(event.getPlayer().getInventory().getItemInMainHand().getType() == Material.TORCH){
            // Player need permission to change the mob spawning
            if(!event.getPlayer().hasPermission(Permissions.TOGGLE_MOB_SPAWNING.getPermission())){
                return;
            }

            int torched = 0;
            if(beacon.getPersistentDataContainer().has(plugin.getTorchedKey(), PersistentDataType.INTEGER)){
                torched = beacon.getPersistentDataContainer().get(plugin.getTorchedKey(), PersistentDataType.INTEGER);
            }

            if(torched == 0){
                beacon.getPersistentDataContainer().set(plugin.getTorchedKey(), PersistentDataType.INTEGER, 1);
                Messages.NO_MOB_SPAWNING.send(event.getPlayer());
                if(!plugin.getBeacons().contains(beacon)){
                    plugin.getBeacons().add(beacon);
                }
            } else if(torched == 1){
                beacon.getPersistentDataContainer().set(plugin.getTorchedKey(), PersistentDataType.INTEGER, 0);
                Messages.MOB_SPAWNING.send(event.getPlayer());
            }
            event.setCancelled(true);
            beacon.update();
        }


    }
}
