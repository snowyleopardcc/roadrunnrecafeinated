/**
 * 
 */
package com.cc.roadrunner.config;

/**
 * @author cc
 *
 *
 *	This is an enumeration / list of all entities that are able
 *	to be white listed for applying modifiers to...
 *
 * 	Please be careful when adding to this list that the corresponding
 *  entity class is added into the switch statement in the state manager!
 */
public enum WhitelistableEntities {

	Creepers("creeper"),
	Skeletons("skeleton"),
	Spiders("spiders"),
	Zombies("zombies"),
	ZombieVillagers("zombie_villagers"),
	Villagers("villagers");
	
	
	private WhitelistableEntities(String name) {
		
		this.name = name;
	}
	
	private String name;
	
	public String getValue() {
		
		return this.name;
	}
}
