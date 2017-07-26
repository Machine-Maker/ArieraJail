package me.x1machinemaker1x.arierajail.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.objects.Jail;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;

public class Jails {
	
	private Jails() { }
	
	private static Jails instance = new Jails();
	
	public static Jails getInstance() {
		return instance;
	}
	
	private List<Jail> jails;
	
	/**
	 * Setup method for all jails
	 * @param plugin
	 */
	public void setup(Plugin plugin) {
		jails = new ArrayList<Jail>();
		ConfigurationSection conf = Configs.getInstance().getConfig(ConfigType.JAILS).getConfigurationSection("Jails");
		if (conf == null) {
			plugin.getLogger().info("No jails were found in jails.yml so no jails were loaded in!"); 
			return;
		}
		for (String name : conf.getKeys(false)) {
			jails.add(Jail.deserialize(conf.getConfigurationSection(name).getValues(true)));
		}
		plugin.getLogger().info((jails.size() == 1) ? "1 jail was found and was successfully loaded!" : jails.size() + " jails were found and successfully loaded!");
	}
	
	/**
	 * Saves the jails to jails.yml
	 */
	public void saveJails() {
		Configs.getInstance().getConfig(ConfigType.JAILS).set("Jails", null);
		Configs.getInstance().saveConfig(ConfigType.JAILS);
		if (jails.isEmpty()) return;
		ConfigurationSection conf = Configs.getInstance().getConfig(ConfigType.JAILS).createSection("Jails");
		for (Jail jail : jails) {
			conf.set(jail.getName(), jail.serialize());
		}
		Configs.getInstance().saveConfig(ConfigType.JAILS);
	}
	
	/**
	 * Adds a jail to the internal jail list.
	 * You must run {@link saveJails()} to save them to the config
	 * @param name Name of the Jail
	 * @param sel Worldedit selection of the jail
	 */
	public void addJail(String name, CuboidSelection sel) {
		jails.add(new Jail(name, sel));
		saveJails();
	}
	
	/**
	 * Deletes a jail
	 * @param jail Jail to delte
	 */
	public void delJail(Jail jail) {
		jails.remove(jail);
		saveJails();
	}
	
	/**
	 * Adds a cell to a jail
	 * @param name Name of the cell
	 * @param sel Worldedit selection of the cell
	 * @param jail Jail to add the cell to
	 */
	public void addCell(String name, CuboidSelection sel, Jail jail) {
		jail.addCell(new Cell(name, sel, jail.getName()));
		saveJails();
	}
	
	/**
	 * Deletes a cell from a jail
	 * @param cell Cell to delete
	 * @param jail Jail to delete cell from
	 */
	public void delCell(Cell cell, Jail jail) {
		jail.delCell(cell);
		saveJails();
	}
	
	/**
	 * Gets the Jail object
	 * @param name Name of jail
	 * @return Jail
	 */
	public Jail getJail(String name) {
		if (jails.isEmpty()) return null;
		for (Jail jail : jails) {
			if (jail.getName().equals(name)) {
				return jail;
			}
		}
		return null;
	}
	
	/**
	 * Gets the Cell object
	 * @param name Name of the cell
	 * @param jail Jail where the cell is
	 * @return Cell
	 */
	public Cell getCell(String name, Jail jail) {
		if (jail.getCells().isEmpty()) return null;
		for (Cell cell : jail.getCells()) {
			if (cell.getName().equals(name))
				return cell;
		}
		return null;
	}
	
	/**
	 * Gets the cell for the player
	 * @param uuid Player's uuid
	 * @return Cell
	 */
	public Cell getCell(UUID uuid) {
		if (jails.isEmpty()) return null;
		for (Jail jail : jails)
			if (!jail.getCells().isEmpty())
				for (Cell cell : jail.getCells())
					if (cell.getUUID() != null)
						if (cell.getUUID().equals(uuid))
							return cell;
		return null;
	}
	
	/**
	 * Gets the cell that this location is in
	 * @param loc Location to check
	 * @return Cell
	 */
	public Cell getCell(Location loc) {
		if (jails.isEmpty()) return null;
		for (Jail jail : jails)
			if (!jail.getCells().isEmpty())
				for (Cell cell : jail.getCells())
					if (cell.getSel().contains(loc))
						return cell;
		return null;
	}
	
	/**
	 * If there are no jails
	 * @return true if there are no jails
	 */
	public boolean jailsEmpty() {
		return jails.isEmpty();
	}
	
	/**
	 * Gets the list of jails
	 * @return List of jails
	 */
	public List<Jail> getJails() {
		return jails;
	}
}
