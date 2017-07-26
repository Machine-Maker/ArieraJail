package me.x1machinemaker1x.arierajail.objects;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Handcuff {
	
	private UUID cuffer;
	private UUID cuffee;
	boolean didViolate;
	
	public Handcuff(UUID cuffer, UUID cuffee) {
		this.cuffer = cuffer;
		this.cuffee = cuffee;
		this.didViolate = false;
	}
	
	public Player getCuffer() {
		return Bukkit.getPlayer(cuffer);
	}
	
	public Player getCuffee() {
		return Bukkit.getPlayer(cuffee);
	}
	
	public boolean didViolate() {
		return didViolate;
	}
	
	public void setDidViolate(boolean didViolate) {
		this.didViolate = didViolate;
	}

}
