package com.cc.roadrunner.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Pattern;

import com.cc.roadrunner.RoadRunnerRecaffeinatedMod;
import com.cc.roadrunner.config.WhitelistableEntities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;

public class StateManager {

	private StateManager() {
	}

	private static StateManager instance;

	public static StateManager getInstance() {

		if (instance == null) {
			instance = new StateManager();
		}

		return instance;
	}

	/**
	 * Road Runner Blocks
	 */
	private Map<UUID, RoadRunnerBlock> blocks = new HashMap<UUID, RoadRunnerBlock>();

	private List<Class<? extends EntityLivingBase>> entityClassList = new ArrayList<Class<? extends EntityLivingBase>>();

	public void sync(String[] blocks) {

		this.blocks = new HashMap<UUID, RoadRunnerBlock>();

		Pattern pattern = Pattern.compile("([a-zA-Z0-9_]+?:[a-zA-Z0-9_]+?)");

		for (String entry : blocks) {

			String[] buffer = entry.trim().toLowerCase().split("\\|");

			if ((pattern.matcher(buffer[0]).matches()) && buffer.length >= 2) {

				String blockId = buffer[0];

				Map<String, Float> modifiers = new HashMap<String, Float>();

				for (int idx = 1; idx < buffer.length; idx++) {

					String[] modifier = buffer[idx].split(":");

					modifiers.put(modifier[0], Float.parseFloat(modifier[1]));
				}

				RoadRunnerBlock block = new RoadRunnerBlock("Block{" + blockId + "}", modifiers);

				this.blocks.put(block.getUUID(), block);
			}

			else {

				logError(buffer.toString());
			}
		}
	}

	private void logError(String msg) {

		RoadRunnerRecaffeinatedMod.logger.error("Invalid block or modifier specified in config file...");
		RoadRunnerRecaffeinatedMod.logger.error("Skipping entry -> " + msg);
	}

	/**
	 * NOTE: We are using the uuid of the block, which is derived from the one and
	 * only required attribute modifier; the movement speed. This is so as to
	 * eliminate some of the redundancy and overhead of constantly iterating over
	 * all of the blocks. I'll eventually write a better solution, but for now it
	 * works just fine.
	 */

	public void sync(Map<WhitelistableEntities, Boolean> whitelist) {

		/**
		 * Initialize and then add in the player class...
		 */
		entityClassList = new ArrayList<Class<? extends EntityLivingBase>>();
		entityClassList.add(EntityPlayer.class);
		
		for (Entry<WhitelistableEntities, Boolean> entry : whitelist.entrySet()) {

			if (entry.getValue()) {

				entityToClass(entry.getKey());
			}
		}
	}

	public boolean isEntityWhitelisted(EntityLivingBase entity) {

		boolean isWhitelisted = false;

		for (Class<? extends EntityLivingBase> item : entityClassList) {

			if (item.isInstance(entity)) {

				isWhitelisted = true;
				break;
			}
		}

		return isWhitelisted;
	}

	private void entityToClass(WhitelistableEntities entityName) {

		switch (entityName) {

		case Creepers:

			entityClassList.add(EntityCreeper.class);
			break;

		case Skeletons:

			entityClassList.add(EntitySkeleton.class);
			break;

		case Spiders:

			entityClassList.add(EntitySpider.class);
			break;

		case Zombies:

			entityClassList.add(EntityZombie.class);
			break;

		case ZombieVillagers:

			entityClassList.add(EntityZombieVillager.class);
			break;

		case Villagers:

			entityClassList.add(EntityVillager.class);
			break;
		default:
			break;
		}
	}

	/**
	 * Check if a block exists by id
	 * 
	 * @param blockId
	 * @return
	 */
	public boolean hasBlock(String blockId) {

		boolean found = false;

		for (RoadRunnerBlock block : blocks.values()) {

			if (block.getBlockId().equalsIgnoreCase(blockId)) {

				found = true;
				break;
			}
		}

		return found;
	}

	/**
	 * Check if a block exists by UUID NOTE: This is the preferred way to lookup
	 * blocks...
	 * 
	 * @param modifier
	 * @return
	 */
	public boolean hasModifier(UUID uuid) {

		return blocks.containsKey(uuid);
	}

	public RoadRunnerBlock getBlock(UUID uuid) {

		RoadRunnerBlock found = null;

		for (RoadRunnerBlock block : blocks.values()) {

			if (block.getUUID().equals(uuid)) {

				found = block;
				break;
			}
		}

		return found;
	}

	public RoadRunnerBlock getBlock(String blockId) {

		RoadRunnerBlock found = null;

		for (RoadRunnerBlock block : blocks.values()) {

			if (block.getBlockId().equalsIgnoreCase(blockId)) {

				found = block;
				break;
			}
		}

		return found;
	}

}
