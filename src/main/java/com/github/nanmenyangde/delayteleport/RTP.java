package com.github.nanmenyangde.delayteleport;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.Math.random;

public class RTP implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("DelayTeleport.rtp")) {
                DelayTeleport.sendNoPermissionMessage(player);
                return true;
            }
            Player tpPlayer;
            if (args.length == 0) {
                tpPlayer = player;
            } else if (args.length == 1) {
                tpPlayer = Bukkit.getPlayer(args[0]);
                if (tpPlayer == null) {
                    player.sendMessage(ChatColor.DARK_RED+"输入的玩家名离线或不存在！");
                    return true;
                }
                if (tpPlayer.getUniqueId() != player.getUniqueId() && !player.hasPermission("DelayTeleport.other")) {
                    player.sendMessage(ChatColor.DARK_RED+"你没有权限传送他人！");
                    return true;
                }
            }else return false;
            List<String> worlds = ConfigReader.getAllowRtpWorlds();
            if (!worlds.contains(player.getWorld().getName())) {
                tpPlayer.sendMessage(ConfigReader.getDenyRtpMessage());
                return true;
            }
            Location location = tpPlayer.getLocation();
            tpPlayer.sendTitle(ConfigReader.getPreTeleportMessage(), "");
            new BukkitRunnable() {
                @Override
                public void run() {
                    Location to = location.clone();
                    int cnt = 0;
                    int XRange = ConfigReader.getXCoordinateRange(), ZRange = ConfigReader.getZCoordinateRange();
                    Future<Block> block;
                    try {
                        do {
                            double x = (random()*2*XRange)-XRange, z = (random()*2*ZRange)-ZRange;
                            to = location.clone().add(x, 0, z);
                            Location finalTo = to;
                            block = Bukkit.getScheduler().callSyncMethod(DelayTeleport.getInstance(), () -> location.getWorld().getHighestBlockAt(finalTo));
                            cnt++;
                        }while (!block.get().isSolid() && cnt<=20);
                    } catch (InterruptedException | ExecutionException ignored) {}
                    if (cnt>=20) {
                        tpPlayer.sendTitle("§4周围安全位置过少，传送失败！", "");
                        return ;
                    }
                    to.add(0, 1, 0);
                    DelayTeleport.Teleport(tpPlayer, to, ConfigReader.getTeleportDelaySeconds());
                }
            }.runTaskAsynchronously(DelayTeleport.getInstance());
            return true;
        }
        sender.sendMessage("该命令仅限玩家使用！");
        return true;
    }
}
