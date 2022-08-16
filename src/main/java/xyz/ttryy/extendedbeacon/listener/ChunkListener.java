package xyz.ttryy.extendedbeacon.listener;

import org.bukkit.block.Beacon;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.*;
import xyz.ttryy.extendedbeacon.main.ExtendedBeaconPlugin;

import java.util.Arrays;

public class ChunkListener implements Listener {

    private ExtendedBeaconPlugin plugin;

    public ChunkListener(ExtendedBeaconPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event){
        for(BlockState state : event.getChunk().getTileEntities()){
            if(state instanceof Beacon && !plugin.getBeacons().contains(state.getLocation())){
                plugin.getBeacons().add(state.getLocation());
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event){
        for(BlockState state : event.getChunk().getTileEntities()){
            if(state instanceof Beacon && plugin.getBeacons().contains(state.getLocation())){
                plugin.getBeacons().remove(state.getLocation());
            }
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event){
        Arrays.stream(event.getWorld().getLoadedChunks()).forEach(chunk -> {
            Arrays.stream(chunk.getTileEntities()).forEach(tileEntity -> {
                if(tileEntity instanceof Beacon && !plugin.getBeacons().contains(tileEntity.getLocation())){
                    plugin.getBeacons().add(tileEntity.getLocation());
                }
            });
        });
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event){
        Arrays.stream(event.getWorld().getLoadedChunks()).forEach(chunk -> {
            Arrays.stream(chunk.getTileEntities()).forEach(tileEntity -> {
                if(tileEntity instanceof Beacon && plugin.getBeacons().contains(tileEntity.getLocation())){
                    plugin.getBeacons().remove(tileEntity.getLocation());
                }
            });
        });
    }
}
