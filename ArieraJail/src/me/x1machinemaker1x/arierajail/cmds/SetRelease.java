package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.objects.Jail;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class SetRelease extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		Jail jail = Jails.getInstance().getJail(args[0]);
		if (jail == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_JAIL.toString());
			return;
		}
		jail.setRelease(p.getLocation());
		Jails.getInstance().saveJails();
		p.sendMessage(Messages.PREFIX.toString() + Messages.RELEASE_SET.toString().replace("%name%", jail.getName()));
	}
	
	public String name() {
		return "setrelease";
	}
	
	public String info() {
		return "Sets the release point for a jail";
	}
	
	public String permission() {
		return "arierajail.setrelease";
	}
	
	public String format() {
		return "/aj setrelease <jail>";
	}
	
	public String[] aliases() {
		return new String[] { "setr", "sr" };
	}
	
	public int argsReq() {
		return 1;
	}

}
