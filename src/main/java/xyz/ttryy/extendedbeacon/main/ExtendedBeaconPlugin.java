package xyz.ttryy.extendedbeacon.main;

import com.google.common.collect.Lists;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Beacon;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.ttryy.extendedbeacon.listener.ChunkListener;
import xyz.ttryy.extendedbeacon.listener.InteractBeaconListener;
import xyz.ttryy.extendedbeacon.listener.MobSpawnListener;

import java.util.List;

public class ExtendedBeaconPlugin extends JavaPlugin {

    private static ExtendedBeaconPlugin instance;

    private NamespacedKey torchedKey;
    private List<Beacon> beacons;

    @Override
    public void onEnable() {
        instance = this;

        torchedKey = new NamespacedKey(this, "torched");
        beacons = Lists.newArrayList();

        saveDefaultConfig();

        initListener();
    }

    private void initListener(){
        new InteractBeaconListener(this);
        new MobSpawnListener(this);
        new ChunkListener(this);
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public NamespacedKey getTorchedKey() {
        return torchedKey;
    }

    public static ExtendedBeaconPlugin getInstance() {
        return instance;
    }
}
