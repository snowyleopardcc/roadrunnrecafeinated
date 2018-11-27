package com.cc.roadrunner.config;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;

public class RoadRunnerBlock {


	public RoadRunnerBlock(String blockId, float movementSpeedModifier) {
				
		this.blockId = blockId;
				
				
		/**
		 * NOTE: Only movement speed is required,  so we are going to use the UUID
		 * from it as a primary key for looking up this block with.
		 */
		AttributeModifier modifier = createModifier(blockId, SharedMonsterAttributes.MOVEMENT_SPEED, movementSpeedModifier);
		
		this.uuid = modifier.getID();
		
		this.modifiers = new HashMap<IAttribute, AttributeModifier>() {

			private static final long serialVersionUID = 1L;

		{						
			put(SharedMonsterAttributes.MOVEMENT_SPEED, modifier);
		}};
		
		
	}
	
	public RoadRunnerBlock(String blockId, float movementSpeedModifier, float attackSpeedModifier, float knockbackResistanceModifier ) {
		this(blockId, movementSpeedModifier);
		
		this.modifiers.replace(SharedMonsterAttributes.ATTACK_SPEED, new AttributeModifier("Block " + blockId + "movement modifier", movementSpeedModifier, 2));
		this.modifiers.replace(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, new AttributeModifier("Block " + blockId + "movement modifier", movementSpeedModifier, 2));
	}
	
	/***
	 * NOTES: maximum for modifiers is as below...
	 * movement and attack: 64
	 * knock back: 1.0 
	 */
	
	public RoadRunnerBlock(String blockId, Map<String, Float> modifiers) {

		this.blockId = blockId;
		this.modifiers = new HashMap<IAttribute, AttributeModifier>();
		
		for(Map.Entry<String, Float> entry : modifiers.entrySet()) {
			
			switch(entry.getKey()) {
			
			case "movement":
				
				AttributeModifier modifier = createModifier(blockId, SharedMonsterAttributes.MOVEMENT_SPEED, entry.getValue());
				
				this.modifiers.put(SharedMonsterAttributes.MOVEMENT_SPEED, modifier);
				
				this.uuid = modifier.getID();
			break;
			
			case "attack":
			
				this.modifiers.put(SharedMonsterAttributes.ATTACK_SPEED, createModifier(blockId, SharedMonsterAttributes.MOVEMENT_SPEED, entry.getValue()));
			break;
			
			case "knockback":
			
				this.modifiers.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, createModifier(blockId, SharedMonsterAttributes.MOVEMENT_SPEED, entry.getValue()));
			break;
			}
		}
	}

	/**
	 * Instance members
	 * 
	 * NOTE: the uuid is used as a primary key of sorts,  to help 
	 * identify or lookup this block with.  It is directly tied to
	 * the movement modifier which is the only required parameter.
	 * 
	 * I may end up creating a better solution later,  but for now
	 * this works just fine to get by with,  yes,  it is a bit 
	 * cheesie.  But meh....
	 * 
	 * NOTE:  The Attribute Modifier Class creates and maintains it's
	 * own uuid's internally (thread safe?)
	 */
	private UUID uuid;
	private final String blockId;
	
	private Map<IAttribute, AttributeModifier> modifiers;
	
	
	/*****  Getters & Setters  ******/
	
	
	public String getBlockId() { return this.blockId; }	

	public UUID getUUID() { return this.uuid; }
	
	/***
	 * Sets value for one of three supported modifiers;
	 * Movement Speed, Attack Speed,  and Knock Back Resistance
	 * @param modifier -> One of the three supported Attribute Modifier Types
	 * @param value
	 */
	public void setModifier(IAttribute modifier, float value) {
		
		if(modifier.equals(SharedMonsterAttributes.MOVEMENT_SPEED) 
				|| modifier.equals(SharedMonsterAttributes.ATTACK_SPEED) 
				|| modifier.equals(SharedMonsterAttributes.KNOCKBACK_RESISTANCE) ) {
			
			
			this.modifiers.replace(modifier, this.createModifier(getBlockId(), modifier, value));
		}
		else {
			
			throw new UnsupportedOperationException("Modifier of type: " + modifier.toString() + " is currently unsupported!");
		}
	}


	public AttributeModifier getModifier(IAttribute modifier) {
	
		//  returns null if modifier is not found in hash map...
		return this.modifiers.get(modifier);
	}

	

	/******  Helper Methods  ******/
	
	/**
	 * Utility method to clamp modifier values to within min / max bounds.
	 * @param value is what we are clamping to within range of.
	 * @param boundary is the value to clamp to.
	 */
	@SuppressWarnings("unused")
	private float clamp(float value, float boundary) {
		
		if(value > 0.0f) { return Math.min(value, boundary); }
		else { return 0.0f; }
	}
	
	/**
	 * Used for creating standardized attribute modifiers.
	 * @param blockName
	 * @param value
	 * @return
	 */
	private AttributeModifier createModifier(String blockName, IAttribute type, float value ) {
		
		String name = String.format("Attribute modifier for %s 's %s", blockName, type.getName());
		return new AttributeModifier(name, value, 2);
	}
	

	/**
	 * Override -- Comparing Road Blocks based upon UUID's or Movement Speed Modifiers
	 * @param block
	 * @return
	 */
//	public boolean equals(RoadRunnerBlock block ) {
//		
//		return (this.uuid == block.getUUID())
//				|| (this.getModifier(SharedMonsterAttributes.MOVEMENT_SPEED).equals(block.getModifier(SharedMonsterAttributes.MOVEMENT_SPEED))) ;
//	}
}
