package com.tyr.builders;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TyrBuildersTab extends CreativeTabs 
{
	public TyrBuildersTab (String label)
	{
		super(label);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem() 
	{
		return new ItemStack(TyrBuildersBlocks.barrel_acacia);
	}
}