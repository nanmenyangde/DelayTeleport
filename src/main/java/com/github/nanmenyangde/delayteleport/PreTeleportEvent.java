package com.github.nanmenyangde.delayteleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class PreTeleportEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Location from, to;
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    public PreTeleportEvent(Player player, Location _from, Location _to) {
        super(player, true);
        from = _from;
        to = _to;
    }
    public Location getTo() {
        return to;
    }
    public void setTo(Location to1) {
        to = to1;
    }
    public Location getFrom() {
        return from;
    }
    @Override
    public boolean isCancelled() {
        return cancelled;
    }
    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
