package xyz.ttryy.extendedbeacon.main;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Beacon;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ttryy.extendedbeacon.listener.*;

import java.util.Arrays;
import java.util.List;

public class ExtendedBeaconPlugin extends JavaPlugin {

    private static ExtendedBeaconPlugin instance;

    private NamespacedKey torchedKey;
    private NamespacedKey hungerKey;
    private List<Location> beacons;

    private boolean customReason;
    private List<EntityType> whitelist;

    //adding in default buffs. STH 2022-0210
    private boolean defaultStopMobSpawn;
    private boolean defaultStopHunger;
    //#######

    @Override
    public void onEnable() {
        instance = this;

        torchedKey = new NamespacedKey(this, "torched");
        hungerKey = new NamespacedKey(this, "hunger");
        beacons = Lists.newArrayList();

        saveDefaultConfig();
        loadConfig();

        loadLoadedBeacons();

        initListener();
    }

    private void initListener(){
        new InteractBeaconListener(this);
        new MobSpawnListener(this);
        new ChunkListener(this);
        new HungerListener(this);
        new BlockPlaceListener(this);
        new BlockBreakListener(this);
    }

    private void loadConfig(){
        whitelist = Lists.newArrayList();
        customReason = getConfig().getBoolean("mob_spawning.block_custom_reason", false);
        //adding in default buffs. STH 2022-0210
        defaultStopMobSpawn = getConfig().getBoolean("default.stop_mob_spawning", false);
        defaultStopHunger = getConfig().getBoolean("default.stop_hunger", false);
        //#######
        getConfig().getStringList("mob_spawning.whitelist").forEach(type -> {
            if(EntityType.valueOf(type) != null) whitelist.add(EntityType.valueOf(type));
        });
    }

    private void loadLoadedBeacons(){
        Bukkit.getWorlds().forEach(world -> {
            Arrays.asList(world.getLoadedChunks()).forEach(chunk -> {
                Arrays.asList(chunk.getTileEntities()).forEach(tileEntity -> {
                    if(tileEntity instanceof Beacon && !getBeacons().contains(tileEntity.getLocation())){
                        getBeacons().add(tileEntity.getLocation());
                    }
                });
            });
        });
    }

    public List<Location> getBeacons() {
        return beacons;
    }

    public NamespacedKey getTorchedKey() {
        return torchedKey;
    }

    public NamespacedKey getHungerKey() {
        return hungerKey;
    }

    public boolean isCustomReason() {
        return customReason;
    }

    public List<EntityType> getWhitelist() {
        return whitelist;
    }

    public static ExtendedBeaconPlugin getInstance() {
        return instance;
    }

    public boolean getDefaultStopMobSpawn() {
        return defaultStopMobSpawn;
    }

    public boolean getDefaultStopHunger() {
        return defaultStopHunger;
    }
}
