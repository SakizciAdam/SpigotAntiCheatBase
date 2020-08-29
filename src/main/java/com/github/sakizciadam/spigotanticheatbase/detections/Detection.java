package com.github.sakizciadam.spigotanticheatbase.detections;

import com.github.sakizciadam.spigotanticheatbase.SpigotAntiCheat;
import com.github.sakizciadam.spigotanticheatbase.events.UserEvents;
import com.github.sakizciadam.spigotanticheatbase.users.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class Detection implements Listener {
    private String fullName;
    private String name;
    private String type;
    private Category category;
    private HashMap<User,Integer> vl;
    private int maxVL;
    private String punishCommand;
    private boolean resetVLAfterPunish;
    public SpigotAntiCheat plugin;


    public Detection(String fullName,Category category){
        this.plugin=SpigotAntiCheat.getSpigotAntiCheat();
        this.fullName=fullName;
        this.category=category;
        vl=new HashMap<User, Integer>();
        final String[] n = fullName.split(" ");
        name=n[0];
        type=n[1];
        maxVL=plugin.getChecksConfig().getInt(name+".maxVL");
        punishCommand=plugin.getChecksConfig().getString(name+".punish");
        resetVLAfterPunish=plugin.getChecksConfig().getBoolean(name+".resetVLAfterPunish");
        Bukkit.getServer().getPluginManager().registerEvents(this, SpigotAntiCheat.getSpigotAntiCheat());
    }

    public void flag(Player player,String moreInfo){
        final User user = plugin.getUserManager().get(player);
        user.addAction(UserEvents.EnumAction.FLAGGED);
        int vl=getVL(player);
        vl++;
        setVL(player,vl);


        for(Player staffs : Bukkit.getOnlinePlayers()){
            if(staffs.hasPermission("anticheat.alerts")){
                staffs.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e[&6ANTICHEAT&e] &r"+player.getName()+" has been failed "+this.getName()+". &eVL: "+getVL(player)+" More Info: &r"+moreInfo));
            }
        }
        checkPunish(player);
    }

    public void flag(Player player){
        final User user = plugin.getUserManager().get(player);
        user.addAction(UserEvents.EnumAction.FLAGGED);
        int vl=getVL(player);
        vl++;
        setVL(player,vl);


        for(Player staffs : Bukkit.getOnlinePlayers()){
            if(staffs.hasPermission("anticheat.alerts")){
                staffs.sendMessage(ChatColor.translateAlternateColorCodes('&',"&e[&6ANTICHEAT&e] &r"+player.getName()+" has been failed "+this.getName()+". &eVL: "+getVL(player)));
            }
        }
        checkPunish(player);
    }

    public void checkPunish(Player player){
        if(maxVL<0){
            return;
        }
        int intvl=getVL(player);
        if(intvl==0)
            return;

        if(intvl>=maxVL){

            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(),punishCommand.replace("%player%",player.getName()));
                    if(resetVLAfterPunish){
                        vl.put(plugin.getUserManager().get(player),0);
                    }
                }
            });
        }
    }

    public void setVL(Player player,int newvl){
        vl.put(plugin.getUserManager().get(player), newvl);
    }

    public int getVL(Player player){
        return vl.getOrDefault(plugin.getUserManager().get(player),0);
    }

    public int getMaxVL() {
        return maxVL;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return name;
    }

    public Category getCategory() {
        return category;
    }
}
