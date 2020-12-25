package xyz.ttryy.extendedbeacon.listener;

import org.bukkit.Location;
import org.bukkit.block.Beacon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import xyz.ttryy.extendedbeacon.main.ExtendedBeaconPlugin;

public class MobSpawnListener implements Listener {

    private ExtendedBeaconPlugin plugin;

    public MobSpawnListener(ExtendedBeaconPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event){
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL){
            return;
        }

        for(Beacon beacon : plugin.getBeacons()){
            if(beacon.getLocation().getWorld() != event.getLocation().getWorld()){
                return;
            }

            if(beacon.getPersistentDataContainer().has(plugin.getTorchedKey(), PersistentDataType.INTEGER) &&
                    beacon.getPersistentDataContainer().get(plugin.getTorchedKey(), PersistentDataType.INTEGER) == 1 &&
                    beacon.getTier() > 0){
                int range = 10 + beacon.getTier()*10;

                Location location = event.getLocation();

                // Unlimited range above the beacon
                if(beacon.getLocation().getY() < location.getY()){
                    location.setY(beacon.getLocation().getY());
                }

                if(location.distance(beacon.getLocation()) <= range){
                    //Visualizes the mobs (not) spawning
                    //event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.ARMOR_STAND).setGlowing(true);
                    event.setCancelled(true);
                }
            }
        }

    }
}
