package com.tyr.builders;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LootTyrBuilders 
{

	@SubscribeEvent
	public void onLootTablesLoaded(LootTableLoadEvent event) 
	{
	 
	    if (event.getName().equals(LootTableList.CHESTS_DESERT_PYRAMID)) 
	    {
	 
	    	final LootPool main = event.getTable().getPool("main");
	        final LootPool pool1 = event.getTable().getPool("pool1");
	        
	        if (main != null)
	        {
	        	main.addEntry(new LootEntryItem(Item.getItemFromBlock(TyrBuildersBlocks.sandstone_carved_lost_one), 25, 0, new LootFunction[0], new LootCondition[0], "tyrbuilders:sandstone_carved_lost_one"));
	        	main.addEntry(new LootEntryItem(Item.getItemFromBlock(TyrBuildersBlocks.sandstone_carved_lost_two), 25, 0, new LootFunction[0], new LootCondition[0], "tyrbuilders:sandstone_carved_lost_two"));
	        }
	        if (pool1 != null) 
	        {
	            // pool1.addEntry(new LootEntryItem(ITEM, WEIGHT, QUALITY, FUNCTIONS, CONDITIONS, NAME));
	            pool1.addEntry(new LootEntryItem(Item.getItemFromBlock(TyrBuildersBlocks.sandstone_pillar), 10, 0, new LootFunction[0], new LootCondition[0], "tyrbuilders:sandstone_pillar"));
	        }
	    }
	    
	    if (event.getName().equals(LootTableList.CHESTS_ABANDONED_MINESHAFT)) 
	    {
	 
	    	final LootPool main = event.getTable().getPool("main");
	        if (main != null)
	        {
	        	main.addEntry(new LootEntryItem(Item.getItemFromBlock(TyrBuildersBlocks.copper_patina_roof_tile_stairs), 25, 0, new LootFunction[0], new LootCondition[0], "tyrbuilders:copper_patina_roof_tile_stairs"));
		        
	        }
	    }      	
	}
	
	public Random Rand = new Random();
	
	@SubscribeEvent
	public void onBlockDestroyed(HarvestDropsEvent event)
	{
		
		if (event.getState().getBlock() == Blocks.LEAVES || event.getState().getBlock() == Blocks.LEAVES2) 
		{
			if (Rand.nextInt(300) == 77)
			{
				event.getDrops().add(new ItemStack(TyrBuildersBlocks.beehive, 1));
			}
		}
		
	}
}
