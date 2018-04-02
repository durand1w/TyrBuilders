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

public class BlockChainHeavyGold extends Block
{
	protected static final AxisAlignedBB CHAIN_HEAVY_GOLD_AABB = new AxisAlignedBB(0.3125F, 0.0F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
	
	public BlockChainHeavyGold(String blockName)
	{
		super(Material.IRON);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(3.0F);
		setResistance(30.0F);
		setSoundType(SoundType.METAL);
	}
	
    @Override public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) { return true; }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return CHAIN_HEAVY_GOLD_AABB;
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
    
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return this.canBePlacedOn(worldIn, pos);
    }
    
    private boolean canBePlacedOn(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.up()).isFullCube() || (worldIn.getBlockState(pos.up()).getBlock() == TyrBuildersBlocks.chain_heavy_gold) || (worldIn.getBlockState(pos.up()).getBlock() == TyrBuildersBlocks.chain_heavy_iron) || (worldIn.getBlockState(pos.up()).getBlock() == TyrBuildersBlocks.chain_heavy_wrought_iron);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (!this.canBePlacedOn(worldIn, pos))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
