package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockWroughtIronBars extends BlockPane 
{
	public BlockWroughtIronBars(String blockName)
	{
		super(Material.IRON, true);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(5F);
		setResistance(35F);
		setSoundType(SoundType.METAL);
	}
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
