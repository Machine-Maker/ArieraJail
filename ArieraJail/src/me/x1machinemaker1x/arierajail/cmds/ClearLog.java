package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.utils.Configs;
import me.x1machinemaker1x.arierajail.utils.LogFile;
import me.x1machinemaker1x.arierajail.utils.Messages;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;

public class ClearLog extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		Configs.getInstance().getConfig(ConfigType.LOG).set("Log", null);
		Configs.getInstance().saveConfig(ConfigType.LOG);
		LogFile.getInstance().clearLogList();
		p.sendMessage(Messages.PREFIX.toString() + Messages.LOG_CLEARED.toString());
	}
	
	public String name() {
		return "clearlog";
	}
	
	public String info() {
		return "Clears the log file";
	}
	
	public String[] aliases() {
		return new String[] { "clog", "cl" };
	}
	
	public String permission() {
		return "arierajail.clearlog";
	}
	
	public int argsReq() {
		return 0;
	}
	
	public String format() {
		return "/aj clearlog";
	}

}
