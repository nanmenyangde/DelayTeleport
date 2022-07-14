package com.github.nanmenyangde.delayteleport;

import org.bukkit.Bukkit;

import java.util.*;

public class PreTeleportManager {
    private final Set<UUID> players;
    PreTeleportManager() {
        players = Collections.synchronizedSet(new HashSet<>());
    }
    public void addPlayer(UUID player) {
        players.add(player);
    }
    public boolean hasPlayer(UUID player) {
        boolean flag = players.contains(player);
        players.remove(player);
        return flag;
    }
    public void removePlayer(UUID player, boolean message) {
        if (players.contains(player)) {
            players.remove(player);
            if (message) {
                Objects.requireNonNull(Bukkit.getPlayer(player)).sendTitle(ConfigReader.getTeleportSuspendMessage(), "");
            }
        }
    }
}
