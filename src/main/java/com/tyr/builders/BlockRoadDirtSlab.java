package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRoadDirtSlab extends Block
{
	protected static final AxisAlignedBB ROAD_DIRT_SLAB_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D , 0.375D, 1.0D);
	
	public BlockRoadDirtSlab(String blockName)
	{
		super(Material.ROCK);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		this.useNeighborBrightness = true;
		setHardness(1.6F);
		setResistance(16F);
		setSoundType(SoundType.GROUND);
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return ROAD_DIRT_SLAB_AABB;
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
    
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos.down());
        return super.canPlaceBlockAt(worldIn, pos) && iblockstate.getBlock().isFullBlock(iblockstate);
    }
    
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos.down());
        return iblockstate.getBlock().isFullBlock(iblockstate);
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    	if (entityIn instanceof EntityPlayer)
    	{
            entityIn.motionX *= 1.01D;
            entityIn.motionZ *= 1.01D;
    	}
    	else if (entityIn instanceof EntityHorse)
    	{
            entityIn.motionX *= 1.005D;
            entityIn.motionZ *= 1.005D;
    	}
    }
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
