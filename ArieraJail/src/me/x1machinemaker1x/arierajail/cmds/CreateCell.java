package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.entity.Player;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.bukkit.selections.Selection;

import me.x1machinemaker1x.arierajail.ArieraJail;
import me.x1machinemaker1x.arierajail.objects.Jail;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class CreateCell extends SubCommand {
	
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
		CuboidSelection cSel = (CuboidSelection) sel;
		Jail jail = Jails.getInstance().getJail(args[0]);
		if (jail == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_JAIL.toString());
			return;
		}
		String name = args[1];
		if (Jails.getInstance().getCell(name, jail) != null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.BAD_NAME.toString());
			return;
		}
		Jails.getInstance().addCell(name, new CuboidSelection(cSel.getWorld(), cSel.getMinimumPoint(), cSel.getMaximumPoint()), jail);
		p.sendMessage(Messages.PREFIX.toString() + Messages.CELL_CREATED.toString().replace("%cellname%", name).replace("%jailname%", jail.getName()));
	}
	
	public String name() {
		return "createcell";
	}
	
	public String info() {
		return "Creates a cell in a jail";
	}
	
	public String permission() {
		return "arierajail.createcell";
	}
	
	public String format() {
		return "/aj createcell <jail> <cellname>";
	}
	
	public String[] aliases() {
		return new String[] { "cc", "createc" };
	}
	
	public int argsReq() {
		return 2;
	}

}
