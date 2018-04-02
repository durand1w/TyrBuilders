package com.tyr.builders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonObject;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import scala.actors.threadpool.Arrays;

public class CommonProxy 
{
	//Declare Events
	LootTyrBuilders eventsLootTyrBuilders = new LootTyrBuilders();
		
	public void preInit(FMLPreInitializationEvent e) 
    {   			  	
		TyrBuildersBlocks eventTyrBuildersBlocks = new TyrBuildersBlocks();
		TyrBuildersItems eventTyrBuildersItems = new TyrBuildersItems();   
		GameRegistry.registerFuelHandler(new TyrBuildersFuelHandler());
    }
	
    public void init(FMLInitializationEvent e) 
    {
    	//Event Handler Registry
    	FMLCommonHandler.instance().bus().register(eventsLootTyrBuilders);
    	MinecraftForge.EVENT_BUS.register(eventsLootTyrBuilders);
    	    	   	
    	//RECIPES
    	List<String> TyrRecipes = new ArrayList();
    	Iterator<String> TyrRecipesIterator = TyrRecipes.iterator();
    	while (TyrRecipesIterator.hasNext())
    	{
        	CraftingHelper.register(new ResourceLocation(TyrBuilders.MODID + "_" + TyrRecipesIterator.next() +".json"), new IRecipeFactory()
        	{
        		@Override
        		public IRecipe parse(JsonContext context, JsonObject json)
        		{
        			return CraftingHelper.getRecipe(json,  context);
        		}
        	});
    	}
    	
    	GameRegistry.addSmelting(Blocks.GLASS, new ItemStack(TyrBuildersBlocks.glass_tempered), 0.0F);
    	GameRegistry.addSmelting(Items.IRON_INGOT, new ItemStack(TyrBuildersItems.wrought_iron_ingot), 0.0F);
  


    	
    	
//    	//Stringer Banner Triangles
//    	GameRegistry.addShapelessRecipe(new ItemStack(TyrBuildersBlocks.stringer_spacer), new Object[]
//    			{
//    				new ItemStack(TyrBuildersBlocks.rope_climbers), new ItemStack(Items.STICK)
//    			});
    	
    	
//    	GameRegistry.addShapelessRecipe(new ItemStack(TyrBuildersBlocks.rope_climbers), new Object[]
//    			{
//    				new ItemStack(TyrBuildersBlocks.stringer_spacer)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_white_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,0)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_orange_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,1)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_magenta_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,2)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_light_blue_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,3)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_yellow_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,4)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_lime_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,5)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_pink_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,6)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_grey_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,7)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_light_grey_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,8)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_cyan_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,9)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_purple_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,10)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_blue_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,11)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_brown_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,12)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_green_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,13)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_red_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,14)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_black_triangle), new Object[]
//    			{
//    				" A ",
//    				" B ",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,15)
//    			});

    	
    	
    	
    	
//    	
//    	//Stringer Banner Dovetails
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_white_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,0)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_orange_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,1)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_magenta_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,2)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_light_blue_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,3)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_yellow_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,4)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_lime_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,5)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_pink_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,6)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_grey_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,7)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_light_grey_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,8)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_cyan_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,9)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_purple_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,10)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_blue_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,11)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_brown_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,12)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_green_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,13)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_red_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,14)
//    			});
//    	GameRegistry.addRecipe(new ItemStack(TyrBuildersBlocks.stringer_black_dovetail), new Object[]
//    			{
//    				" A ",
//    				"B B",
//    				'A', new ItemStack(TyrBuildersBlocks.stringer_spacer), 'B', new ItemStack(Blocks.CARPET,1,15)
//    			});
//    	
    	
   	
    }
}
