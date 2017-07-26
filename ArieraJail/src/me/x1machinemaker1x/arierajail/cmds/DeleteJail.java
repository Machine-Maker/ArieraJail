package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.objects.Jail;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class DeleteJail extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		Jail jail = Jails.getInstance().getJail(args[0]);
		if (jail == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_JAIL.toString());
			return;
		}
		Jails.getInstance().delJail(jail);
		p.sendMessage(Messages.PREFIX.toString() + Messages.JAIL_DELETED.toString().replace("%name%", args[0]));
	}
	
	public String name() {
		return "deletejail";
	}
	
	public String info() {
		return "Deletes a jail";
	}
	
	public String permission() {
		return "arierajail.deletejail";
	}
	
	public String format() {
		return "/aj deletejail <name>";
	}
	
	public String[] aliases() {
		return new String[] { "deljail", "dj" };
	}
	
	public int argsReq() {
		return 1;
	}

}
