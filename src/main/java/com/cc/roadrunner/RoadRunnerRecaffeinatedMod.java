package com.cc.roadrunner;

import org.apache.logging.log4j.Logger;

import com.cc.roadrunner.config.ModConfig;
import com.cc.roadrunner.proxy.CommonProxy;
import com.cc.roadrunner.utils.Reference;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, guiFactory = Reference.GUI_FACTORY)
public class RoadRunnerRecaffeinatedMod {

	@Mod.Instance(Reference.MOD_ID)
	public static RoadRunnerRecaffeinatedMod instance;

	public static Logger logger;

	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS )
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event ) {
				
		ModConfig.getInstance().init(event);
				
		logger = event.getModLog();
		this.proxy.preInit(event);		

	}
	
	@EventHandler
	public void init( FMLInitializationEvent event ) {
		MinecraftForge.EVENT_BUS.register(ModConfig.getInstance());
		
		this.proxy.init(event);	
	}
	
	@EventHandler
	public void postInit( FMLPostInitializationEvent event ) {
		
		this.proxy.postInit(event);
		
	}
}
