package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCharcoalBlock extends Block 
{
	public BlockCharcoalBlock(String blockName)
	{
		super(Material.ROCK);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(5F);
		setResistance(30F);
		setSoundType(SoundType.STONE);
	}
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
