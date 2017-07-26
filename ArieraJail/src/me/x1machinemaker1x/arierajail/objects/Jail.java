package me.x1machinemaker1x.arierajail.objects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.sk89q.worldedit.bukkit.selections.CuboidSelection;

import me.x1machinemaker1x.arierajail.utils.Serialize;

public class Jail implements ConfigurationSerializable {
	
	String name = null;
	CuboidSelection sel = null;
	List<Cell> cells = null;
	Location release = null;
	
	public Jail(String name, CuboidSelection sel, List<Cell> cells, Location release) {
		this.name = name;
		this.sel = sel;
		this.cells = cells;
		this.release = release;
	}
	
	public Jail(String name, CuboidSelection sel) {
		this(name, sel, new ArrayList<Cell>(), null);
	}
	
	public String getName() {
		return name;
	}
	
	public CuboidSelection getSel() {
		return sel;
	}
	
	public List<Cell> getCells() {
		return cells;
	}
	
	public void addCell(Cell cell) {
		cells.add(cell);
	}
	
	public void delCell(Cell cell) {
		cells.remove(cell);
	}
	
	public Location getRelease() {
		return release;
	}
	
	public void setRelease(Location release) {
		this.release = release;
	}

	@Override
	public Map<String, Object> serialize() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("name", this.getName());
		map.put("minPoint", Serialize.locationToBase64(this.getSel().getMinimumPoint()));
		map.put("maxPoint", Serialize.locationToBase64(this.getSel().getMaximumPoint()));
		List<Map<String, Object>> cellMaps = new ArrayList<Map<String, Object>>();
		for (Cell cell : this.getCells())
			cellMaps.add(cell.serialize());
		map.put("cells", cellMaps);
		map.put("release", Serialize.locationToBase64(this.getRelease()));
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static Jail deserialize(Map<String, Object> map) {
		String name = String.valueOf(map.get("name"));
		Location minPoint = Serialize.base64ToLocation(String.valueOf(map.get("minPoint")));
		Location maxPoint = Serialize.base64ToLocation(String.valueOf(map.get("maxPoint")));
		CuboidSelection sel = new CuboidSelection(minPoint.getWorld(), minPoint, maxPoint);
		List<Cell> cells = new ArrayList<Cell>();
		List<Map<?,?>> cellMaps = (List<Map<?, ?>>) map.get("cells");
		for (Map<?,?> cellMap : cellMaps) {
			cells.add(Cell.deserialize((Map<String, Object>) cellMap));
		}
		Location release = Serialize.base64ToLocation(String.valueOf(map.get("release")));
		return new Jail(name, sel, cells, release);
	}
}
