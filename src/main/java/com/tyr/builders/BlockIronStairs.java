package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockIronStairs extends BlockStairs
{
	public BlockIronStairs(String blockName)
	{
		super (Blocks.IRON_BLOCK.getStateFromMeta(0));
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		this.useNeighborBrightness = true;
		setSoundType(SoundType.METAL);
	}
	
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
