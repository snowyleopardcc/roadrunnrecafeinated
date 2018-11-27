package com.cc.roadrunner;

import java.util.HashSet;
import java.util.Set;

import com.cc.roadrunner.config.RoadRunnerBlock;
import com.cc.roadrunner.config.StateManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockHandler {


	private final StateManager config = StateManager.getInstance();
	
	
	@SubscribeEvent
	public void playerOnRoad(LivingEvent.LivingUpdateEvent event) {
		
		EntityLivingBase entity = (EntityLivingBase) event.getEntityLiving();

		if( !entity.getEntityWorld().isRemote && config.isEntityWhitelisted(entity)) {	

			BlockPos pos = entity.getPosition().down();
			
			String blockIdUnder = entity.getEntityWorld().getBlockState(pos).getBlock().toString();

			if(config.hasBlock(blockIdUnder)) {
				
				RoadRunnerBlock block = config.getBlock(blockIdUnder);
				
				checkModifiers(entity, block);
			}
			else if(!config.hasBlock(blockIdUnder) && hasModifier(entity)){
				
				RoadRunnerRecaffeinatedMod.logger.info("Removing modifier for entity: " + entity.getName() + " with uuid " + entity.getUniqueID() );
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(getModifier(entity));				
			}		
		}	
	
	}

	/**
	 * Checks to see if the entity has the correct modifiers,  and adjusts them if not.
	 * @param entity
	 * @param block ->  Road runner block containing correct attribute modifiers.
	 */
	private void checkModifiers(EntityLivingBase entity, RoadRunnerBlock block) {
		
//		entity has no modifiers,  either specific to the block or otherwise...
		if(!hasModifier(entity, block) && !hasModifier(entity)  ) {
//		!getModifier(entity).getID().equals(block.getModifier(SharedMonsterAttributes.MOVEMENT_SPEED).getID())
					
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(block.getModifier(SharedMonsterAttributes.MOVEMENT_SPEED));
			RoadRunnerRecaffeinatedMod.logger.info("Adding modifier for entity: " + entity.getName() + " with uuid " + entity.getUniqueID() + " at ammount " + getModifier(entity).getAmount());
		}
//		entity has a modifier,  but not specific to the block
		else if( hasModifier(entity) && !hasModifier(entity, block) ) {

			RoadRunnerRecaffeinatedMod.logger.info("Toggling modifier for entity: " + entity.getName() + " with uuid " + entity.getUniqueID() + " at ammount " + getModifier(entity).getAmount());
//			remove old modifier first,  then add the correct / appropriate one into it...
			AttributeModifier current = getModifier(entity);
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(current);
			
			entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).applyModifier(block.getModifier(SharedMonsterAttributes.MOVEMENT_SPEED));	
		}
		else {
			
			return;
		}
		
	}
	
	/**
	 * Find and return the road block modifier from the entity.  There
	 * can only be one modifier based on the required "Movement Speed"
	 * at a time,  so we use this as the key for searching.
	 * @param entity
	 * @return
	 */
	private AttributeModifier getModifier(EntityLivingBase entity) {
		
		AttributeModifier modifier = null;		
		
		Set<AttributeModifier> modifiers = new HashSet<AttributeModifier>(
				
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifiers()
		);
		
		for(AttributeModifier attr : modifiers ) {
			
			if(config.hasModifier(attr.getID())) {
				
				modifier = attr;
				break;
			}
		}
		
		return modifier;
	}
	
	/**
	 * Checks to see if entity has a specific modifier from the current block based upon UUID.
	 * @param entity
	 * @param currentBlock
	 * @return
	 */
	private boolean hasModifier(EntityLivingBase entity, RoadRunnerBlock currentBlock ) {
		
		boolean found = false;
		
		Set<AttributeModifier> modifiers = new HashSet<AttributeModifier>(
				
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifiers()
		);
		
		for(AttributeModifier attr : modifiers ) {
			
			if(attr.getID().equals(currentBlock.getUUID())) {
				
				found = true;
				break;
			}
		}
		
		return found;
	}
	
	/**
	 * Check to see if an entity has any modifier at all.
	 * @param entity
	 * @return
	 */
	private boolean hasModifier(EntityLivingBase entity) {
		
		boolean found = false;
		
		
		Set<AttributeModifier> modifiers = new HashSet<AttributeModifier>(
				
				entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getModifiers()
		);
		
		for( AttributeModifier attr : modifiers ) {
			
			if(this.config.hasModifier(attr.getID())) {
				
				found = true;
				break;
			}
		}
		
		return found;
	}	
	
}
