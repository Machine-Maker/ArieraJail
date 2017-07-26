package me.x1machinemaker1x.arierajail;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.x1machinemaker1x.arierajail.cmds.HandcuffsCmd;
import me.x1machinemaker1x.arierajail.events.BlockBreak;
import me.x1machinemaker1x.arierajail.events.EntityInteract;
import me.x1machinemaker1x.arierajail.events.OnCommand;
import me.x1machinemaker1x.arierajail.events.PlayerDeath;
import me.x1machinemaker1x.arierajail.events.PlayerInteract;
import me.x1machinemaker1x.arierajail.events.PlayerMove;
import me.x1machinemaker1x.arierajail.events.PlayerQuit;
import me.x1machinemaker1x.arierajail.objects.HandcuffTimer;
import me.x1machinemaker1x.arierajail.utils.Commands;
import me.x1machinemaker1x.arierajail.utils.Configs;
import me.x1machinemaker1x.arierajail.utils.Handcuffs;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;
import me.x1machinemaker1x.arierajail.utils.Signs;

public class ArieraJail extends JavaPlugin {
	
	public static WorldEditPlugin worldEdit;
	
	private static Plugin plugin;
	
	private HashMap<UUID, HandcuffTimer> handcuffTimer;
	
	public void onEnable() {
		
		plugin = this;
		
		handcuffTimer = new HashMap<UUID, HandcuffTimer>();
		
		getConfig().options().copyHeader(true);
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		Configs.getInstance().setup(this);
		Messages.setup(this);
		Jails.getInstance().setup(this);
		Handcuffs.getInstance().setup();
		Signs.getInstance().setup(this);
		registerEvents();
		
		worldEdit = getWorldEdit();
		if (worldEdit == null) {
			this.getLogger().severe("WorldEdit not found! Disabling this plugin");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		this.getLogger().info("WorldEdit found! ArieraJail has been successfully hooked in!");
		
		Commands cm = new Commands();
		cm.setup();
				
		getCommand("handcuffs").setExecutor(new HandcuffsCmd());
		getCommand("arierajail").setExecutor(cm);
	}
	
	public void onDisable() {
		Jails.getInstance().saveJails();
		Signs.getInstance().saveSigns();
	}
	
	/**
	 * Adds a handcuffTimer mapping to the hashmap
	 * @param uuid Player's uuid to add
	 * @param runnable The class that extends BukkitRunnable
	 */
	public void addMapping(UUID uuid, HandcuffTimer runnable) {
		handcuffTimer.put(uuid, runnable);
	}
	
	/**
	 * Checks if player is mapped
	 * @param uuid Player's uuid to check
	 * @return true if the player has a mapping
	 */
	public boolean isMapped(UUID uuid) {
		if (handcuffTimer.keySet().contains(uuid)) return true;
		return false;
	}
	
	/**
	 * Gets the BukkitRunnable for the player
	 * @param uuid Player's uuid
	 * @return HandcuffTimer
	 */
	public HandcuffTimer getTask(UUID uuid) {
		return (handcuffTimer.containsKey(uuid)) ? handcuffTimer.get(uuid) : null;
	}
	
	/**
	 * Cancels the deletes the task and mapping for a player
	 * @param uuid Player's uuid to remove
	 */
	public void cancelTaskDelMap(UUID uuid) {
		try { 
			handcuffTimer.get(uuid).cancel();

		} catch (Exception e) { }
		handcuffTimer.remove(uuid);
	}
	
	/**
	 * Gets the WorldEditPlugin
	 * @return WorldEditPlugin
	 */
	private WorldEditPlugin getWorldEdit() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldEdit");
		if ((plugin == null) || (!(plugin instanceof WorldEditPlugin))) return null;
		return (WorldEditPlugin) plugin;
	}
	
	/**
	 * Registers events
	 */
	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new OnCommand(), this);
		pm.registerEvents(new EntityInteract(this), this);
		pm.registerEvents(new PlayerMove(this), this);
		pm.registerEvents(new PlayerQuit(), this);
		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new PlayerInteract(), this);
		pm.registerEvents(new BlockBreak(), this);
	}
	
	/**
	 * Gets the instance of the class that extends JavaPlugin
	 * @return Plugin
	 */
	public static Plugin getPlugin() {
		return plugin;
	}
	/*
	 * TODO:
	 * - (DONE)Make sure handcuffed player isnt already handcuffed
	 * - (DONE)add 5-sec (configurable) timer for handcuffing.
	 * - (DONE) add signs for each cell
	 * - (NOPE)add detection for when entering a jail or cell
	 * - (DONE)add permissions to bypass and use handcuffs
	 * - (DONE)add permissions in plugin.yml
	 * - (DONE)remove debuging command
	 * - (DONE)add config options for length of sentence
	 * - (DONE)add method to give player back all inventory 
	 * - -----restrict player more when handcuffed
	 * 
	 * permissions:
	 * - (DONE)arierajail.createcell
	 * - (DONE)arierajail.createjail
	 * - (DONE)arierajail.createsign
	 * - (DONE)arierajail.deletecell
	 * - (DONE)arierajail.deletejail
	 * - (DONE)arierajail.handcuffs
	 * - (DONE)arierajail.jaillist
	 * - (DONE)arierajail.reload
	 * - (DONE)arierajail.setcellspawn
	 * - (DONE)arierajail.setrelease
	 * 
	 * - (DONE)arierajail.putinjail
	 * - (DONE)arierajail.releasefromjail
	 * - (DONE)arierajail.handcuffs.bypass
	 * - (DONE)arierajail.handcuffs.use
	 * - (DONE)arierajail.playerquit.bypass
	 * 
	 * - (DONE)arierajail.admin (all create/delete commands + reload)
	 * - (DONE)arierajail.* (all commands)
	 * - (DONE)arierajail.cop (handcuffs, putinjail, releasefromjail)
	 * 
	 */
	
}
