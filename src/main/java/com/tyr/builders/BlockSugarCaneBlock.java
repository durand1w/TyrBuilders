package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockSugarCaneBlock extends BlockRotatedPillar
{
	public BlockSugarCaneBlock(String blockName)
	{
		super(Material.WOOD);
		setBlockName(this, blockName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(1.5F);
		setResistance(20F);
		setSoundType(SoundType.WOOD);
	}
	
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing.Axis axis = EnumFacing.Axis.Y;
        int j = meta & 12;

        if (j == 4)
        {
            axis = EnumFacing.Axis.X;
        }
        else if (j == 8)
        {
            axis = EnumFacing.Axis.Z;
        }

        return this.getDefaultState().withProperty(AXIS, axis);
    }
    
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        EnumFacing.Axis axis = (EnumFacing.Axis)state.getValue(AXIS);

        if (axis == EnumFacing.Axis.X)
        {
            i |= 4;
        }
        else if (axis == EnumFacing.Axis.Z)
        {
            i |= 8;
        }

        return i;
    }
    

    protected ItemStack createStackedBlock(IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(this), 1, 0);
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(AXIS, facing.getAxis());
    }
    
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
