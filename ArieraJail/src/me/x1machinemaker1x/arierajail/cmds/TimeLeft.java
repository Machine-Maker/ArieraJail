package me.x1machinemaker1x.arierajail.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class TimeLeft implements CommandExecutor {
	
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(Messages.PREFIX.toString() + Messages.NOT_PLAYER.toString());
			return true;
		}
		Player p = (Player) cs;
		if (!p.hasPermission("arierajail.timeleft")) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
			return true;
		}
		Cell cell = Jails.getInstance().getCell(p.getUniqueId());
		if (cell == null) {
			p.sendMessage(Messages.NOT_IN_JAIL.toString());
			return true;
		}
		p.sendMessage(Messages.TIME_LEFT.toString().replace("%time%", Messages.convertTime(cell.getJailTimer().timeLeft())));
		return true;
	}
}
