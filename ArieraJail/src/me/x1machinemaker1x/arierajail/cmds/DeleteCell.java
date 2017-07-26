package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.objects.Jail;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class DeleteCell extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		Jail jail = Jails.getInstance().getJail(args[0]);
		if (jail == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_JAIL.toString());
			return;
		}
		Cell cell = Jails.getInstance().getCell(args[1], jail);
		if (cell == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_CELL.toString());
			return;
		}
		Jails.getInstance().delCell(cell, jail);
		p.sendMessage(Messages.PREFIX.toString() + Messages.CELL_DELETED.toString().replace("%cellname%", cell.getName()).replace("%jailname%", jail.getName()));
	}
	
	public String name() {
		return "deletecell";
	}
	
	public String info() {
		return "Deletes a cell in a jail";
	}
	
	public String permission() {
		return "arierajail.deletecell";
	}
	
	public String format() {
		return "/aj deletecell <jail> <cell>";
	}
	
	public String[] aliases() {
		return new String[] { "delcell","dc" };
	}
	
	public int argsReq() {
		return 2;
	}

}
