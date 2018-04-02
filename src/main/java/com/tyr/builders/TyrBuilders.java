package com.tyr.builders;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TyrBuilders.MODID, version = TyrBuilders.VERSION)
public class TyrBuilders
{
    public static final String MODID = "tyrbuilders";
    public static final String VERSION = "1.1.12_01";
       
    
    @SidedProxy(clientSide="com.tyr.builders.ClientProxy", serverSide="com.tyr.builders.ServerProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void preinit(FMLPreInitializationEvent e)
    {   	
    	this.proxy.preInit(e);
    }  	

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
    	this.proxy.init(e);
    }
}
    
  
 