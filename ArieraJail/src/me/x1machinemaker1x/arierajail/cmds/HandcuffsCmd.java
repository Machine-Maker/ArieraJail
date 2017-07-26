package me.x1machinemaker1x.arierajail.cmds;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.x1machinemaker1x.arierajail.utils.Messages;

public class HandcuffsCmd implements CommandExecutor {
	
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
		ItemStack handcuffs = new ItemStack(Material.LEASH, 1, (short) 1);
		ItemMeta handCuffMeta = handcuffs.getItemMeta();
		handCuffMeta.setDisplayName(ChatColor.RESET.toString() + ChatColor.BLUE + ChatColor.BOLD + "Handcuffs");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.YELLOW + "Right click on a criminal to handcuff them!");
		handCuffMeta.setLore(lore);
		handcuffs.setItemMeta(handCuffMeta);
		p.getInventory().addItem(handcuffs);
		p.sendMessage(Messages.PREFIX.toString() + Messages.GOT_HANDCUFFS.toString());
		return true;
	}
}
