package com.tyr.builders;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSimple extends Item 
{
	public ItemSimple(String itemName)
    {
		setItemName(this, itemName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
    }
	
	public static void setItemName(Item item, final String itemName) 
	{
		item.setRegistryName(TyrBuilders.MODID, itemName);
		item.setUnlocalizedName(item.getRegistryName().toString());
	}
}
