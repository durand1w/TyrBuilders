package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom.Item;
import net.minecraftforge.fml.common.IFuelHandler;

public class TyrBuildersFuelHandler implements IFuelHandler 
{

	@Override
	public int getBurnTime(ItemStack fuel) 
	{
		Block blockFuel = Block.getBlockFromItem(fuel.getItem());
        net.minecraft.item.Item itemFuel = fuel.getItem();
        if (blockFuel == TyrBuildersBlocks.charcoal_block)
        {
            return 16000;//Equal to Coal
        }
        else
        {
        	return 0;//Do not change this 0.
        }
	}

}
