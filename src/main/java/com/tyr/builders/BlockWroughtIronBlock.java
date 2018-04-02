package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockWroughtIronBlock extends Block
{
	public BlockWroughtIronBlock(String blockName)
	{
		super(Material.IRON);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(5.0F);
		setResistance(35.0F);
		setSoundType(SoundType.METAL);
	}
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
