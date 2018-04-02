package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStonePedestal extends Block 
{
	protected static final AxisAlignedBB STONE_PEDESTAL_SLAB_AABB = new AxisAlignedBB(0.375F, 0.0F, 0.375F, 0.625F , 1.0F, 0.625F);
		
	public BlockStonePedestal(String blockName)
	{
		super(Material.ROCK);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		useNeighborBrightness = true;
		setHardness(1.5F);
		setResistance(30F);
		setSoundType(SoundType.STONE);
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return STONE_PEDESTAL_SLAB_AABB;
    }
		
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	return false;
    }
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}

