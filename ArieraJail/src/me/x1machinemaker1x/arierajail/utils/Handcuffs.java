package me.x1machinemaker1x.arierajail.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.x1machinemaker1x.arierajail.objects.Handcuff;

public class Handcuffs {
	
	private Handcuffs() { }
	
	private static Handcuffs instance = new Handcuffs();
	
	public static Handcuffs getInstance() {
		return instance;
	}
	
	private List<Handcuff> handcuffedPlayers;
	public void setup() {
		handcuffedPlayers = new ArrayList<Handcuff>();
	}
	
	/**
	 * Adds a cuffed player
	 * @param cuffer The cop who cuffed the criminal
	 * @param cuffee The criminal who was cuffed
	 */
	public void cuffPlayer(UUID cuffer, UUID cuffee) {
		handcuffedPlayers.add(new Handcuff(cuffer, cuffee));
	}

	/**
	 * Uncuffs the player cuffed by a cop
	 * @param cuffer Cop who cuffed the player
	 */
	public void unCuff(Handcuff cuff) {
		cuff.getCuffee().setWalkSpeed(0.2f);
		handcuffedPlayers.remove(cuff);
	}
	
	/**
	 * Gets handcuff object
	 * @param cufferOrCuffee either player UUID
	 * @return Handcuff object for the player
	 */
	public Handcuff getHandcuff(UUID cufferOrCuffee) {
		if (handcuffedPlayers.isEmpty()) return null;
		for (Handcuff h : handcuffedPlayers) {
			if (h.getCuffer().getUniqueId().equals(cufferOrCuffee) || h.getCuffee().getUniqueId().equals(cufferOrCuffee)) return h;
		}
		return null;
	}
	
	/**
	 * Checks if criminal is cuffed
	 * @param cuffee UUID of player
	 * @return boolean if cuffed
	 */
	public boolean isCuffed(UUID cuffee) {
		if (handcuffedPlayers.isEmpty()) return false;
		for (Handcuff h : handcuffedPlayers) {
			if (h.getCuffee().getUniqueId().equals(cuffee)) return true;
		}
		return false;
	}
	
	/**
	 * Checks if cop has cuffed
	 * @param cuffer UUID of player
	 * @return boolean if cuffer
	 */
	public boolean isCuffer(UUID cuffer) {
		if (handcuffedPlayers.isEmpty()) return false;
		for (Handcuff h : handcuffedPlayers) {
			if (h.getCuffer().getUniqueId().equals(cuffer)) return true;
		}
		return false;
	}
}
