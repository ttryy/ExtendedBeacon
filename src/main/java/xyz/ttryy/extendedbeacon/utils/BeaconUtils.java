package xyz.ttryy.extendedbeacon.utils;

import org.bukkit.Location;

public class BeaconUtils {

    public static boolean isInSquareRangeIgnoreUp(Location beaconLoc, Location loc, int range){
        double y = beaconLoc.getY() - loc.getY();
        double x = beaconLoc.getX() - loc.getX();
        double z = beaconLoc.getZ() - loc.getZ();

        return Math.abs(y) < range*2 && Math.abs(x) < range && Math.abs(z) < range;
    }

}
