package xyz.ttryy.extendedbeacon.listener;

//import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
//import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
//import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
//import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
//import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.ttryy.extendedbeacon.main.ExtendedBeaconPlugin;
import xyz.ttryy.extendedbeacon.utils.Messages;

//import java.util.List;

public class BlockPlaceListener implements Listener {

    private ExtendedBeaconPlugin plugin;

    public BlockPlaceListener(ExtendedBeaconPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() != Material.BEACON) {
            return;
        }

        Beacon beacon = (Beacon) event.getBlock().getState();
        ItemStack beaconItem = event.getItemInHand();

        boolean hunger = beaconItem.getItemMeta().getPersistentDataContainer().has(plugin.getHungerKey(), PersistentDataType.INTEGER) &&
                beaconItem.getItemMeta().getPersistentDataContainer().get(plugin.getHungerKey(), PersistentDataType.INTEGER) == 1;
        boolean mobSpawning = beaconItem.getItemMeta().getPersistentDataContainer().has(plugin.getTorchedKey(), PersistentDataType.INTEGER) &&
                beaconItem.getItemMeta().getPersistentDataContainer().get(plugin.getTorchedKey(), PersistentDataType.INTEGER) == 1;


        if(hunger || mobSpawning){
            if (hunger) {
                beacon.getPersistentDataContainer().set(plugin.getHungerKey(), PersistentDataType.INTEGER, 1);
            }

            if (mobSpawning) {
                beacon.getPersistentDataContainer().set(plugin.getTorchedKey(), PersistentDataType.INTEGER, 1);
            }

            if(!plugin.getBeacons().contains(beacon.getLocation())){
                plugin.getBeacons().add(beacon.getLocation());
            }
            beacon.update();
        }

        //adding in default buffs. STH 2022-0210
        boolean defaultMobSpawning = plugin.getDefaultStopMobSpawn();
        boolean defaultHunger = plugin.getDefaultStopHunger();
        if(defaultHunger || defaultMobSpawning){
            if (defaultMobSpawning) {
                beacon.getPersistentDataContainer().set(plugin.getTorchedKey(), PersistentDataType.INTEGER, 1);
                Messages.NO_MOB_SPAWNING.send(event.getPlayer());
                if(!plugin.getBeacons().contains(beacon.getLocation())){
                    plugin.getBeacons().add(beacon.getLocation());
                }
            }
            if (defaultHunger) {
                beacon.getPersistentDataContainer().set(plugin.getHungerKey(), PersistentDataType.INTEGER, 1);
                Messages.NO_HUNGER.send(event.getPlayer());
                if(!plugin.getBeacons().contains(beacon.getLocation())){
                    plugin.getBeacons().add(beacon.getLocation());
                }
            }
            beacon.update();
        }
        //#############

    }

}

