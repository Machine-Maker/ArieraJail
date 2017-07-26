package me.x1machinemaker1x.arierajail.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.x1machinemaker1x.arierajail.utils.Handcuffs;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class OnCommand implements Listener {
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (!Handcuffs.getInstance().isCuffed(e.getPlayer().getUniqueId())) return;
		e.getPlayer().sendMessage(Messages.IN_HANDCUFFS.toString());
		e.setCancelled(true);	
	}
}
