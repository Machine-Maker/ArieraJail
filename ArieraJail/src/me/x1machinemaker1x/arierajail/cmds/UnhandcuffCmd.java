package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.objects.Handcuff;
import me.x1machinemaker1x.arierajail.utils.Handcuffs;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class UnhandcuffCmd implements CommandExecutor {
	
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(Messages.PREFIX.toString() + Messages.NOT_PLAYER.toString());
			return true;
		}
		Player p = (Player) cs;
		if (!p.hasPermission("arierajail.handcuffs")) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
			return true;
		}
		Handcuff h = Handcuffs.getInstance().getHandcuff(p.getUniqueId());
		if (h == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NO_HANDCUFFS.toString());
			return true;
		}
		p.sendMessage(Messages.UNHANDCUFFED_COP.toString().replace("%playername%", h.getCuffee().getName()));
		h.getCuffee().sendMessage(Messages.UNHANDCUFFED_CRIMINAL.toString());
		Handcuffs.getInstance().unCuff(h);
		return true;
	}
}