package me.x1machinemaker1x.arierajail.utils;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public enum Messages {
	
	PREFIX("prefix", "&4[&7ArieraJails&4] &e"),
	NO_PERMISSION("no-permission", "&cYou do not have permission!"),
	NOT_PLAYER("not-player", "&cYou must be a player to use that command!"),
	NOT_COMMAND("not-command", "&cThat is not a valid command!"),
	USE_FORMAT("use-format", "&eUse format: %format%"),
	BAD_NAME("bad-name", "&cThat name is already in use!"),
	NO_SELECTION("no-selection", "&cYou must have an area selected!"),
	NOT_CUBOID("not-cuboid", "&cThe selection must be a cuboid!"),
	NOT_JAIL("not-jail", "&cThat is not a valid jail!"),
	NOT_CELL("not-cell", "&cThat is not a valid cell for that jail!"),
	JAIL_CREATED("jail-created", "&aYou have created the jail %name%"),
	JAIL_DELETED("jail-deleted", "&aYou have deleted the jail %name%"),
	CELL_CREATED("cell-created", "&aYou have created the cell %cellname% for jail %jailname%!"),
	CELL_DELETED("cell-deleted", "&aYou have deleted the cell %cellname% from jail %jailname%!"),
	NOT_IN_CELL("not-in-cell", "&cThis location is not in the cell area!"),
	RELEASE_SET("release-set", "&aThe release point for jail %name% has been set to your location!"),
	CELLSPAWN_SET("cellspawn-set", "&aThe cell spawn for cell %cellname% in jail %jailname% has been set!"),
	NO_RELEASE_SET("no-release-set", "&cNo release point has been set for this jail!"),
	
	NOT_SIGN("not-sign", "&cThe block you have targeted is not a sign!"),
	SIGN_CREATED("sign-created", "&aYou have successfully created a sign for the cell %cellname% in the jail %jailname%"),
	
	RELOAD_CONFIG("reload-config", "&a%filename% %has/have% been reloaded"),
	NOT_CONFIG("not-config", "&cThat is not a valid configuration!"),
	NO_JAILS("no-jails", "&cNo jails have been created!"),
	
	GOT_HANDCUFFS("got-handcuffs", "&aYou have received handcuffs"),
	ALREADY_HANDCUFFED("already-handcuffed", "&cThat player is already handcuffed!"),
	YOU_MOVED_CRIMINAL("you-moved.criminal", "&aYou moved and broke the handcuffs!"),
	YOU_MOVED_COP("you-moved.cop", "&a%playername% moved and the handcuffs came off!"),
	ATTEMPT_HANDCUFF_CRIMINAL("attempt-handcuff.criminal", "&6%playername% is trying to put handcuffs on you! You will be cuffed in %seconds% seconds!"),
	ATTEMPT_HANDCUFF_COP("attempt-handcuff.cop", "&aYou are trying to handcuff %playername%! They must not move for %seconds% seconds!"),
	HANDCUFFED_CRIMINAL("handcuffed.criminal", "&aYou have been handcuffed!"),
	HANDCUFFED_COP("handcuffed.cop", "&aYou handcuffed %playername%!"),
	IN_HANDCUFFS("in-handcuffs", "&aYou are in handcuffs!"),
	TOO_FAR("too-far", "&aYou got too far away! Teleporting you to %name%!"),
	NO_HANDCUFFS("no-handcuffs", "&cYou have not handcuffed a person!"),
	UNHANDCUFFED_COP("unhandcuffed.cop", "&aYou have unhandcuffed %playername%!"),
	UNHANDCUFFED_CRIMINAL("unhandcuffed.criminal", "&aYou have been unhandcuffed!"),
	
	CELL_FULL("cell-full", "&cThat cell is already occupied!"),
	CELL_EMPTY("cell-empty", "&cThat cell is empty!"),
	NO_SPAWN("no-spawn", "&cNo spawn point has been set for that cell!"),
	CRINIMAL_LOCKED_UP("locked-up.criminal", "&aYou have been locked up!"),
	COP_LOCKED_UP("locked-up.cop", "&a%playername% was put into jail!"),
	CRIMINAL_RELEASED("released.criminal", "&aYou have been released! Don't get caught again :)"),
	COP_RELEASED("released.cop", "&aYou have released %playername%");
	
	String path;
	String def;
	
	Messages(String path, String def) {
		this.path = path;
		this.def = def;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getDefault() {
		return this.def;
	}
	
	public void setMessage(String message) {
		this.def = message;
	}
	
	public static FileConfiguration mconfig;
	public static File mfile;
	
	public static void setup(Plugin plugin) {
		mfile = new File(plugin.getDataFolder(), "messages.yml");
		if (!mfile.exists()) {
			try {
				plugin.getDataFolder().mkdir();
				mfile.createNewFile();
			} catch (Exception e) {
				plugin.getLogger().severe("Could not create messages.yml!");
			}
		}
		mconfig = YamlConfiguration.loadConfiguration(mfile);
		for (Messages message : Messages.values())
			if (!mconfig.isSet(message.getPath()))
				mconfig.set(message.getPath(), message.getDefault());
			else
				if (!mconfig.getString(message.getPath()).equals(message.getDefault()))
					message.setMessage(mconfig.getString(message.getPath()));
		try {
			mconfig.save(mfile);
		} catch (Exception e) {
			plugin.getLogger().severe("Could not save messages.yml");
		}
	}
	
	public static void reloadMessages() {
		mconfig = YamlConfiguration.loadConfiguration(mfile);
	}
	
	@Override
	public String toString() {
		return ChatColor.translateAlternateColorCodes('&', this.getDefault());
	}
}
