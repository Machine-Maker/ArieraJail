package me.x1machinemaker1x.arierajail.utils;

import me.x1machinemaker1x.arierajail.objects.Log;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LogFile {

    private LogFile() { }

    private static LogFile instance = new LogFile();

    public static LogFile getInstance() { return instance; }

    private List<Log> logList;

    public static FileConfiguration fconfig;
    public static File ffile;

    public void setup(Plugin plugin) {
        logList = new ArrayList<>();
        ffile = new File(plugin.getDataFolder(), "log.yml");
        if (!ffile.exists()) {
            try {
                plugin.getDataFolder().mkdir();
                ffile.createNewFile();
            } catch (Exception e) {
                plugin.getLogger().severe("Could not create log.yml!");
            }
        }
        fconfig = YamlConfiguration.loadConfiguration(ffile);
    }

    public FileConfiguration getLog() { return fconfig; }

    public void reloadLog() {
        fconfig = YamlConfiguration.loadConfiguration(ffile);
    }

    public void saveLog() {
        try {
            fconfig.save(ffile);
        } catch (Exception e) {
            Bukkit.getLogger().severe("[ArieraFireFighters] Could not save log.yml!");
        }
    }
}
