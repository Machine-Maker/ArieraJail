package me.x1machinemaker1x.arierajail.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.x1machinemaker1x.arierajail.utils.Jails;

public class JailTimer extends BukkitRunnable {
	
	public int counter = 0;
	private final Player player;
	private final Cell cell;
	
	public JailTimer(int counter, Player p, Cell cell) {
		this.counter = counter;
		this.player = p;
		this.cell = cell;
	}

	@Override
	public void run() {
		if (counter > 0) {
			if (counter == 60)
				try {
					player.sendMessage(org.bukkit.ChatColor.GREEN + "1 minute left!");
				} catch (NullPointerException e) { }
			else if (counter == 10)
				try {
					player.sendMessage(org.bukkit.ChatColor.GREEN + "10 seconds left");
				} catch (NullPointerException e) { }
			counter --;
			cell.setCounter(counter);
		}
		else {
			if (Bukkit.getOfflinePlayer(player.getUniqueId()).isOnline()) {
				cell.release();
				cell.clearCell();
				Jails.getInstance().saveJails();
			}
		}
	}
	
	public int timeLeft() {
		return this.counter;
	}
}
