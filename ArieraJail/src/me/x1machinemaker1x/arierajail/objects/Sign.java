package me.x1machinemaker1x.arierajail.objects;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import me.x1machinemaker1x.arierajail.utils.Serialize;

public class Sign implements ConfigurationSerializable {
	
	Location loc = null;
	String cellJail = null;
	
	/**
	 * Creates a sign instance
	 * @param loc Location of the sign
	 * @param cellJail String in format (cellname:jailname)
	 */
	public Sign(Location loc, String cellJail) {
		this.loc = loc;
		this.cellJail = cellJail;
	}
	
	/**
	 * Gets location of the sign
	 * @return Location
	 */
	public Location getLoc() {
		return loc;
	}
	
	/**
	 * Gets the cel lname
	 * @return Cell Name
	 */
	public String getCellString() {
		return cellJail.split(":")[0];
	}
	
	/**
	 * Gets the jail name
	 * @return Jail Name
	 */
	public String getJailString() {
		return cellJail.split(":")[1];
	}
	
	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> result = new LinkedHashMap<String, Object>();
		result.put("location", Serialize.locationToBase64(this.getLoc()));
		result.put("cell", this.cellJail.split(":")[0]);
		result.put("jail", this.cellJail.split(":")[1]);
		return result;
	}
	
	/**
	 * Deserializes a sign
	 * @param map Map<String, Object> 
	 * @return Sign instance
	 */
	public static Sign deserialize(Map<String, Object> map) {
		Location loc = Serialize.base64ToLocation(String.valueOf(map.get("location")));
		String cell = String.valueOf(map.get("cell"));
		String jail = String.valueOf(map.get("jail"));
		return new Sign(loc, cell + ":" + jail);
	}
}
