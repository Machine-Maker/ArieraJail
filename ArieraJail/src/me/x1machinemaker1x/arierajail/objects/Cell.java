package me.x1machinemaker1x.arierajail.objects;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import me.x1machinemaker1x.arierajail.ArieraJail;
import me.x1machinemaker1x.arierajail.utils.Jails;
import me.x1machinemaker1x.arierajail.utils.Messages;
import me.x1machinemaker1x.arierajail.utils.Serialize;
import me.x1machinemaker1x.arierajail.utils.Signs;

public class Cell implements ConfigurationSerializable {
	
	String name = null;
	CuboidSelection sel = null;
	UUID uuid = null;
	//PlayerInventory inv = null;
	ItemStack[] contents = null;
	ItemStack[] armor = null;
	Location cellSpawn = null;
	String jailName = null;
	
	BukkitRunnable jailTimer = null;
	Integer counter = null;
	
	public Cell(String name, CuboidSelection sel, UUID uuid, ItemStack[] contents, ItemStack[] armor, Location cellSpawn, String jailName, Integer counter) {
		this.name = name;
		this.sel = sel;
		this.uuid = uuid;
		this.contents = contents;
		this.armor = armor;
		this.cellSpawn = cellSpawn;
		this.jailName = jailName;
		this.counter = counter;
		if (this.counter != null && this.uuid != null) {
			this.startSentence(counter);
		}
	}
	
	public Cell(String name, CuboidSelection sel, String jailName) {
		this(name, sel, null, null, null, null, jailName, null);
	}
	
	public String getName() {
		return name;
	}
	
	public CuboidSelection getSel() {
		return sel;
	}
	
	public UUID getUUID() {
		return uuid;
	}
	
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	
	public ItemStack[] getContents() {
		return contents;
	}
	
	public ItemStack[] getArmor() {
		return armor;
	}
	
	public void setInv(ItemStack[] contents, ItemStack[] armor) {
		this.contents = contents;
		this.armor = armor;
	}
	
	public Location getSpawn() {
		return cellSpawn;
	}
	
	public void setCellSpawn(Location loc) {
		this.cellSpawn = loc;
	}
	
	public String getJailName() {
		return jailName;
	}
	
	public Integer getCounter() {
		return this.counter;
	}
	
	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	
	public void startSentence(int seconds) {
		this.counter = seconds;
		this.jailTimer = new JailTimer(seconds, Bukkit.getPlayer(this.getUUID()), this);
		this.jailTimer.runTaskTimer(ArieraJail.getPlugin(), 0L, 20L);
	}
	
	public void clearCell() {
		this.uuid = null;
		this.contents = null;
		this.armor = null;
		this.counter = null;
		this.jailTimer.cancel();
		this.jailTimer = null;
	}
	
	public void lockUp() {
		Player p = Bukkit.getPlayer(this.getUUID());
		if (p.hasPotionEffect(PotionEffectType.SLOW))
			p.removePotionEffect(PotionEffectType.SLOW);
		this.setInv(p.getInventory().getContents().clone(), p.getInventory().getArmorContents().clone());
		p.getInventory().clear();
		p = null;
	}
	
	public void release() {
		Player p = Bukkit.getPlayer(this.getUUID());
		p.teleport(Jails.getInstance().getJail(this.getJailName()).getRelease());
		p.getInventory().setContents(this.getContents());
		p.getInventory().setArmorContents(this.getArmor());
		p.sendMessage(Messages.CRIMINAL_RELEASED.toString());
		((org.bukkit.block.Sign) Signs.getInstance().getSign(this).getLoc().getBlock().getState()).setLine(3, "§4(empty)");
		p = null;
	}

	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("name", this.getName());
		map.put("minPoint", Serialize.locationToBase64(this.getSel().getMinimumPoint()));
		map.put("maxPoint", Serialize.locationToBase64(this.getSel().getMaximumPoint()));
		map.put("uuid", (this.getUUID() == null) ? null : this.getUUID().toString());
		map.put("contents", Serialize.itemStackArrayToBase64(this.getContents()));
		map.put("armor", Serialize.itemStackArrayToBase64(this.getArmor()));
		map.put("cellSpawn", Serialize.locationToBase64(this.getSpawn()));
		map.put("jail-name", this.getJailName());
		map.put("counter", this.getCounter());
		return map;
	}
	
	public static Cell deserialize(Map<String, Object> map) {
		String name = String.valueOf(map.get("name"));
		Location minPoint = Serialize.base64ToLocation(String.valueOf(map.get("minPoint")));
		Location maxPoint = Serialize.base64ToLocation(String.valueOf(map.get("maxPoint")));
		CuboidSelection sel = new CuboidSelection(minPoint.getWorld(), minPoint, maxPoint);
		UUID uuid = (map.get("uuid") == null) ? null : UUID.fromString(String.valueOf(map.get("uuid")));
		ItemStack[] contents = Serialize.itemStackArrayFromBase64(String.valueOf(map.get("contents")));
		ItemStack[] armor = Serialize.itemStackArrayFromBase64(String.valueOf(map.get("armor")));
		Location cellSpawn = Serialize.base64ToLocation(String.valueOf(map.get("cellSpawn")));
		String jailName = String.valueOf(map.get("jail-name"));
		Integer counter = (map.get("counter") == null) ? null : Integer.valueOf(String.valueOf(map.get("counter")));
		return new Cell(name, sel, uuid, contents, armor, cellSpawn, jailName, counter);
	}
}
