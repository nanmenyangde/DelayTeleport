package com.github.nanmenyangde.delayteleport;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public final class DelayTeleport extends JavaPlugin {
    private static JavaPlugin instance;
    private static PreTeleportManager preTeleportManager;
    static JavaPlugin getInstance() {return instance;}
    static PreTeleportManager getPreTeleportManager() {return preTeleportManager;}
    public void onLoad() {
        saveDefaultConfig();
    }
    @Override
    public void onEnable() {
        instance = this;
        preTeleportManager = new PreTeleportManager();
        Bukkit.getPluginManager().registerEvents(new PreTeleportCanceler(), this);
        Objects.requireNonNull(Bukkit.getPluginCommand("dtp")).setExecutor(new DTP());
        Objects.requireNonNull(Bukkit.getPluginCommand("rtp")).setExecutor(new RTP());
    }
    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
    public static void sendNoPermissionMessage(Player player) {
        player.sendMessage(ConfigReader.getNoPermissionMessage());
    }
    public static void Teleport(Player player, Location location, int delay) {
        DelayTeleport.getPreTeleportManager().addPlayer(player.getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                Location to = location;
                PreTeleportEvent event = new PreTeleportEvent(player, player.getLocation(), to);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    return ;
                }
                to = event.getTo();
                if (!DelayTeleport.getPreTeleportManager().hasPlayer(player.getUniqueId())) return ;
                Location finalTo = to;
                Bukkit.getScheduler().runTask(DelayTeleport.getInstance(), ()-> player.teleportAsync(finalTo));
                player.sendTitle(ConfigReader.getTeleportSuccessMessage(), "");
            }
        }.runTaskLaterAsynchronously(DelayTeleport.getInstance(), delay* 20L);
    }
}
