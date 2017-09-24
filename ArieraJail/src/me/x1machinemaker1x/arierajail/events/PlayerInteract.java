package me.x1machinemaker1x.arierajail.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.x1machinemaker1x.arierajail.objects.Cell;
import me.x1machinemaker1x.arierajail.objects.Handcuff;
import me.x1machinemaker1x.arierajail.objects.Jail;
import me.x1machinemaker1x.arierajail.utils.Configs;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;
import me.x1machinemaker1x.arierajail.utils.Handcuffs;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;
import me.x1machinemaker1x.arierajail.utils.Signs;

public class PlayerInteract implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if (!e.getClickedBlock().getType().equals(Material.SIGN_POST) && !e.getClickedBlock().getType().equals(Material.WALL_SIGN)) return;
		if (!Signs.getInstance().isSign(e.getClickedBlock().getLocation())) return;
		if (!(e.getClickedBlock().getState() instanceof Sign)) {
			Signs.getInstance().delSign(e.getClickedBlock().getLocation());
			return;
		}
		Sign sign = (Sign) e.getClickedBlock().getState();
		Jail jail = Jails.getInstance().getJail(sign.getLine(1).split(": §0")[1]);
		if (jail == null) return;
		Cell cell = Jails.getInstance().getCell(sign.getLine(2).split(": §0")[1], jail);
		if (cell == null) return;
		if (Handcuffs.getInstance().isCuffer(e.getPlayer().getUniqueId())) { //player has a cuffed player
			if (!e.getPlayer().hasPermission("arierajail.putinjail")) {
				e.getPlayer().sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
				return;
			}
			Handcuff h = Handcuffs.getInstance().getHandcuff(e.getPlayer().getUniqueId());
			if (cell.getUUID() == null) { //cell is empty
				if (cell.getSpawn() == null) {
					e.getPlayer().sendMessage(Messages.PREFIX.toString() + Messages.NO_SPAWN.toString());
					return;
				}
				if (jail.getRelease() == null) {
					e.getPlayer().sendMessage(Messages.PREFIX.toString() + Messages.NO_RELEASE_SET.toString());
					return;
				}
				h.getCuffee().teleport(cell.getSpawn());
				h.getCuffee().sendMessage(Messages.CRINIMAL_LOCKED_UP.toString());
				cell.setUUID(h.getCuffee().getUniqueId());
				cell.lockUp();
				h.getCuffer().sendMessage(Messages.COP_LOCKED_UP.toString().replace("%playername%", h.getCuffee().getName()));
				sign.setLine(3, h.getCuffee().getName());
				sign.update();
				Handcuffs.getInstance().unCuff(h);
				cell.startSentence(Configs.getInstance().getConfig(ConfigType.CONFIG).getInt("sentence-length-in-minutes") * 60);
				Jails.getInstance().saveJails();
			}
			else { //cell is occupied
				e.getPlayer().sendMessage(Messages.PREFIX.toString() + Messages.CELL_FULL.toString());
				return;
			}
		}
		else { //player does not have a cuffed player
			if (!e.getPlayer().hasPermission("arierajail.releasefromjail")) {
				e.getPlayer().sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
				return;
			}
			if (cell.getUUID() != null) {
				cell.release();
				e.getPlayer().sendMessage(Messages.COP_RELEASED.toString().replace("%playername%", Bukkit.getPlayer(cell.getUUID()).getName()));
				cell.clearCell();
				Jails.getInstance().saveJails();
			}
			else { //cell is empty
				e.getPlayer().sendMessage(Messages.PREFIX.toString() + Messages.CELL_EMPTY.toString());
				return;
			}
		}
	}
}
