package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockStoneBrick extends Block
{
	public BlockStoneBrick(String blockName)
	{
		super(Material.ROCK);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(1.5F);
		setResistance(30.0F);
		setSoundType(SoundType.STONE);
	}
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}

