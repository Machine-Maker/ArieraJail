package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import me.x1machinemaker1x.arierajail.ArieraJail;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class CreateJail extends SubCommand {
	
	public void onCommand(Player p, String[] args) {
		Selection sel = ArieraJail.worldEdit.getSelection(p);
		if (sel == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NO_SELECTION.toString());
			return;
		}
		if (!(sel instanceof CuboidSelection)) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_CUBOID.toString());
			return;
		}
		String name = args[0];
		if (Jails.getInstance().getJail(name) != null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.BAD_NAME.toString());
			return;
		}
		CuboidSelection cSel = (CuboidSelection) sel;
		Jails.getInstance().addJail(name, new CuboidSelection(cSel.getWorld(), cSel.getMinimumPoint(), cSel.getMaximumPoint()));
		p.sendMessage(Messages.PREFIX.toString() + Messages.JAIL_CREATED.toString().replace("%name%", name));
	}
	
	public String name() {
		return "createjail";
	}
	
	public String info() {
		return "Creates a jail";
	}
	
	public String permission() {
		return "arierajail.createjail";
	}
	
	public String format() {
		return "/aj createjail <name>";
	}
	
	public String[] aliases() {
		return new String[] { "cj", "createj" };
	}
	
	public int argsReq() {
		return 1;
	}

}
