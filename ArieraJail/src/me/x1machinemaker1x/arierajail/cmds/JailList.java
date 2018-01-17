package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class JailList extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		if (Jails.getInstance().jailsEmpty()) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NO_JAILS.toString());
			return;
		}
		p.sendMessage("?6=============?2Jails?6=============");
		for (int i = 0; i < Jails.getInstance().getJails().size(); i++) {
			p.sendMessage("?d" + (i+1) + ":");
			p.sendMessage(" ?6Name: ?e" + Jails.getInstance().getJails().get(i).getName());
			String cells = "?e";
			if (Jails.getInstance().getJails().get(i).getCells().isEmpty()) {
				cells = "?cNo cells found!";
			}
			else {
				for (Cell cell : Jails.getInstance().getJails().get(i).getCells()) {
					cells += cell.getName() + ", ";
				}
				cells = cells.substring(0, cells.length()-2);
			}
			p.sendMessage(" ?6Cells: " + cells);
		}
	}
	
	public String name() {
		return "jaillist";
	}
	
	public String info() {
		return "Lists all the jails and their cells";
	}
	
	public String format() {
		return "/aj jaillist";
	}
	
	public String permission() {
		return "arierajail.jaillist";
	}
	
	public String[] aliases() {
		return new String[] { "jlist", "jl" };
	}
	
	public int argsReq() {
		return 0;
	}
}
