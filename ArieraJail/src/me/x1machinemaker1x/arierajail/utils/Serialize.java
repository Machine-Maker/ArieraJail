package me.x1machinemaker1x.arierajail.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class Serialize {  
    /**
     * 
     * A method to serialize an {@link ItemStack} array to Base64 String.
     * 
     * <p />
     * 
     * @param items to turn into a Base64 String.
     * @return Base64 string of the items.
     */
    public static String itemStackArrayToBase64(ItemStack[] items) {
    	if (items == null) return null;
    	try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            
            // Write the size of the inventory
            dataOutput.writeInt(items.length);
            
            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }
            
            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (IOException e) { 
        	return null;
        }
    }
    
    /**
     * A method to serialize an {@link Location} to a Base64 String.
     * 
     * <p />
     * 
     * @param loc Location to be serialized
     * @return Base64 String
     */
    public static String locationToBase64(Location loc) {
    	if (loc == null) return null;
    	try {
    		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    		BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
    		//write data
    		dataOutput.writeObject(loc.getWorld().getUID().toString());
    		dataOutput.writeInt(loc.getBlockX());
    		dataOutput.writeInt(loc.getBlockY());
    		dataOutput.writeInt(loc.getBlockZ());
    		dataOutput.writeFloat(loc.getPitch());
    		dataOutput.writeFloat(loc.getYaw());
    		dataOutput.close();
    		return Base64Coder.encodeLines(outputStream.toByteArray());
    	} catch (IOException e) {
    		return null;
    	}
    }
    
    /**
     * Gets a location from a Base64 string
     * 
     * <p />
     * 
     * @param data base64 string
     * @return Location 
     * @throws IOException
     */
    public static Location base64ToLocation(String data) {
    	if (data == null || data.equals("null")) return null;
    	try {
    		ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
    		BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
    		World world = Bukkit.getWorld(UUID.fromString(String.valueOf(dataInput.readObject())));
    		int x = dataInput.readInt();
    		int y = dataInput.readInt();
    		int z = dataInput.readInt();
    		Float pitch = dataInput.readFloat();
    		Float yaw = dataInput.readFloat();
    		dataInput.close();
    		return new Location(world, x, y, z, yaw, pitch);
    	} catch (IOException | ClassNotFoundException e) {
    		return null;
    	}
    }
    
    /**
     * Gets an array of ItemStacks from Base64 string.
     * 
     * <p />
     * 
     * @param data Base64 string to convert to ItemStack array.
     * @return ItemStack array created from the Base64 string.
     * @throws IOException
     */
    public static ItemStack[] itemStackArrayFromBase64(String data) {
    	try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
    
            // Read the serialized inventory
            for (int i = 0; i < items.length; i++) {
            	items[i] = (ItemStack) dataInput.readObject();
            }
            
            dataInput.close();
            return items;
        } catch (ClassNotFoundException | IOException e) {
        	return null;
        }
    }
}
