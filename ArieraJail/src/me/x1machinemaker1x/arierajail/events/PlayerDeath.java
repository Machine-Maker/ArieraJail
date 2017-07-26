package me.x1machinemaker1x.arierajail.events;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import me.x1machinemaker1x.arierajail.objects.Handcuff;
import me.x1machinemaker1x.arierajail.utils.Handcuffs;

public class PlayerDeath implements Listener {
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent e) {
		if (!Handcuffs.getInstance().isCuffed(e.getEntity().getUniqueId())) return;
		Handcuff h = Handcuffs.getInstance().getHandcuff(e.getEntity().getUniqueId());
		e.setDeathMessage(ChatColor.RED + e.getEntity().getName() + " was killed because they logged off while in handcuffs!");
		Handcuffs.getInstance().unCuff(h);
	}

}
