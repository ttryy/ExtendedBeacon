package xyz.ttryy.extendedbeacon.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.persistence.PersistentDataType;
import xyz.ttryy.extendedbeacon.main.ExtendedBeaconPlugin;
import xyz.ttryy.extendedbeacon.utils.BeaconUtils;

import java.util.Iterator;

public class MobSpawnListener implements Listener {

    private ExtendedBeaconPlugin plugin;

    public MobSpawnListener(ExtendedBeaconPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMobSpawn(CreatureSpawnEvent event){
        if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL){
            if(!(event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM && plugin.isCustomReason())){
                return;
            }
        }

        Iterator<Location> beaconLocations = plugin.getBeacons().iterator();

        while(beaconLocations.hasNext()){
            Location beaconLocation = beaconLocations.next();

            if(beaconLocation.getBlock().getType() != Material.BEACON) {
                beaconLocations.remove();
                continue;
            }

            if(beaconLocation.getWorld() != event.getLocation().getWorld()){
                continue;
            }

            Beacon beacon = (Beacon) beaconLocation.getBlock().getState();

            if(beacon.getTier() > 0 &&
                    beacon.getPersistentDataContainer().has(plugin.getTorchedKey(), PersistentDataType.INTEGER) &&
                    beacon.getPersistentDataContainer().get(plugin.getTorchedKey(), PersistentDataType.INTEGER) == 1){
                int range = 10 + beacon.getTier()*10;

                Location location = event.getLocation();

                if(BeaconUtils.isInSquareRangeIgnoreUp(beacon.getLocation(), location, range)){
                    //Visualizes the mobs (not) spawning
                    //event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.ARMOR_STAND).setGlowing(true);
                    if(!plugin.getWhitelist().contains(event.getEntityType())){
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }

    }
}
