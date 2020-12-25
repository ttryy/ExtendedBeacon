package xyz.ttryy.extendedbeacon.listener;

import org.bukkit.block.Beacon;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
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
            if(state instanceof Beacon && !plugin.getBeacons().contains((Beacon) state)){
                plugin.getBeacons().add((Beacon) state);
            }
        }
    }

    @EventHandler
    public void onChunkUnload(ChunkUnloadEvent event){
        for(BlockState state : event.getChunk().getTileEntities()){
            if(state instanceof Beacon && plugin.getBeacons().contains((Beacon) state)){
                plugin.getBeacons().remove((Beacon) state);
            }
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event){
        Arrays.stream(event.getWorld().getLoadedChunks()).forEach(chunk -> {
            Arrays.stream(chunk.getTileEntities()).forEach(tileEntity -> {
                if(tileEntity instanceof Beacon && !plugin.getBeacons().contains((Beacon) tileEntity)){
                    plugin.getBeacons().add((Beacon) tileEntity);
                }
            });
        });
    }

    @EventHandler
    public void onWorldUnload(WorldUnloadEvent event){
        Arrays.stream(event.getWorld().getLoadedChunks()).forEach(chunk -> {
            Arrays.stream(chunk.getTileEntities()).forEach(tileEntity -> {
                if(tileEntity instanceof Beacon && plugin.getBeacons().contains((Beacon) tileEntity)){
                    plugin.getBeacons().remove((Beacon) tileEntity);
                }
            });
        });
    }
}
