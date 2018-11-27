package com.cc.roadrunner.proxy;

import com.cc.roadrunner.BlockHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {

	}

	public void init(FMLInitializationEvent event) {

		MinecraftForge.EVENT_BUS.register(new BlockHandler());

	}

	public void postInit(FMLPostInitializationEvent event) {
		// TODO Auto-generated method stub

	}

}
