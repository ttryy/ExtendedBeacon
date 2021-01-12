package xyz.ttryy.extendedbeacon.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import xyz.ttryy.extendedbeacon.main.ExtendedBeaconPlugin;
import xyz.ttryy.extendedbeacon.utils.BeaconUtils;

import java.util.Iterator;

public class HungerListener implements Listener {

    private ExtendedBeaconPlugin plugin;

    public HungerListener(ExtendedBeaconPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerHunger(FoodLevelChangeEvent event){
        Iterator<Location> beaconLocations = plugin.getBeacons().iterator();

        while(beaconLocations.hasNext()){
            Location beaconLocation = beaconLocations.next();

            if(beaconLocation.getBlock().getType() != Material.BEACON) {
                beaconLocations.remove();
                continue;
            }

            if(beaconLocation.getWorld() != event.getEntity().getLocation().getWorld()){
                continue;
            }

            Beacon beacon = (Beacon) beaconLocation.getBlock().getState();

            if(beacon.getTier() > 0 &&
                    beacon.getPersistentDataContainer().has(plugin.getHungerKey(), PersistentDataType.INTEGER) &&
                    beacon.getPersistentDataContainer().get(plugin.getHungerKey(), PersistentDataType.INTEGER) == 1){
                int range = 10 + beacon.getTier()*10;

                Location location = event.getEntity().getLocation();

                if(BeaconUtils.isInSquareRangeIgnoreUp(beacon.getLocation(), location, range)){
                    if(event.getItem() == null && !event.getEntity().hasPotionEffect(PotionEffectType.SATURATION)){
                        event.setCancelled(true);
                        break;
                    }
                }
            }
        }
    }
}
