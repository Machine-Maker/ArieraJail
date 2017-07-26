package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class SetCellSpawn extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		Cell cell = Jails.getInstance().getCell(p.getLocation());
		if (cell == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_IN_CELL.toString());
			return;
		}
//		Jail jail = Jails.getInstance().getJail(args[0]);
//		if (jail == null) {
//			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_JAIL.toString());
//			return;
//		}
//		Cell cell = Jails.getInstance().getCell(args[1], jail);
//		if (cell == null) {
//			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_CELL.toString());
//			return;
//		}
//		if (!cell.getSel().contains(p.getLocation())) {
//			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_IN_CELL.toString());
//			return;
//		}
		cell.setCellSpawn(p.getLocation());
		Jails.getInstance().saveJails();
		p.sendMessage(Messages.PREFIX.toString() + Messages.CELLSPAWN_SET.toString().replace("%cellname%", cell.getName()).replace("%jailname%", cell.getJailName()));
	}
	
	public String name() {
		return "setcellspawn";
	}
	
	public String info() {
		return "Sets the spawn in the cell";
	}
	
	public String permission() {
		return "arierajail.setcellspawn";
	}
	
	public String format() {
		return "/aj setcellspawn";
	}
	
	public String[] aliases() {
		return new String[] { "setcs", "scs" };
	}
	
	public int argsReq() {
		return 0;
	}
}
