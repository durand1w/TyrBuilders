package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockHardenedClayStairs extends BlockStairs
{
	private final String name="hardened_clay_stairs";
	
	public BlockHardenedClayStairs(String blockName)
	{
		super (Blocks.HARDENED_CLAY.getStateFromMeta(0));
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
