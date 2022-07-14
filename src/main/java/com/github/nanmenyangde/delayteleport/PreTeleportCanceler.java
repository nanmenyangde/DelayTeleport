package com.github.nanmenyangde.delayteleport;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PreTeleportCanceler implements Listener {
    private void removePlayer(UUID player, boolean message) {
        DelayTeleport.getPreTeleportManager().removePlayer(player, message);
    }
    @EventHandler
    public void move(PlayerMoveEvent event) {
        if (!event.hasChangedPosition()) return;
        removePlayer(event.getPlayer().getUniqueId(), true);
    }
    @EventHandler
    public void quit(PlayerQuitEvent event) {
        removePlayer(event.getPlayer().getUniqueId(), false);
    }
    @EventHandler
    public void attack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            removePlayer(event.getDamager().getUniqueId(), true);
        }
    }
    @EventHandler
    public void injure(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            removePlayer(event.getEntity().getUniqueId(), true);
        }
    }
}
