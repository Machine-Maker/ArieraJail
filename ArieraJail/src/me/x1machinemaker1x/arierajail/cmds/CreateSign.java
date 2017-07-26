package me.x1machinemaker1x.arierajail.cmds;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.objects.Jail;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;
import me.x1machinemaker1x.arierajail.utils.Signs;

public class CreateSign extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		Jail jail = Jails.getInstance().getJail(args[0]);
		if (jail == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_JAIL.toString());
			return;
		}
		Cell cell = Jails.getInstance().getCell(args[1], jail);
		if (cell == null) {
			p.sendMessage(Messages.PREFIX + Messages.NOT_CELL.toString());
			return;
		}
		Block b = p.getTargetBlock((Set<Material>) null, 5);
		if (!b.getType().equals(Material.WALL_SIGN) && !b.getType().equals(Material.SIGN_POST)) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_SIGN.toString());
			return;
		}
		Sign sign = (Sign) b.getState();
		sign.setLine(0, ChatColor.DARK_RED + "[" + ChatColor.DARK_GRAY + "ArieraJails" + ChatColor.DARK_RED + "]");
		sign.setLine(1, ChatColor.BLUE + "Jail: " + ChatColor.BLACK + jail.getName());
		sign.setLine(2, ChatColor.BLUE + "Cell: " + ChatColor.BLACK + cell.getName());
		sign.setLine(3, ChatColor.RED + ((cell.getUUID() == null) ? "(empty)" : Bukkit.getPlayer(cell.getUUID()).getName()));
		sign.update();
		Signs.getInstance().addSign(b.getLocation(), cell.getName() + ":" + jail.getName());
		p.sendMessage(Messages.PREFIX.toString() + Messages.SIGN_CREATED.toString().replace("%cellname%", cell.getName()).replace("%jailname%", jail.getName()));
	}
	
	public String name() {
		return "createsign";
	}
	
	public String info() {
		return "Creates a sign for a jail cell";
	}
	
	public String permission() {
		return "arierajail.createsign";
	}
	
	public String format() {
		return "/aj createsign <jail> <cell>";
	}
	
	public String[] aliases() {
		return new String[] { "csign", "cs" };
	}
	
	public int argsReq() {
		return 2;
	}

}
