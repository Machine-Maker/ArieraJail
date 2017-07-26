package me.x1machinemaker1x.arierajail.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.x1machinemaker1x.arierajail.cmds.CreateCell;
import me.x1machinemaker1x.arierajail.cmds.CreateJail;
import me.x1machinemaker1x.arierajail.cmds.CreateSign;
import me.x1machinemaker1x.arierajail.cmds.DeleteCell;
import me.x1machinemaker1x.arierajail.cmds.DeleteJail;
import me.x1machinemaker1x.arierajail.cmds.JailList;
import me.x1machinemaker1x.arierajail.cmds.Reload;
import me.x1machinemaker1x.arierajail.cmds.SetCellSpawn;
import me.x1machinemaker1x.arierajail.cmds.SetRelease;
import me.x1machinemaker1x.arierajail.cmds.SubCommand;


public class Commands implements CommandExecutor {
	private List<SubCommand> commands = new ArrayList<SubCommand>();

	public void setup() {
		commands.add(new CreateCell());
		commands.add(new CreateJail());
		commands.add(new DeleteCell());
		commands.add(new DeleteJail());
		commands.add(new JailList());
		commands.add(new SetRelease());
		commands.add(new SetCellSpawn());
		commands.add(new CreateSign());
		commands.add(new Reload());
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(Messages.PREFIX.toString() + Messages.NOT_PLAYER.toString());
			return true;
		}
		Player p = (Player) cs;
		if (args.length == 0) {
			p.sendMessage(Messages.PREFIX.toString() + "/handcuffs - Gives the player handcuffs");
			for (SubCommand c : this.commands) {
				p.sendMessage(Messages.PREFIX.toString() + c.format() + " (" + aliases(c) + ") - " + c.info());
			}
			return true;
		}
		SubCommand command = get(args[0]);
		if (command == null) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.NOT_COMMAND.toString());
			return true;
		}
		if (!p.hasPermission(command.permission())) {
			cs.sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
			return true;
		}
		List<String> a = new ArrayList<String>();
		a.addAll(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		if (args.length != command.argsReq()) {
			p.sendMessage(Messages.PREFIX.toString() + Messages.USE_FORMAT.toString().replace("%format%", command.format()));
			return true;
		}
		try {
			command.onCommand(p, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private String aliases(SubCommand cmd) {
		String fin = "";
		for (String a : cmd.aliases()) {
			fin += a + " | ";
		}
		return fin.substring(0, fin.lastIndexOf(" | "));
	}

	private SubCommand get(String name) {
		for (SubCommand c : commands) {
			if (c.name().equalsIgnoreCase(name))
				return c;
			for (String alias : c.aliases())
				if (name.equalsIgnoreCase(alias))
					return c;
		}
		return null;
	}
}
