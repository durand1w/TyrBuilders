package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrateSmall extends Block
{
	//Directions labels are not true
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	//EAST
	protected static final AxisAlignedBB CRATE_SMALL_ACACIA_SOUTH_AABB = new AxisAlignedBB(0.1875F, 0.0F, 0.125F, 0.9375F, 0.8125F, 0.875F);
	//SOUTH
	protected static final AxisAlignedBB CRATE_SMALL_ACACIA_WEST_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.1875F, 0.875F, 0.8125F, 0.9375F);
	//WEST
	protected static final AxisAlignedBB CRATE_SMALL_ACACIA_NORTH_AABB = new AxisAlignedBB(0.0625, 0.0F, 0.125F, 0.8125F, 0.8125F, 0.875F);
	//NORTH
	protected static final AxisAlignedBB CRATE_SMALL_ACACIA_EAST_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.0625F, 0.875F, 0.8125F, 0.8125F);
	
	public BlockCrateSmall(String blockName)
	{
		super(Material.WOOD);
		setBlockName(this, blockName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(2.0F);
		setResistance(10F);
		setSoundType(SoundType.WOOD);
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb;

            switch ((EnumFacing)state.getValue(FACING))
            {
                case NORTH:
                default:
                    axisalignedbb = CRATE_SMALL_ACACIA_NORTH_AABB;
                    break;
                case SOUTH:
                    axisalignedbb = CRATE_SMALL_ACACIA_SOUTH_AABB;
                    break;
                case WEST:
                    axisalignedbb = CRATE_SMALL_ACACIA_WEST_AABB;
                    break;
                case EAST:
                    axisalignedbb = CRATE_SMALL_ACACIA_EAST_AABB;
            }
        return axisalignedbb;
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
    
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing enumfacing1 = placer.getHorizontalFacing().rotateY();
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, enumfacing1);
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    static final class SwitchEnumFacing
        {
            static final int[] FACING_LOOKUP = new int[EnumFacing.values().length];
            private static final String __OBFID = "CL_00002104";

            static
            {
                try
                {
                    FACING_LOOKUP[EnumFacing.NORTH.ordinal()] = 1;
                }
                catch (NoSuchFieldError var4)
                {
                    ;
                }

                try
                {
                    FACING_LOOKUP[EnumFacing.SOUTH.ordinal()] = 3;
                }
                catch (NoSuchFieldError var3)
                {
                    ;
                }

                try
                {
                    FACING_LOOKUP[EnumFacing.WEST.ordinal()] = 4;
                }
                catch (NoSuchFieldError var2)
                {
                    ;
                }

                try
                {
                    FACING_LOOKUP[EnumFacing.EAST.ordinal()] = 2;
                }
                catch (NoSuchFieldError var1)
                {
                    ;
                }
            }
        }
	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
