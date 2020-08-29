package com.github.sakizciadam.spigotanticheatbase;

import com.github.sakizciadam.spigotanticheatbase.detections.Detection;
import com.github.sakizciadam.spigotanticheatbase.detections.DetectionManager;
import com.github.sakizciadam.spigotanticheatbase.events.UserEvents;
import com.github.sakizciadam.spigotanticheatbase.users.UserManager;
import org.apache.commons.io.IOExceptionWithCause;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class SpigotAntiCheat extends JavaPlugin {

    private static SpigotAntiCheat INSTANCE;

    private UserManager userManager;
    private DetectionManager detectionManager;

    private File checksFile;
    private FileConfiguration checks;

    @Override
    public void onEnable(){
        INSTANCE=this;
        userManager=new UserManager();
        detectionManager=new DetectionManager();
        createCheckConfig();
        Bukkit.getServer().getPluginManager().registerEvents(new UserEvents(),this);
        detectionManager.registerDetections();


    }

    @Override
    public void onDisable(){

    }

    public FileConfiguration getChecksConfig() {
        return checks;
    }

    public void reload(){
        this.onDisable();
        this.onEnable();
    }

    private void createCheckConfig() {
        checksFile = new File(getDataFolder(), "checks.yml");
        if (!checksFile.exists()) {
            checksFile.getParentFile().mkdirs();
            saveResource("checks.yml", false);
        }

        checks= new YamlConfiguration();
        try {
            checks.load(checksFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public static SpigotAntiCheat getSpigotAntiCheat() {
        return INSTANCE;
    }

    public UserManager getUserManager() {
        return userManager;
    }
}
