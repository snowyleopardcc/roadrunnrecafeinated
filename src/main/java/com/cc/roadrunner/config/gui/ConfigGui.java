package com.cc.roadrunner.config.gui;

import java.util.ArrayList;
import java.util.List;

import com.cc.roadrunner.config.ModConfig;
import com.cc.roadrunner.utils.Reference;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ConfigGui extends GuiConfig {

	/**
	 * Shoutout to the guys over at *Primal Tech* for great code examples!  :D
	 * @param screen
	 */
	public ConfigGui(GuiScreen screen) {
		
		super(
				screen,
				getCategories(),
				Reference.MOD_ID,
				Reference.MOD_ID,
				false,
				false,
				"Road Runner Blocks -- Config"
		);
	}

	private static List<IConfigElement> getCategories() {
		
		ModConfig config = ModConfig.getInstance();
		
		List<IConfigElement> elements = new ArrayList<IConfigElement>();
		
		for(String cat : config.CATEGORIES) {
			
			elements.add(new ConfigElement(config.getConfig().getCategory(cat)));
		}
		
		return elements;
	}
}
