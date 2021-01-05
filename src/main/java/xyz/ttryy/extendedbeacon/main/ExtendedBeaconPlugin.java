package xyz.ttryy.extendedbeacon.main;

import com.google.common.collect.Lists;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Beacon;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ttryy.extendedbeacon.listener.ChunkListener;
import xyz.ttryy.extendedbeacon.listener.InteractBeaconListener;
import xyz.ttryy.extendedbeacon.listener.MobSpawnListener;

import java.util.List;

public class ExtendedBeaconPlugin extends JavaPlugin {

    private static ExtendedBeaconPlugin instance;

    private NamespacedKey torchedKey;
    private List<Beacon> beacons;

    private boolean customReason;
    private List<EntityType> whitelist;

    @Override
    public void onEnable() {
        instance = this;

        torchedKey = new NamespacedKey(this, "torched");
        beacons = Lists.newArrayList();

        loadConfig();
        saveDefaultConfig();

        initListener();
    }

    private void initListener(){
        new InteractBeaconListener(this);
        new MobSpawnListener(this);
        new ChunkListener(this);
    }

    private void loadConfig(){
        whitelist = Lists.newArrayList();
        customReason = getConfig().getBoolean("mob_spawning.block_custom_reason", false);
        getConfig().getStringList("mob_spawning.whitelist").forEach(type -> {
            if(EntityType.valueOf(type) != null) whitelist.add(EntityType.valueOf(type));
        });
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public NamespacedKey getTorchedKey() {
        return torchedKey;
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
}
