package me.x1machinemaker1x.arierajail.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.x1machinemaker1x.arierajail.cmds.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


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
		commands.add(new LogCmd());
		commands.add(new ClearLog());
	}

	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if (!(cs instanceof Player)) {
			cs.sendMessage(Messages.PREFIX.toString() + Messages.NOT_PLAYER.toString());
			return true;
		}
		Player p = (Player) cs;
		if (args.length == 0) {
			if (p.hasPermission("arierajail.handcuffs")) {
				p.sendMessage(Messages.PREFIX.toString() + "/handcuffs (cuffs) - Gives the player handcuffs");
				p.sendMessage(Messages.PREFIX.toString() + "/unhandcuff (uncuff) - Unhandcuffs the player");
			}
			if (p.hasPermission("arierajail.timeleft")) {
				p.sendMessage(Messages.PREFIX.toString() + "/timeleft (tl) - Shows time left in your sentence");
			}
			for (SubCommand c : this.commands) {
				if (p.hasPermission(c.permission())) {
					p.sendMessage(Messages.PREFIX.toString() + c.format() + " (" + aliases(c) + ") - " + c.info());
				}
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
