package com.github.sakizciadam.spigotanticheatbase.users;

import com.github.sakizciadam.spigotanticheatbase.events.UserEvents;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class User {
    private final UUID uuid;
    private boolean alerts;

    public boolean onGround;

    private Location lastSafeLocation;

    private Map<UserEvents.EnumAction,Long> actionMap = new HashMap<UserEvents.EnumAction,Long>();


    public int groundTicks, airTicks, liquidTicks;


    public User(UUID uuid){
        this.uuid=uuid;
    }

    public Location getLastSafeLocation(){
        return lastSafeLocation;
    }

    public void setLastSafeLocation(Location lastSafeLocation) {
        this.lastSafeLocation = lastSafeLocation;
    }

    public void addAction(UserEvents.EnumAction enumAction, long time){
        actionMap.put(enumAction,time);
    }

    public void addAction(UserEvents.EnumAction enumAction){
        actionMap.put(enumAction,System.currentTimeMillis());
    }

    public long sinceAction(UserEvents.EnumAction enumAction){
        return actionMap.getOrDefault(enumAction,-1L);
    }

    public boolean getAlert() {
        return alerts;
    }

    public void setAlert(boolean alerts) {
        this.alerts = alerts;
    }

    public Player getPlayer(){
        final Player player = Bukkit.getServer().getPlayer(uuid);
        if(player.isOnline()&&player!=null){
            return player;
        }
        return null;
    }

    public UUID getUUID() {
        return uuid;
    }
}
