package me.x1machinemaker1x.arierajail.events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import me.x1machinemaker1x.arierajail.ArieraJail;
import me.x1machinemaker1x.arierajail.objects.HandcuffTimer;
import me.x1machinemaker1x.arierajail.utils.Configs;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;
import me.x1machinemaker1x.arierajail.utils.Handcuffs;
import me.x1machinemaker1x.arierajail.utils.Messages;

public class EntityInteract implements Listener {
	
	private final ArieraJail instance;
	
	public EntityInteract(ArieraJail instance) {
		this.instance = instance;
	}
	
	@EventHandler
	public void onEntityClick(PlayerInteractAtEntityEvent e) {
		if (!(e.getRightClicked() instanceof Player)) return;
		Player clicked = (Player) e.getRightClicked();
		if (e.getPlayer().getUniqueId().toString().equals(clicked.getUniqueId().toString())) return;
		if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLD_HOE)) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getDurability() !=  Material.GOLD_HOE.getMaxDurability()) return;
		if (!e.getPlayer().hasPermission("arierajail.handcuffs.use")) {
			e.getPlayer().sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
			return;
		}
		if (Handcuffs.getInstance().getHandcuff(e.getPlayer().getUniqueId()) == null && !instance.isMapped(clicked.getUniqueId())) {
			if (Handcuffs.getInstance().isCuffer(e.getPlayer().getUniqueId())) return;
			if (Handcuffs.getInstance().isCuffed(clicked.getUniqueId())) {
				e.getPlayer().sendMessage(Messages.PREFIX.toString() + Messages.ALREADY_HANDCUFFED.toString());
				return;
			}
			HandcuffTimer runnable = new HandcuffTimer(instance, clicked, e.getPlayer());
			instance.addMapping(clicked.getUniqueId(), runnable);
			Long seconds = Configs.getInstance().getConfig(ConfigType.CONFIG).getLong("must-stay-still-time-if-handcuffed-in-seconds");
			clicked.sendMessage(Messages.ATTEMPT_HANDCUFF_CRIMINAL.toString().replace("%playername%", e.getPlayer().getName()).replace("%seconds%", seconds+""));
			e.getPlayer().sendMessage(Messages.ATTEMPT_HANDCUFF_COP.toString().replace("%playername%", clicked.getName()).replace("%seconds%", seconds+""));
			runnable.runTaskLater(instance, seconds * 20L);
		}
	}
}
