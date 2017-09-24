package me.x1machinemaker1x.arierajail.events;

import java.util.Date;

import org.bukkit.BanList;
import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.x1machinemaker1x.arierajail.utils.Configs;
import me.x1machinemaker1x.arierajail.utils.Handcuffs;
import me.x1machinemaker1x.arierajail.utils.Configs.ConfigType;

public class PlayerQuit implements Listener {
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if (!Handcuffs.getInstance().isCuffed(e.getPlayer().getUniqueId())) return;
		if (e.getPlayer().hasPermission("arierajail.playerquit.bypass")) return;
		ConfigurationSection conf = Configs.getInstance().getConfig(ConfigType.CONFIG).getConfigurationSection("if-player-quits");
		if (conf.getBoolean("kill-player")) {
			e.getPlayer().setHealth(0);
		}
		if (conf.getBoolean("temp-ban.enable")) {
			BanList banList = Bukkit.getBanList(Type.NAME);
			String reason = ChatColor.translateAlternateColorCodes('&',conf.getString("temp-ban.ban-message"));
			int minutes = conf.getInt("temp-ban.time-in-minutes");
			Date date = new Date(System.currentTimeMillis() + (minutes * 60 * 1000));
			banList.addBan(e.getPlayer().getName(), reason, date, null);
		}
		Handcuffs.getInstance().unCuff(Handcuffs.getInstance().getHandcuff(e.getPlayer().getUniqueId()));
	}
}