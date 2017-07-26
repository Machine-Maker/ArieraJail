package me.x1machinemaker1x.arierajail.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.objects.Sign;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;

public class Signs {
	
	private Signs() { }
	
	private static Signs instance = new Signs();
	
	public static Signs getInstance() {
		return instance;
	}
	
	private List<Sign> signs;
	
	/**
	 * Setup for the plugin signs
	 * @param plugin Main plugin instance
	 */
	public void setup(Plugin plugin)	{
		signs = new ArrayList<Sign>();
		ConfigurationSection signConf = Configs.getInstance().getConfig(ConfigType.SIGNS).getConfigurationSection("Signs");
		if (signConf == null) {
			plugin.getLogger().info("No signs were found in signs.yml so no signs were loaded in!");
			return;
		}
		for (String s : signConf.getKeys(false)) {
			Sign sign = Sign.deserialize(signConf.getConfigurationSection(s).getValues(true));
			if (!sign.getLoc().getBlock().getType().equals(Material.WALL_SIGN) && !sign.getLoc().getBlock().getType().equals(Material.SIGN_POST)) {
				sign = null;
			}
			else {
				signs.add(sign);
			}
			saveSigns();
		}
		plugin.getLogger().info(((signs.size() == 1) ? "1 sign was found in signs.yml and was " : signs.size() + " signs were found in signs.yml and were ") + "successfully loaded in!");
	}
	
	/**
	 * Saves the signs to the configs
	 */
	public void saveSigns() {
		Configs.getInstance().getConfig(ConfigType.SIGNS).set("Signs", null);
		Configs.getInstance().saveConfig(ConfigType.SIGNS);
		if (signs.isEmpty()) return;
		ConfigurationSection conf = Configs.getInstance().getConfig(ConfigType.SIGNS).createSection("Signs");
		for (int i = 0; i < signs.size(); i++) {	
			conf.set(String.valueOf(i), signs.get(i).serialize());
			Configs.getInstance().saveConfig(ConfigType.SIGNS);
		}
		Configs.getInstance().saveConfig(ConfigType.SIGNS);
	}
	
	/**
	 * Adds a sign to the sign list
	 * Use {@link #saveSigns()} to write sign data to the config
	 * @param loc Location of the sign
	 * @param cellJail String in "cell:jail" format
	 */
	public void addSign(Location loc, String cellJail) {
		Sign replaceSign = null;
		if (!signs.isEmpty()) {
			for (Sign sign : signs) {
				if (sign.getLoc().equals(loc))
					replaceSign = sign;
			}
		}
		if (replaceSign != null) {
			signs.set(signs.indexOf(replaceSign), new Sign(loc, cellJail));
		}
		else {
			signs.add(new Sign(loc, cellJail));
		}
		saveSigns();
	}
	
	/**
	 * Deletes a sign
	 * @param loc Location to be deleted
	 */
	public void delSign(Location loc) {
		if (signs.isEmpty()) return;
		Iterator<Sign> i = signs.iterator();
		while (i.hasNext()) {
			Sign s = i.next();
			if (s.getLoc().equals(loc))
				signs.remove(s);
		}
		saveSigns();
	}
	
	/**
	 * Checks if location contains a plugin sign
	 * @param loc Location to be checked
	 * @return true if location has a sign
	 */
	public boolean isSign(Location loc) {
		if (signs.isEmpty()) return false;
		for (Sign s : signs) {
			if (s.getLoc().equals(loc)) return true;
		}
		return false;
	}
	
	/**
	 * Gets the sign of the cell
	 * @param cell Cell you want the sign for
	 * @return Sign
	 */
	public Sign getSign(Cell cell) {
		if (signs.isEmpty()) return null;
		for (Sign s : signs) {
			if (s.getJailString().equals(cell.getJailName()) && s.getCellString().equals(cell.getName()))
				return s;
		}
		return null;
	}
	
	/**
	 * Gets the sign at the location
	 * @param loc Location to check
	 * @return Sign
	 */
	public Sign getSign(Location loc) {
		if (signs.isEmpty()) return null;
		for (Sign sign : signs)
			if (sign.getLoc().equals(loc)) return sign;
		return null;
	}
}
