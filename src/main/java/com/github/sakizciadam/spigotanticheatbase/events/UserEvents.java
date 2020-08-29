package com.github.sakizciadam.spigotanticheatbase.events;

import com.github.sakizciadam.spigotanticheatbase.SpigotAntiCheat;
import com.github.sakizciadam.spigotanticheatbase.users.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class UserEvents implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        final Player player=event.getPlayer();
        final User user = SpigotAntiCheat.getSpigotAntiCheat().getUserManager().get(player);
        Material m = player.getLocation().getBlock().getType();
        if (m == Material.STATIONARY_WATER || m == Material.WATER || m == Material.STATIONARY_LAVA || m == Material.LAVA) {
            user.addAction(EnumAction.IN_LIQUID);
            user.liquidTicks++;
        } else {
            user.liquidTicks=0;
        }
        user.onGround=player.isOnGround();

        if(user.onGround){
            user.groundTicks++;

            user.airTicks=0;
            if(user.sinceAction(EnumAction.FLAGGED)>0&&user.sinceAction(EnumAction.FLAGGED)<20000){
                user.setLastSafeLocation(player.getLocation());
            }
        } else {
            user.groundTicks=0;
            user.airTicks++;
        }




    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event){
        final Player player=event.getPlayer();
        final User user = SpigotAntiCheat.getSpigotAntiCheat().getUserManager().get(player);
        user.addAction(EnumAction.TELEPORT);




    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        final Player player=event.getPlayer();
        SpigotAntiCheat.getSpigotAntiCheat().getUserManager().addUser(player);
        final User user = SpigotAntiCheat.getSpigotAntiCheat().getUserManager().get(player);
        user.setLastSafeLocation(player.getLocation());

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        final Player player=event.getPlayer();
        SpigotAntiCheat.getSpigotAntiCheat().getUserManager().removeUser(SpigotAntiCheat.getSpigotAntiCheat().getUserManager().get(player));


    }

    public enum EnumAction {
        TELEPORT,IN_LIQUID,FLAGGED;
    }
}
