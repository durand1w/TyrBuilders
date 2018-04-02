package com.tyr.builders;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockWheelShip extends Block
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	protected static final AxisAlignedBB WHEEL_SHIP_ACACIA_SOUTH_AABB = new AxisAlignedBB(0.0F, 0.0625F, 0.6875F, 0.9375F, 1.0F, 0.875F);
	protected static final AxisAlignedBB WHEEL_SHIP_ACACIA_POST_SOUTH_AABB = new AxisAlignedBB(0.375F, 0.0F, 0.375F, 0.625F, 0.6875F, 0.6875F);	
	protected static final AxisAlignedBB WHEEL_SHIP_ACACIA_WEST_AABB = new AxisAlignedBB(0.125F, 0.0625F, 0.0F, 0.3125F, 1.0F, 0.9375F);
	protected static final AxisAlignedBB WHEEL_SHIP_ACACIA_POST_WEST_AABB = new AxisAlignedBB(0.3125F, 0.0F, 0.375F, 0.625F, 0.6875F, 0.625F);	
	protected static final AxisAlignedBB WHEEL_SHIP_ACACIA_NORTH_AABB = new AxisAlignedBB(0.0625F, 0.0625F, 0.3125F, 1.0F, 1.0F, 0.1255F);
	protected static final AxisAlignedBB WHEEL_SHIP_ACACIA_POST_NORTH_AABB = new AxisAlignedBB(0.375F, 0.0F, 0.625F, 0.625F, 0.6875F, 0.3125F);
	protected static final AxisAlignedBB WHEEL_SHIP_ACACIA_EAST_AABB = new AxisAlignedBB(0.6875F, 0.0625F, 0.0625F, 0.875F, 1.0F, 1.0F);
	protected static final AxisAlignedBB WHEEL_SHIP_ACACIA_POST_EAST_AABB = new AxisAlignedBB(0.375F, 0.0F, 0.375F, 0.6875F, 0.6875F, 0.625F);	
	
	public BlockWheelShip(String blockName)
	{
		super(Material.WOOD);
		setBlockName(this, blockName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(1.5F);
		setResistance(15F);
		setSoundType(SoundType.WOOD);
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb;

            switch ((EnumFacing)state.getValue(FACING))
            {
                case NORTH:
                default:
                    axisalignedbb = WHEEL_SHIP_ACACIA_NORTH_AABB;
                    break;
                case SOUTH:
                    axisalignedbb = WHEEL_SHIP_ACACIA_SOUTH_AABB;
                    break;
                case WEST:
                    axisalignedbb = WHEEL_SHIP_ACACIA_WEST_AABB;
                    break;
                case EAST:
                    axisalignedbb = WHEEL_SHIP_ACACIA_EAST_AABB;
            }
        return axisalignedbb;
    }
    
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        state = this.getActualState(state, worldIn, pos);

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
        }
    }
    
    private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate)
    {
        List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
        list.add(getWheelBlock(bstate));
        list.add(getPostBlock(bstate));
        return list;
    }
    
    private static AxisAlignedBB getWheelBlock(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return WHEEL_SHIP_ACACIA_NORTH_AABB;
            case SOUTH:
                return WHEEL_SHIP_ACACIA_SOUTH_AABB;
            case WEST:
                return WHEEL_SHIP_ACACIA_EAST_AABB;
            case EAST:
                return WHEEL_SHIP_ACACIA_WEST_AABB;
        }
    }
    
    private static AxisAlignedBB getPostBlock(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return WHEEL_SHIP_ACACIA_POST_NORTH_AABB;
            case SOUTH:
                return WHEEL_SHIP_ACACIA_POST_SOUTH_AABB;
            case WEST:
                return WHEEL_SHIP_ACACIA_POST_EAST_AABB;
            case EAST:
                return WHEEL_SHIP_ACACIA_POST_WEST_AABB;
        }
    }

    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();

        for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos)))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for (RayTraceResult raytraceresult : list)
        {
            if (raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if (d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }
	
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return true;
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) 
    {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
