package com.tyr.builders;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
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

public class BlockLogSharp extends BlockDirectional
{
	public static DamageSource spikeDamage = new DamageSource("spikes");

	protected static final AxisAlignedBB[] LOG_SHARP_OAK_COLLISION_SOUTH = new AxisAlignedBB[] {new AxisAlignedBB(0.0, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D), new AxisAlignedBB(0.0625D, 0.0625D, 0.125D, 0.9375D, 0.9375D, 0.25D), new AxisAlignedBB(0.125D, 0.125D, 0.25D, 0.875D, 0.875D, 0.375D), new AxisAlignedBB(0.1875D, 0.1875D, 0.375D, 0.8125D, 0.8125D, 0.5D), new AxisAlignedBB(0.25D, 0.25D, 0.5D, 0.75D, 0.75D, 0.625D), new AxisAlignedBB(0.3125D, 0.3125D, 0.625D, 0.6875D, 0.6875D, 0.75D), new AxisAlignedBB(0.375D, 0.375D, 0.75D, 0.625D, 0.625D, 0.875D), new AxisAlignedBB(0.4375D, 0.4375D, 0.875D, 0.5625D, 0.5625D, 1.0D)};
	protected static final AxisAlignedBB[] LOG_SHARP_OAK_COLLISION_WEST = new AxisAlignedBB[] {new AxisAlignedBB(1.0, 0.0D, 0.0D, 0.875D, 1.0D, 1.0D), new AxisAlignedBB(0.875D, 0.0625D, 0.0625D, 0.75D, 0.9375D, 0.9375D), new AxisAlignedBB(0.75D, 0.125D, 0.125D, 0.625D, 0.875D, 0.875D), new AxisAlignedBB(0.625D, 0.1875D, 0.1875D, 0.5D, 0.8125D, 0.8125D), new AxisAlignedBB(0.5D, 0.25D, 0.25D, 0.375D, 0.75D, 0.75D), new AxisAlignedBB(0.375D, 0.3125D, 0.3125D, 0.25D, 0.6875D, 0.6875D), new AxisAlignedBB(0.25D, 0.375D, 0.375D, 0.125D, 0.625D, 0.625D), new AxisAlignedBB(0.125D, 0.4375D, 0.4375D, 0.0D, 0.5625D, 0.5625D)};
	protected static final AxisAlignedBB[] LOG_SHARP_OAK_COLLISION_NORTH = new AxisAlignedBB[] {new AxisAlignedBB(0.0, 0.0D, 1.0D, 1.0D, 1.0D, 0.875D), new AxisAlignedBB(0.0625D, 0.0625D, 0.875D, 0.9375D, 0.9375D, 0.75D), new AxisAlignedBB(0.125D, 0.125D, 0.75D, 0.875D, 0.875D, 0.625D), new AxisAlignedBB(0.1875D, 0.1875D, 0.625D, 0.8125D, 0.8125D, 0.5D), new AxisAlignedBB(0.25D, 0.25D, 0.5D, 0.75D, 0.75D, 0.375D), new AxisAlignedBB(0.3125D, 0.3125D, 0.375D, 0.6875D, 0.6875D, 0.25D), new AxisAlignedBB(0.375D, 0.375D, 0.25D, 0.625D, 0.625D, 0.125D), new AxisAlignedBB(0.4375D, 0.4375D, 0.125D, 0.5625D, 0.5625D, 0.0D)};
	protected static final AxisAlignedBB[] LOG_SHARP_OAK_COLLISION_EAST = new AxisAlignedBB[] {new AxisAlignedBB(0.0, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D), new AxisAlignedBB(0.125D, 0.0625D, 0.0625D, 0.25D, 0.9375D, 0.9375D), new AxisAlignedBB(0.25D, 0.125D, 0.125D, 0.375D, 0.875D, 0.875D), new AxisAlignedBB(0.375D, 0.1875D, 0.1875D, 0.5D, 0.8125D, 0.8125D), new AxisAlignedBB(0.5D, 0.25D, 0.25D, 0.625D, 0.75D, 0.75D), new AxisAlignedBB(0.625D, 0.3125D, 0.3125D, 0.75D, 0.6875D, 0.6875D), new AxisAlignedBB(0.75D, 0.375D, 0.375D, 0.875D, 0.625D, 0.625D), new AxisAlignedBB(0.875D, 0.4375D, 0.4375D, 1.0D, 0.5625D, 0.5625D)};
	protected static final AxisAlignedBB[] LOG_SHARP_OAK_COLLISION_UP = new AxisAlignedBB[] {new AxisAlignedBB(0.0, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0625D, 0.125D, 0.0625D, 0.9375D, 0.25D, 0.9375D), new AxisAlignedBB(0.125D, 0.25D, 0.125D, 0.875D, 0.375D, 0.875D), new AxisAlignedBB(0.1875D, 0.375D, 0.1875D, 0.8125D, 0.5D, 0.8125D), new AxisAlignedBB(0.25D, 0.5D, 0.25D, 0.75D, 0.625D, 0.75D), new AxisAlignedBB(0.3125D, 0.625D, 0.3125D, 0.6875D, 0.75D, 0.6875D), new AxisAlignedBB(0.3725D, 0.75D, 0.3725D, 0.625D, 0.875D, 0.625D), new AxisAlignedBB(0.4375D, 0.875D, 0.4375D, 0.5625D, 1.0D, 0.5625D)};
	protected static final AxisAlignedBB[] LOG_SHARP_OAK_COLLISION_DOWN = new AxisAlignedBB[] {new AxisAlignedBB(0.0, 1.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0625D, 0.875D, 0.0625D, 0.9375D, 0.75D, 0.9375D), new AxisAlignedBB(0.125D, 0.75D, 0.125D, 0.875D, 0.625D, 0.875D), new AxisAlignedBB(0.1875D, 0.625D, 0.1875D, 0.8125D, 0.5D, 0.8125D), new AxisAlignedBB(0.25D, 0.5D, 0.25D, 0.75D, 0.375D, 0.75D), new AxisAlignedBB(0.3125D, 0.375D, 0.3125D, 0.6875D, 0.25D, 0.6875D), new AxisAlignedBB(0.3725D, 0.25D, 0.3725D, 0.625D, 0.125D, 0.625D), new AxisAlignedBB(0.4375D, 0.125D, 0.4375D, 0.5625D, 0.0D, 0.5625D)};

	
	public BlockLogSharp(String blockName)
	{
		super(Material.WOOD);
		setBlockName(this, blockName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(2F);
		setResistance(10F);
		setSoundType(SoundType.WOOD);
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FULL_BLOCK_AABB;
    }
    
    @Override
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
        list.add(getLayer1(bstate));
        list.add(getLayer2(bstate));
        list.add(getLayer3(bstate));
        list.add(getLayer4(bstate));
        list.add(getLayer5(bstate));
        list.add(getLayer6(bstate));
        list.add(getLayer7(bstate));
        list.add(getLayer8(bstate));
        return list;
    }
    
    private static AxisAlignedBB getLayer1(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return LOG_SHARP_OAK_COLLISION_NORTH[0];
            case SOUTH:
                return LOG_SHARP_OAK_COLLISION_SOUTH[0];
            case WEST:
                return LOG_SHARP_OAK_COLLISION_WEST[0];
            case EAST:
                return LOG_SHARP_OAK_COLLISION_EAST[0];
            case UP:
                return LOG_SHARP_OAK_COLLISION_UP[0];
            case DOWN:
                return LOG_SHARP_OAK_COLLISION_DOWN[0];
        }
    }
    
    private static AxisAlignedBB getLayer2(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return LOG_SHARP_OAK_COLLISION_NORTH[1];
            case SOUTH:
                return LOG_SHARP_OAK_COLLISION_SOUTH[1];
            case WEST:
                return LOG_SHARP_OAK_COLLISION_WEST[1];
            case EAST:
                return LOG_SHARP_OAK_COLLISION_EAST[1];
            case UP:
                return LOG_SHARP_OAK_COLLISION_UP[1];
            case DOWN:
                return LOG_SHARP_OAK_COLLISION_DOWN[1];
        }
    }
    
    private static AxisAlignedBB getLayer3(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return LOG_SHARP_OAK_COLLISION_NORTH[2];
            case SOUTH:
                return LOG_SHARP_OAK_COLLISION_SOUTH[2];
            case WEST:
                return LOG_SHARP_OAK_COLLISION_WEST[2];
            case EAST:
                return LOG_SHARP_OAK_COLLISION_EAST[2];
            case UP:
                return LOG_SHARP_OAK_COLLISION_UP[2];
            case DOWN:
                return LOG_SHARP_OAK_COLLISION_DOWN[2];
        }
    }
    
    private static AxisAlignedBB getLayer4(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return LOG_SHARP_OAK_COLLISION_NORTH[3];
            case SOUTH:
                return LOG_SHARP_OAK_COLLISION_SOUTH[3];
            case WEST:
                return LOG_SHARP_OAK_COLLISION_WEST[3];
            case EAST:
                return LOG_SHARP_OAK_COLLISION_EAST[3];
            case UP:
                return LOG_SHARP_OAK_COLLISION_UP[3];
            case DOWN:
                return LOG_SHARP_OAK_COLLISION_DOWN[3];
        }
    }
    
    private static AxisAlignedBB getLayer5(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return LOG_SHARP_OAK_COLLISION_NORTH[4];
            case SOUTH:
                return LOG_SHARP_OAK_COLLISION_SOUTH[4];
            case WEST:
                return LOG_SHARP_OAK_COLLISION_WEST[4];
            case EAST:
                return LOG_SHARP_OAK_COLLISION_EAST[4];
            case UP:
                return LOG_SHARP_OAK_COLLISION_UP[4];
            case DOWN:
                return LOG_SHARP_OAK_COLLISION_DOWN[4];
        }
    }
    
    private static AxisAlignedBB getLayer6(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return LOG_SHARP_OAK_COLLISION_NORTH[5];
            case SOUTH:
                return LOG_SHARP_OAK_COLLISION_SOUTH[5];
            case WEST:
                return LOG_SHARP_OAK_COLLISION_WEST[5];
            case EAST:
                return LOG_SHARP_OAK_COLLISION_EAST[5];
            case UP:
                return LOG_SHARP_OAK_COLLISION_UP[5];
            case DOWN:
                return LOG_SHARP_OAK_COLLISION_DOWN[5];
        }
    }
    
    private static AxisAlignedBB getLayer7(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return LOG_SHARP_OAK_COLLISION_NORTH[6];
            case SOUTH:
                return LOG_SHARP_OAK_COLLISION_SOUTH[6];
            case WEST:
                return LOG_SHARP_OAK_COLLISION_WEST[6];
            case EAST:
                return LOG_SHARP_OAK_COLLISION_EAST[6];
            case UP:
                return LOG_SHARP_OAK_COLLISION_UP[6];
            case DOWN:
                return LOG_SHARP_OAK_COLLISION_DOWN[6];
        }
    }
    
    private static AxisAlignedBB getLayer8(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return LOG_SHARP_OAK_COLLISION_NORTH[7];
            case SOUTH:
                return LOG_SHARP_OAK_COLLISION_SOUTH[7];
            case WEST:
                return LOG_SHARP_OAK_COLLISION_WEST[7];
            case EAST:
                return LOG_SHARP_OAK_COLLISION_EAST[7];
            case UP:
                return LOG_SHARP_OAK_COLLISION_UP[7];
            case DOWN:
                return LOG_SHARP_OAK_COLLISION_DOWN[7];
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
    
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    	entityIn.attackEntityFrom(spikeDamage, 2.0F);
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
       
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        for (EnumFacing enumfacing : EnumFacing.values())
        {
            if (canPlaceBlock(worldIn, pos, enumfacing))
            {
                return true;
            }
        }

        return false;
    }

    protected static boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction)
    {
        return true;
    }

    @Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) 
    {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, facing);
	}
    
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing;

        switch (meta & 7)
        {
            case 0:
                enumfacing = EnumFacing.DOWN;
                break;
            case 1:
                enumfacing = EnumFacing.EAST;
                break;
            case 2:
                enumfacing = EnumFacing.WEST;
                break;
            case 3:
                enumfacing = EnumFacing.SOUTH;
                break;
            case 4:
                enumfacing = EnumFacing.NORTH;
                break;
            case 5:
            default:
                enumfacing = EnumFacing.UP;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {
        int i;

        switch ((EnumFacing)state.getValue(FACING))
        {
            case EAST:
                i = 1;
                break;
            case WEST:
                i = 2;
                break;
            case SOUTH:
                i = 3;
                break;
            case NORTH:
                i = 4;
                break;
            case UP:
            default:
                i = 5;
                break;
            case DOWN:
                i = 0;
        }

        return i;
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
