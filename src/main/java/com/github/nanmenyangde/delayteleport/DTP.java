package com.github.nanmenyangde.delayteleport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DTP implements CommandExecutor {
    @Nullable
    private Vector getVector(String x, String y, String z, CommandSender s) {
        Vector place;
        try {
            place = new Vector(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z));
        }catch (NumberFormatException e) {
            s.sendMessage(ChatColor.DARK_RED +"请输入合法的坐标！");
            return null;
        }
        return place;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("DelayTeleport.dtp")) {
                DelayTeleport.sendNoPermissionMessage(player);
                return true;
            }
            World world = null;
            Vector place;
            Player tpPlayer = null;
            if (args.length == 1) {
                tpPlayer = Bukkit.getPlayer(args[0]);
                if (tpPlayer == null) {
                    player.sendMessage(ChatColor.DARK_RED+"输入的玩家名离线或不存在！");
                    return true;
                }
                world = tpPlayer.getWorld();
                place = tpPlayer.getLocation().toVector();
                tpPlayer = player;
            } else if (args.length == 3) {
                place = getVector(args[0], args[1], args[2], player);
                if (place == null) return true;
            } else if (args.length == 4){
                world = Bukkit.getWorld(args[0]);
                if (world == null) {
                    tpPlayer = Bukkit.getPlayer(args[0]);
                    if (tpPlayer == null) {
                        player.sendMessage(ChatColor.DARK_RED+"输入的玩家名离线或不存在，或是输入的世界名错误！");
                        return true;
                    } else {
                        place = getVector(args[1], args[2], args[3], player);
                        if (place == null) return true;
                    }
                } else{
                    place = getVector(args[1], args[2], args[3], player);
                    if (place == null) return true;
                }
            } else if (args.length == 5){
                tpPlayer = Bukkit.getPlayer(args[0]);
                if (tpPlayer == null) {
                    player.sendMessage(ChatColor.DARK_RED+"输入的玩家名离线或不存在！");
                    return true;
                }
                world = Bukkit.getWorld(args[1]);
                if (world == null) {
                    player.sendMessage(ChatColor.DARK_RED+"输入的世界名不存在！");
                    return true;
                }
                place = getVector(args[2], args[3], args[4], player);
                if (place == null) return true;
            } else return false;
            if (tpPlayer == null) tpPlayer = player;
            if (world == null) world = tpPlayer.getWorld();
            if (tpPlayer.getUniqueId() != player.getUniqueId() && !player.hasPermission("DelayTeleport.other")) {
                player.sendMessage(ChatColor.DARK_RED+"你没有权限传送他人！");
                return true;
            }
            tpPlayer.sendTitle(ConfigReader.getPreTeleportMessage(), "");
            DelayTeleport.Teleport(tpPlayer, new Location(world, place.getX(), place.getY(), place.getZ()), ConfigReader.getTeleportDelaySeconds());
            return true;
        }
        sender.sendMessage("该命令仅限玩家使用！");
        return true;
    }
}







