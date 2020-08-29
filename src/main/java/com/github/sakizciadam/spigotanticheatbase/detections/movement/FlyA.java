package com.github.sakizciadam.spigotanticheatbase.detections.movement;

import com.github.sakizciadam.spigotanticheatbase.SpigotAntiCheat;
import com.github.sakizciadam.spigotanticheatbase.detections.Category;
import com.github.sakizciadam.spigotanticheatbase.detections.Detection;
import com.github.sakizciadam.spigotanticheatbase.events.UserEvents;
import com.github.sakizciadam.spigotanticheatbase.users.User;
import com.google.common.util.concurrent.AtomicDouble;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class FlyA extends Detection {

    private double lastOffsetY;
    private int violations;

    public FlyA(String fullName, Category category){
        super(fullName, category);
        violations=0;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        final Player player=event.getPlayer();
        final User user = plugin.getUserManager().get(player);

        if(user.liquidTicks>0)
            return;

        double offsetY = event.getTo().getY() - event.getFrom().getY();

        double diff=offsetY-lastOffsetY;
        if(diff==0&&user.airTicks>6){
            event.setTo(user.getLastSafeLocation());
            flag(player);

            return;
        }
        if(diff>getJumpUpwardsMotion(player)&&user.airTicks>6){
            event.setTo(user.getLastSafeLocation());
            flag(player);
        }

        lastOffsetY=offsetY;
    }

    private float getJumpUpwardsMotion(Player player) {
        int level=0;
        if(player.hasPotionEffect(PotionEffectType.JUMP)){
            PotionEffect potionEffectType=null;
            for(PotionEffect type : player.getActivePotionEffects()){
                if(type.getType().equals(PotionEffectType.JUMP)){
                    potionEffectType=type;
                    break;
                }
            }
            level=potionEffectType.getAmplifier();
        }
        return 0.42F + level * 0.11f;
    }

}
