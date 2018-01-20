package me.x1machinemaker1x.arierajail.objects;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class Log implements ConfigurationSerializable{

    String playerArrested = null;
    UUID playerUUID = null;
    Integer infractionNumber = null;
    String arrestedBy = null;
    Integer lengthOfImprisonment = null; //in seconds
    Boolean releasedEarly = null;
    Boolean hasBeenReleased = null;

    public Log(String playerArrested, UUID playerUUID, Integer infractionNumber, String arrestedBy, Integer lengthOfImprisonment, Boolean releasedEarly, Boolean hasBeenReleased) {
        this.playerArrested = playerArrested;
        this.playerUUID = playerUUID;
        this.infractionNumber = infractionNumber;
        this.arrestedBy = arrestedBy;
        this.lengthOfImprisonment = lengthOfImprisonment;
        this.releasedEarly = releasedEarly;
        this.hasBeenReleased = hasBeenReleased;
    }

    public Log(String playerArrested, UUID playerUUID, Integer infractionNumber, String arrestedBy) {
        this(playerArrested, playerUUID, infractionNumber, arrestedBy, null, null, false);
    }

    public void setReleased(Integer lengthOfImprisonment, Boolean releasedEarly) {
        if ( lengthOfImprisonment == null || releasedEarly == null) {
            throw new IllegalStateException("Already released!");
        }
        this.lengthOfImprisonment = lengthOfImprisonment;
        this.releasedEarly = releasedEarly;
        this.hasBeenReleased = true;
    }

    public boolean isReleased() {
        return this.hasBeenReleased;
    }
    
    public String getPlayer() {
    	return this.playerArrested;
    }
    
    public UUID getUUID() {
    	return this.playerUUID;
    }
    
    public Integer getOffenseNumber() {
    	return this.infractionNumber;
    }
    
    public String getArrestingPlayer() {
    	return this.arrestedBy;
    }
    
    public Integer getLengthOfImprisonment() {
    	return this.lengthOfImprisonment;
    }
    
    public Boolean wasReleasedEarly() {
    	return this.releasedEarly;
    }
    
    public Boolean hasBeenReleased() {
    	return this.hasBeenReleased;
    }

    @Override
    public Map<String, Object> serialize() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("playerArrested", playerArrested);
        map.put("playerUUID", playerUUID.toString());
        map.put("infractionNumber", infractionNumber);
        map.put("arrestedBy", arrestedBy);
        map.put("lengthOfImprisonment", lengthOfImprisonment);
        map.put("releasedEarly", releasedEarly);
        map.put("hasBeenReleased", hasBeenReleased);
        return map;
    }

    public static Log deserialize(Map<String, Object> map) {
        String playerArrested = String.valueOf(map.get("playerArrested"));
        UUID playerUUID = UUID.fromString(String.valueOf(map.get("playerUUID")));
        Integer infractionNumber = Integer.valueOf(String.valueOf(map.get("infractionNumber")));
        String arrestedBy = String.valueOf(map.get("arrestedBy"));
        Integer lengthOfImprisonment = Integer.valueOf(String.valueOf(map.get("lengthOfImprisonment")));
        Boolean releasedEarly = Boolean.valueOf(String.valueOf(map.get("releasedEarly")));
        Boolean hasBeenReleased = Boolean.valueOf(String.valueOf(map.get("hasBeenReleased")));
        return new Log(playerArrested, playerUUID, infractionNumber, arrestedBy, lengthOfImprisonment, releasedEarly, hasBeenReleased);
    }
}
