package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.ChatColor;
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
		p.sendMessage(ChatColor.GOLD + "=============" + ChatColor.DARK_GREEN + "Jails" + ChatColor.GOLD + "=============");
		for (int i = 0; i < Jails.getInstance().getJails().size(); i++) {
			p.sendMessage(ChatColor.LIGHT_PURPLE.toString() + (i+1) + ":");
			p.sendMessage(" " + ChatColor.GOLD + "Name: " + ChatColor.YELLOW + Jails.getInstance().getJails().get(i).getName());
			String cells = ChatColor.YELLOW.toString();
			if (Jails.getInstance().getJails().get(i).getCells().isEmpty()) {
				cells = ChatColor.RED + "No cells found!";
			}
			else {
				for (Cell cell : Jails.getInstance().getJails().get(i).getCells()) {
					cells += cell.getName() + ", ";
				}
				cells = cells.substring(0, cells.length()-2);
			}
			p.sendMessage(" " + ChatColor.GOLD + "Cells: " + cells);
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
