package com.tyr.builders;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(TyrBuilders.MODID)
public class TyrBuildersItems 
{
	public static CreativeTabs tabTyrBuilders = new TyrBuildersTab("TyrBuilders");
	
	public static Item beeswax = new ItemSimple("beeswax");
	public static Item fertilizer = new ItemFertilizer("fertilizer");
	public static Item honeycomb = new ItemSimple("honeycomb");
	public static Item seed_sunflower = new ItemSimpleFood("seed_sunflower", 1, 0.23F, false);
	public static Item sunflower_oil = new ItemSimple("sunflower_oil");
	public static Item wrought_iron_nugget = new ItemSimple("wrought_iron_nugget");
	public static Item wrought_iron_ingot = new ItemSimple("wrought_iron_ingot");
	
	
	@Mod.EventBusSubscriber(modid = TyrBuilders.MODID)
	public static class RegistrationHandler 
	{
		@SubscribeEvent
		public static void registerItems(final RegistryEvent.Register<Item> event)
		{
			final IForgeRegistry<Item> registry = event.getRegistry();
			registry.register(beeswax);			
			registry.register(fertilizer);
			registry.register(honeycomb);
			registry.register(seed_sunflower);
			registry.register(sunflower_oil);
			registry.register(wrought_iron_nugget);
			registry.register(wrought_iron_ingot);
		}
		
		@SubscribeEvent
		public static void registerModels(ModelRegistryEvent event)
		{			
			ModelLoader.setCustomModelResourceLocation(beeswax, 0, new ModelResourceLocation(beeswax.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(fertilizer, 0, new ModelResourceLocation(fertilizer.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(honeycomb, 0, new ModelResourceLocation(honeycomb.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(seed_sunflower, 0, new ModelResourceLocation(seed_sunflower.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(sunflower_oil, 0, new ModelResourceLocation(sunflower_oil.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(wrought_iron_nugget, 0, new ModelResourceLocation(wrought_iron_nugget.getRegistryName(), "inventory"));
			ModelLoader.setCustomModelResourceLocation(wrought_iron_ingot, 0, new ModelResourceLocation(wrought_iron_ingot.getRegistryName(), "inventory"));
		}
	}
}
