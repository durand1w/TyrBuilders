package com.tyr.builders;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChainLightIron extends Block
{
	protected static final AxisAlignedBB CHAIN_LIGHT_IRON_AABB = new AxisAlignedBB(0.40625F, 0.0F, 0.40625F, 0.59375F, 1.0F, 0.59375F);
	
	public BlockChainLightIron(String blockName)
	{
		super(Material.IRON);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(5.0F);
		setResistance(30.0F);
		setSoundType(SoundType.METAL);
	}
	
    @Override public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) { return true; }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CHAIN_LIGHT_IRON_AABB;
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
    
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
    {
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		player.world.playSound(null , x, y, z, TyrBuildersSounds.chains_rattle, SoundCategory.BLOCKS , 1.0F, 1.0F);
	    super.harvestBlock(worldIn, player, pos, state, te, stack);
    }
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
