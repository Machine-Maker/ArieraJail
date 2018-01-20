package me.x1machinemaker1x.arierajail.events;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.x1machinemaker1x.arierajail.ArieraJail;
import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.objects.Handcuff;
import me.x1machinemaker1x.arierajail.objects.Jail;
import me.x1machinemaker1x.arierajail.utils.Configs;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;
import me.x1machinemaker1x.arierajail.utils.Handcuffs;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;
import me.x1machinemaker1x.arierajail.utils.Signs;
import net.minecraft.server.v1_12_R1.ChatMessageType;
import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;

public class PlayerMove implements Listener {
	
	private ArieraJail plugin;
	
	public PlayerMove(ArieraJail plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (plugin.isMapped(e.getPlayer().getUniqueId()) && !e.getTo().equals(e.getFrom())) {
			plugin.getTask(e.getPlayer().getUniqueId()).getClicker().sendMessage(Messages.YOU_MOVED_COP.toString().replace("%playername%", e.getPlayer().getName()));
			plugin.cancelTaskDelMap(e.getPlayer().getUniqueId());
			e.getPlayer().sendMessage(Messages.YOU_MOVED_CRIMINAL.toString());
		}
		Handcuff h = Handcuffs.getInstance().getHandcuff(e.getPlayer().getUniqueId());
		if (h != null) {
			if (Handcuffs.getInstance().isCuffed(e.getPlayer().getUniqueId())) { //If player is a criminal
				Player cuffer = h.getCuffer();
				Player cuffee = h.getCuffee();
				int maxDistanceSquared = Configs.getInstance().getConfig(ConfigType.CONFIG).getInt("max-distance-squared");
				try {
					if ((cuffer.getLocation().distanceSquared(e.getTo()) <= maxDistanceSquared) || h.didViolate()) return;
				} catch (IllegalArgumentException er) {
					cuffee.teleport(cuffer.getLocation());
				}
				Location loc = cuffer.getLocation().clone();
				h.setDidViolate(true);
				new BukkitRunnable() {
					@Override
					public void run() {
						cuffee.teleport(loc);
						cuffee.sendMessage(Messages.TOO_FAR.toString().replace("%name%", cuffer.getName()));
						h.setDidViolate(false);
					}
				}.runTaskLater(plugin, 10);
			}
			else { //Player is a cop
				Bukkit.getPluginManager().callEvent(new PlayerMoveEvent(h.getCuffee(), h.getCuffee().getLocation(), h.getCuffee().getLocation()));
				if (!e.getPlayer().hasPermission("arierajail.putinjail")) return;
				Block b = e.getPlayer().getTargetBlock((Set<Material>) null, 4);
				if (b.getType().equals(Material.SIGN_POST) || b.getType().equals(Material.WALL_SIGN)) {
					if (Signs.getInstance().isSign(b.getLocation())) {
						sendActionBar(e.getPlayer(), Messages.CLICK_TO_IMPRISON.toString().replace("%playername%", h.getCuffee().getName()));
					}
				}
			}
		}
		else if (Jails.getInstance().getCell(e.getPlayer().getUniqueId()) != null) {
			Cell cell = Jails.getInstance().getCell(e.getPlayer().getUniqueId());
			if (!cell.getSel().contains(e.getTo())) {
				e.getPlayer().teleport(cell.getSpawn());
			}
		}
		else {
			if (!e.getPlayer().hasPermission("arierajail.releasefromjail")) return;
			Block b = e.getPlayer().getTargetBlock((Set<Material>) null, 4);
			if (b.getType().equals(Material.SIGN_POST) || b.getType().equals(Material.WALL_SIGN)) {
				if (Signs.getInstance().isSign(b.getLocation())) {
					Jail jail = Jails.getInstance().getJail(Signs.getInstance().getSign(b.getLocation()).getJailString());
					Cell cell = Jails.getInstance().getCell(Signs.getInstance().getSign(b.getLocation()).getCellString(), jail);
					if (cell == null) return;
					if (cell.getUUID() == null) return;
					sendActionBar(e.getPlayer(), Messages.CLICK_TO_RELEASE.toString().replace("%playername%", Bukkit.getOfflinePlayer(cell.getUUID()).getName()));
				}
			}
		}
	}
	
	private void sendActionBar(Player p, String message) {
		IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");
		PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, ChatMessageType.a((byte) 2));
		((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
	}
}
