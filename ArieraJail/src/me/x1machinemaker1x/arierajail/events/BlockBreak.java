package me.x1machinemaker1x.arierajail.events;

import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Signs;

public class BlockBreak implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if ((e.getBlock().getState() instanceof Sign)) {
			if (!Signs.getInstance().isSign(e.getBlock().getLocation())) return;
			Signs.getInstance().delSign(e.getBlock().getLocation());
		}
		else if (Jails.getInstance().getCell(e.getPlayer().getUniqueId()) != null) {
			e.setCancelled(true);
		}
	}
}
