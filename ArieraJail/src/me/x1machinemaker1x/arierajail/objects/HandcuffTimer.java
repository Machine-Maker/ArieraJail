package me.x1machinemaker1x.arierajail.objects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.x1machinemaker1x.arierajail.ArieraJail;
import me.x1machinemaker1x.arierajail.utils.Handcuffs;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class HandcuffTimer extends BukkitRunnable {
	
	private final Player clicked, clicker;
	private final ArieraJail plugin;
	
	public HandcuffTimer(ArieraJail plugin, Player clicked, Player clicker) {
		this.clicked = clicked;
		this.clicker = clicker;
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		plugin.cancelTaskDelMap(clicked.getUniqueId());
		Handcuffs.getInstance().cuffPlayer(this.clicker.getUniqueId(), clicked.getUniqueId());
		this.clicked.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 0), true);
		this.clicked.setWalkSpeed(0.1f);
		this.clicked.sendMessage(Messages.HANDCUFFED_CRIMINAL.toString());
		this.clicker.sendMessage(Messages.HANDCUFFED_COP.toString().replace("%playername%", this.clicked.getName()));
	}
	
	public Player getClicker() {
		return this.clicker;
	}

}
