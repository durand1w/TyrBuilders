package com.tyr.builders;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class ItemSimpleFood extends ItemFood 
{
	public ItemSimpleFood(String itemName, int amount, float saturation, boolean isWolfFood)
    {
		super(amount, saturation, isWolfFood);
		setItemName(this, itemName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
    }
	
	public static void setItemName(Item item, final String itemName) 
	{
		item.setRegistryName(TyrBuilders.MODID, itemName);
		item.setUnlocalizedName(item.getRegistryName().toString());
	}
}
