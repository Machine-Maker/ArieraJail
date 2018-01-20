package me.x1machinemaker1x.arierajail.cmds;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.x1machinemaker1x.arierajail.objects.Log;
import me.x1machinemaker1x.arierajail.utils.LogFile;
import me.x1machinemaker1x.arierajail.utils.Messages;
import net.minecraft.server.v1_12_R1.PacketDataSerializer;
import net.minecraft.server.v1_12_R1.PacketPlayOutCustomPayload;

public class LogCmd extends SubCommand {

	@SuppressWarnings("deprecation")
	public void onCommand(Player p, String[] args) {
		ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bMeta = (BookMeta) book.getItemMeta();
    	if (args[0].equalsIgnoreCase("all")) {
    		if (!p.hasPermission("arierajail.log.all")) {
    			p.sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
    			return;
    		}
    		for (Log log : LogFile.getInstance().getAllLogs()) {
    			bMeta.addPage(formatLogEntry(log));
    		}
    		book.setItemMeta(bMeta);
    		openBook(p, book);
    	}
    	else if (!LogFile.getInstance().getLogsForPlayer(Bukkit.getOfflinePlayer(args[0]).getUniqueId()).isEmpty()) {
    		if (args[0].equals(p.getDisplayName())) {
    			if (!p.hasPermission("arierajail.log.self") && !p.hasPermission("arierajail.log.all")) {
    				p.sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
    				return;
    			}
    			List<Log> logListForPlayer = LogFile.getInstance().getLogsForPlayer(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
    			for (Log log : logListForPlayer) {
    				bMeta.addPage(formatLogEntry(log));
    			}
    			book.setItemMeta(bMeta);
    			openBook(p, book);
    		}
    		else {
    			if (!p.hasPermission("arierajail.log.others") && !p.hasPermission("arierajail.log.all")) {
    				p.sendMessage(Messages.PREFIX.toString() + Messages.NO_PERMISSION.toString());
    				return;
    			}
    			List<Log> logListForPlayer = LogFile.getInstance().getLogsForPlayer(Bukkit.getOfflinePlayer(args[0]).getUniqueId());
    			for (Log log : logListForPlayer) {
    				bMeta.addPage(formatLogEntry(log));
    			}
    			book.setItemMeta(bMeta);
    			openBook(p, book);
    		}
    	}
    	else {
    		p.sendMessage(Messages.PREFIX.toString() + Messages.NO_LOG.toString());
    	}
    }

    public String name() {
        return "log";
    }

    public String info() {
        return "Gives a book with a log of players and their jail times";
    }

    public String[] aliases() {
        return new String[] { "l" };
    }

    public String permission() {
        return "arierajail.log";
    }

    public int argsReq() {
        return 1;
    }

    public String format() {
        return "/aj log <playername|all>";
    }
    
    private String formatLogEntry(Log log) {
    	String logInfo = ChatColor.BLUE.toString() + ChatColor.UNDERLINE;
    	if (("Player: " + log.getPlayer()).length() > 21) {
    		logInfo += "Player:\n" + ChatColor.RED + log.getPlayer() + "\n";
    	}
    	else {
    		logInfo += "Player:" + ChatColor.RESET + ChatColor.RED + " " + log.getPlayer() + "\n\n"; 
    	}
    	logInfo += ChatColor.BLUE + ChatColor.UNDERLINE.toString() + "Offense Number:" + ChatColor.RESET + ChatColor.DARK_AQUA + " " + log.getOffenseNumber() + "\n\n";
    	String arrestedBy = ChatColor.BLUE.toString() + ChatColor.UNDERLINE;
    	if (("Arrested By: " + log.getArrestingPlayer()).length() > 21) {
    		arrestedBy += "Arrested By:\n" + ChatColor.DARK_GREEN + log.getArrestingPlayer() + "\n";
    	}
    	else {
    		arrestedBy += "Arrested By:" + ChatColor.RESET + ChatColor.DARK_GREEN + " " + log.getArrestingPlayer() + "\n\n";
    	}
    	logInfo += arrestedBy;
    	String released = ChatColor.BLUE.toString() + ChatColor.UNDERLINE + "Released:" + ChatColor.RESET;
    	if (log.hasBeenReleased() == null) {
    		released += ChatColor.RED + " No\n";
    	}
    	else {
    		if (log.hasBeenReleased()) {
    			released += ChatColor.DARK_GREEN + " Yes\n\n";
    		}
    		else {
    			released += ChatColor.RED + " No\n\n";
    		}    		
    	}
    	logInfo += released;
    	String timeInJail = ChatColor.BLUE.toString() + ChatColor.UNDERLINE + "Time in Jail:" + ChatColor.RESET;
    	if (log.getLengthOfImprisonment() == null) {
    		timeInJail += ChatColor.RED + " N/A\n\n";
    	}
    	else {
    		timeInJail += ChatColor.GOLD + " " + Messages.convertTime(log.getLengthOfImprisonment()) + "\n\n";
    	}
    	logInfo += timeInJail;
    	String releasedEarly = ChatColor.BLUE.toString() + ChatColor.UNDERLINE + "Released Early:" + ChatColor.RESET;
    	if (log.wasReleasedEarly() == null) {
    		releasedEarly += ChatColor.RED + " N/A\n\n";
    	}
    	else {
    		if (log.wasReleasedEarly()) {
    			releasedEarly += ChatColor.DARK_GREEN + " Yes\n\n";
    		}
    		else {
    			releasedEarly += ChatColor.RED + " No\n\n";
    		}
    	}
    	logInfo += releasedEarly;
    	return logInfo;
    }
    
    private void openBook(Player p, ItemStack book) {
    	int slot = p.getInventory().getHeldItemSlot();
    	ItemStack oldItem = p.getInventory().getItem(slot);
		p.getInventory().setItem(slot, book);
		
		ByteBuf buf = Unpooled.buffer(256);
		buf.setByte(0,  (byte) 0);
		buf.writerIndex(1);
		
		PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|BOpen", new PacketDataSerializer(buf));
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		p.getInventory().setItem(slot, oldItem);
    }
}
