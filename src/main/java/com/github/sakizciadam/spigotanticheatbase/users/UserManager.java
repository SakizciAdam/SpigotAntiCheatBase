package com.github.sakizciadam.spigotanticheatbase.users;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static List<User> userList;


    public UserManager(){
        userList=new ArrayList<User>();
    }

    public User get(Player player){
        for(User users : userList){
            if(users.getPlayer()!=null){
                if(users.getPlayer().equals(player)){
                    return users;
                }
            }
        }
        //Impossible
        return null;
    }

    public void addUser(Player player){
        boolean found=false;
        for(User users : userList){
            if(users.getPlayer()!=null){
                if(users.getPlayer().equals(player)){
                    found=true;
                }
            }
        }
        if(!found){
            userList.add(new User(player.getUniqueId()));
        }
    }

    public void addUser(User user){
        if(!userList.contains(user)){
            userList.add(user);
        }

    }

    public void removeUser(Player player){
        boolean found=false;
        for(User users : userList){
            if(users.getPlayer()!=null){
                if(users.getPlayer().equals(player)){
                    userList.remove(users);
                }
            }
        }

    }

    public void removeUser(User user){
        if(userList.contains(user)){
            userList.remove(user);
        }

    }


}
