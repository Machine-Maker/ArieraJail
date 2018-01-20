package me.x1machinemaker1x.arierajail.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.plugin.Plugin;

import me.x1machinemaker1x.arierajail.objects.Log;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;

public class LogFile {

    private LogFile() { }

    private static LogFile instance = new LogFile();

    public static LogFile getInstance() { return instance; }

    private List<Log> logList;

    @SuppressWarnings("unchecked")
	public void setup(Plugin plugin) {
        logList = new ArrayList<>();
        
        List<Map<?,?>> logMapList = Configs.getInstance().getConfig(ConfigType.LOG).getMapList("Log");
        for (Map<?,?> map : logMapList) {
        	logList.add(Log.deserialize((Map<String, Object>) map));
        }
    }
    
    /**
     * Adds a new Log entry into the log file
     * @param playerName arrested player name
     * @param playerUUID arrested player uuid
     * @param arrestedBy cop who made the arrest
     */
    public void addLogEntry(String playerName, UUID playerUUID,  String arrestedBy) {
    	logList.add(new Log(playerName, playerUUID, getInfractionNumber(playerUUID), arrestedBy));
    	saveLog();
    }
    
    /**
     * Gets currently active log for player
     * @param playerUUID player's uuid
     * @return active log
     */
    public Log getActiveLog(UUID playerUUID) {
    	if (logList.isEmpty()) return null;
    	for (Log log : logList) {
    		if (!log.isReleased()) {
    			if (log.getUUID().equals(playerUUID)) {
    				return log;
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * Gets all the log entries for a player
     * @param playerUUID player's UUID
     * @return List of all log entries
     */
    public List<Log> getLogsForPlayer(UUID playerUUID) {
    	List<Log> playerLogList = new ArrayList<Log>();
    	if (logList.isEmpty()) return playerLogList;
    	for (Log log : logList) {
    		if (log.getUUID().equals(playerUUID)) {
    			playerLogList.add(log);
    		}
    	}
    	return playerLogList;
    }
    
    public List<Log> getAllLogs() {
    	return logList;
    }
    
    /**
     * saves Logs
     */
    public void saveLog() {
    	Configs.getInstance().getConfig(ConfigType.LOG).set("Log", null);
    	Configs.getInstance().saveConfig(ConfigType.LOG);
    	if (logList.isEmpty()) return;
    	List<Map<?,?>> logMapList  = new ArrayList<Map<?,?>>();
    	for (Log log : logList) {
    		logMapList.add(log.serialize());
    	}
    	Configs.getInstance().getConfig(ConfigType.LOG).set("Log", logMapList);
    	Configs.getInstance().saveConfig(ConfigType.LOG);
    }
    
    public void clearLogList() {
    	logList.clear();
    }
    
    private int getInfractionNumber(UUID playerUUID) {
    	int number = 1;
    	for (Log log : logList) {
    		if (log.getUUID().equals(playerUUID)) {
    			number++;
    		}
    	}
    	return number;
    }

}
