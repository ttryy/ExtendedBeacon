package xyz.ttryy.extendedbeacon.listener;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import xyz.ttryy.extendedbeacon.main.ExtendedBeaconPlugin;
import xyz.ttryy.extendedbeacon.utils.Messages;
import xyz.ttryy.extendedbeacon.utils.Permissions;

import java.util.List;

public class BlockBreakListener implements Listener {

    private ExtendedBeaconPlugin plugin;

    public BlockBreakListener(ExtendedBeaconPlugin plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.BEACON) {
            return;
        }

        if(!Permissions.CAN_BREAK_BEACON.hasPermission(event.getPlayer())){
            return;
        }

        Beacon beacon = (Beacon) event.getBlock().getState();

        boolean hunger = beacon.getPersistentDataContainer().has(plugin.getHungerKey(), PersistentDataType.INTEGER) &&
                beacon.getPersistentDataContainer().get(plugin.getHungerKey(), PersistentDataType.INTEGER) == 1;
        boolean mobSpawning = beacon.getPersistentDataContainer().has(plugin.getTorchedKey(), PersistentDataType.INTEGER) &&
                beacon.getPersistentDataContainer().get(plugin.getTorchedKey(), PersistentDataType.INTEGER) == 1;

        if (hunger || mobSpawning) {
            ItemStack beaconItem = new ItemStack(Material.BEACON, 1);
            ItemMeta beaconMeta = beaconItem.getItemMeta();
            beaconMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            beaconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            List<String> beaconLore = beaconMeta.getLore();
            if(beaconLore == null) beaconLore = Lists.newArrayList();

            if (hunger) {
                beaconLore.add(Messages.HUNGER_ITEMLORE.getMessage());
                beaconMeta.getPersistentDataContainer().set(plugin.getHungerKey(), PersistentDataType.INTEGER, 1);
            }

            if (mobSpawning) {
                beaconLore.add(Messages.MOB_SPAWNING_ITEMLORE.getMessage());
                beaconMeta.getPersistentDataContainer().set(plugin.getTorchedKey(), PersistentDataType.INTEGER, 1);
            }

            beaconMeta.setLore(beaconLore);
            beaconItem.setItemMeta(beaconMeta);

            if (event.isDropItems()) {
                event.setDropItems(false);
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), beaconItem);
            }
        }

    }

}
