package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;

public class BlockStoneStairs extends BlockStairs
{
	public BlockStoneStairs(String blockName)
	{
		super (Blocks.STONE.getStateFromMeta(0));
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		this.useNeighborBrightness = true;
	}
	
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
