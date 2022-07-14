package com.github.nanmenyangde.delayteleport;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public final class ConfigReader {
    private static final FileConfiguration config = DelayTeleport.getInstance().getConfig();
    public static String getNoPermissionMessage() {
        return config.getString("noPermissionMessage");
    }
    public static int getTeleportDelaySeconds() {
        return config.getInt("teleportDelaySeconds");
    }
    public static String getPreTeleportMessage() {
        return config.getString("preTeleportMessage");
    }
    public static String getTeleportSuccessMessage() {
        return config.getString("teleportSuccessMessage");
    }
    public static String getTeleportSuspendMessage() {
        return config.getString("teleportSuspendMessage");
    }
    public static int getXCoordinateRange() {
        return config.getInt("XCoordinateRange");
    }
    public static int getZCoordinateRange() {
        return config.getInt("ZCoordinateRange");
    }
    public static List<String> getAllowRtpWorlds() {
        List<?> list = config.getList("allowRtpWorlds");
        if (list == null) list = new ArrayList<String>();
        return (List<String>) list;
    }
    public static String getDenyRtpMessage() {
        return config.getString("denyRtpMessage");
    }
}
