package me.x1machinemaker1x.arierajail.objects;

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
				player.sendMessage("§a1 minute left!");
			else if (counter == 10) 
				player.sendMessage("§a10 seconds left");
			cell.setCounter(counter);
			counter --;
		}
		else {
			cell.release();
			cell.clearCell();
			Jails.getInstance().saveJails();
		}
	}
}
