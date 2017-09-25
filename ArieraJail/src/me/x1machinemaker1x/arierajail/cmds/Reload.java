package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.utils.Configs;
import me.x1machinemaker1x.arierajail.utils.Messages;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;

public class Reload extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		String fileName = args[0].toLowerCase();
		switch (fileName) {
		case "messages":
			Messages.reloadMessages();
			break;
		case "config":
			Configs.getInstance().reloadConfig(ConfigType.CONFIG);
			break;
		case "jails":
			Configs.getInstance().reloadConfig(ConfigType.JAILS);
			break;
		case "signs":
			Configs.getInstance().reloadConfig(ConfigType.SIGNS);
			break;
		case "all":
			Messages.reloadMessages();
			Configs.getInstance().reloadConfig(ConfigType.CONFIG);
			Configs.getInstance().reloadConfig(ConfigType.JAILS);
			Configs.getInstance().reloadConfig(ConfigType.SIGNS);
			break;
		default: 
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_CONFIG.toString());
			return;
		}
		p.sendMessage(Messages.PREFIX.toString() + Messages.RELOAD_CONFIG.toString().replaceAll("%filename%", (fileName.equals("all") ? "All files" : fileName + ".yml")).replace("%has/have%", (fileName.equals("all")) ? "have" : "has"));
	}
	
	public String name() {
		return "reload";
	}
	
	public String info() {
		return "Reloads the config files";
	}
	
	public String permission() {
		return "arierajail.reload";
	}
	
	public String format() {
		return "/aj reload <messages, config, jails, all>";
	}
	
	public String[] aliases() {
		return new String[] { "rel", "r" };
	}
	
	public int argsReq() {
		return 1;
	}

}
