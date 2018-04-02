package com.tyr.builders;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRopeSturdy extends Block
{
	protected static final AxisAlignedBB ROPE_STURDY_AABB = new AxisAlignedBB(0.4375F, 0.0F, 0.4375F, 0.5625F, 1.0F, 0.5625F);
	
	public BlockRopeSturdy(String blockName)
	{
		super(Material.CLOTH);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(0.8F);
		setResistance(4.0F);
		setSoundType(SoundType.CLOTH);
	}
	
    @Override public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) { return true; }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return ROPE_STURDY_AABB;
    }
		
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return this.canBePlacedOn(worldIn, pos);
    }
    
    private boolean canBePlacedOn(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.up()).isFullCube() || (worldIn.getBlockState(pos.up()).getBlock() == TyrBuildersBlocks.rope_sturdy);// || (worldIn.getBlockState(pos.up()).getBlock() == TyrBuildersBlocks.stringer_spacer);
    }
    

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        this.checkForDrop(worldIn, pos, state);
    }

    protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state)
    {
        if (this.canBlockStay(worldIn, pos))
        {
            return true;
        }
        else
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            return false;
        }
    }
    
    public boolean canBlockStay(World worldIn, BlockPos pos)
    {
        return this.canPlaceBlockAt(worldIn, pos);
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
