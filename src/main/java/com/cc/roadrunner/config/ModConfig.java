package com.cc.roadrunner.config;

import java.util.HashMap;
import java.util.Map;

import com.cc.roadrunner.RoadRunnerRecaffeinatedMod;
import com.cc.roadrunner.utils.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class ModConfig {
	
	
	private ModConfig() {}
	
	private static ModConfig instance;
	
	public static ModConfig getInstance() {
		
		if( instance == null ) { instance = new ModConfig(); }
		
		return instance;
	}
		
	
	private static Configuration config;
	
	/**
	 * Getter for configuration instance 
	 * 
	 * NOTE:  init *must* be called at least once first,
	 * before getting the instance of the configuration!!!
	 * @return instance of Configuration.
	 */
	public static Configuration getConfig() {
		
		return config;
	}
	
	/**
	 * Properties for entity white list
	 */
	
	
	public static final String[] CATEGORIES = {"White Listed Entities", "Blocks"};
			
//	public static boolean WHITELIST_CREEPER = false;
//	public static boolean WHITELIST_SKELETON = false;
//	public static boolean WHITELIST_SPIDER = false;
//	public static boolean WHITELIST_ZOMBIE = false;
//	public static boolean WHITELIST_ZOMBIE_VILLAGER = false;
//	public static boolean WHITELIST_VILLAGER = false;
	
	/**
	 * List of the default blocks,  only the movement is required.
	 */
	private static final String[] DEFAULT_BLOCKS = {
		
		"minecraft:concrete|movement:2.5",
		"minecraft:grass_path|movement:1.5",
		"minecraft:stonebrick|movement:2.0"		
	};


	public static void init(FMLPreInitializationEvent event) {
		
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		sync();
	}
	
	@SuppressWarnings("static-access")
	public static void sync() {
		
		Configuration cfg = getConfig();
		
		@SuppressWarnings("serial")
		Map<WhitelistableEntities, Boolean> whitelist = new HashMap<WhitelistableEntities, Boolean>() {{
			
			put(WhitelistableEntities.Creepers, 		cfg.getBoolean(WhitelistableEntities.Creepers.getValue(), 			CATEGORIES[0], false, "Add Creepers to whitelist?" ));
			put(WhitelistableEntities.Skeletons, 		cfg.getBoolean(WhitelistableEntities.Skeletons.getValue(), 			CATEGORIES[0], false, "Add Skeletons to whitelist?" ));
			put(WhitelistableEntities.Spiders, 			cfg.getBoolean(WhitelistableEntities.Spiders.getValue(), 			CATEGORIES[0], false, "Add Spider to whitelist?" ));
			put(WhitelistableEntities.Zombies, 			cfg.getBoolean(WhitelistableEntities.Zombies.getValue(), 			CATEGORIES[0], false, "Add Zombies to whitelist?" ));
			put(WhitelistableEntities.ZombieVillagers, 	cfg.getBoolean(WhitelistableEntities.ZombieVillagers.getValue(), 	CATEGORIES[0], false, "Add Zombie Villagers to whitelist?" ));
			put(WhitelistableEntities.Villagers, 		cfg.getBoolean(WhitelistableEntities.Villagers.getValue(), 			CATEGORIES[0], false, "Add Villagers to whitelist?" ) );
		}};
		
		
		config.setCategoryComment(CATEGORIES[0], "Entities to add attribute modifiers to...");
		

		String[] blocks = cfg.getStringList("Road Blocks", CATEGORIES[1], DEFAULT_BLOCKS, "A list of road blocks for applying modifiers!");
		
		
		if( config.hasChanged() ) {
			
			config.save();
		}
		
		StateManager.getInstance().sync(blocks);
		StateManager.getInstance().sync(whitelist);
		
		if(RoadRunnerRecaffeinatedMod.logger != null) {
		
			RoadRunnerRecaffeinatedMod.logger.info("(Re)Syncing config info...");
		}		
	}
	
		
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event ) {
		
		if(event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
			
			sync();
		}
	}
	
	public static class WhiteListMap extends HashMap<WhitelistableEntities, Boolean> {

		/**
		 *   Creating a basic,  custom mapping of entity names -> bool values... 
		 */
		private static final long serialVersionUID = -2226562108452434432L;	
	}
	
}
