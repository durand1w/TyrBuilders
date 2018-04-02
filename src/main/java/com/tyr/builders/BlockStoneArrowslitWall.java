package com.tyr.builders;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStoneArrowslitWall extends Block
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    protected static final AxisAlignedBB ARROWSLIT_WALL_EAST_AABB = new AxisAlignedBB(0.3125F, 0.0F, 0.0F, 1.0F, 1.0F, 0.3125F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_EAST_AABB2 = new AxisAlignedBB(0.3125F, 0.0F, 0.6875F, 1.0F, 1.0F, 1.0F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_EAST_AABBcombined = new AxisAlignedBB(0.3125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_WEST_AABB = new AxisAlignedBB(0.0F , 0.0F, 0.6875F, 0.6875F, 1.0F, 1.0F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_WEST_AABB2 = new AxisAlignedBB(0.0F , 0.0F, 0.0F, 0.6875F, 1.0F, 0.3125F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_WEST_AABBcombined = new AxisAlignedBB(0.0F , 0.0F, 0.0F, 0.6875F, 1.0F, 1.0F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_SOUTH_AABB = new AxisAlignedBB(0.6875F, 0.0F, 0.3125F, 1.0F, 1.0F, 1.0F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_SOUTH_AABB2 = new AxisAlignedBB(0.0F, 0.0F, 0.3125F, 0.3125F, 1.0F, 1.0F); 
    protected static final AxisAlignedBB ARROWSLIT_WALL_SOUTH_AABBcombined = new AxisAlignedBB(0.0F, 0.0F, 0.3125F, 1.0F, 1.0F, 1.0F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_NORTH_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.3125F, 1.0F, 0.6875F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_NORTH_AABB2 = new AxisAlignedBB(0.6875F, 0.0F, 0.0F, 1.0F, 1.0F, 0.6875F);
    protected static final AxisAlignedBB ARROWSLIT_WALL_NORTH_AABBcombined = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.6875F);
    
    public BlockStoneArrowslitWall(String blockName)
    {
		super (Material.ROCK);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setHardness(1.5F);
		this.setResistance(30F);
		setSoundType(SoundType.STONE);
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb;

            switch ((EnumFacing)state.getValue(FACING))
            {
                case NORTH:
                default:
                    axisalignedbb = ARROWSLIT_WALL_NORTH_AABBcombined;
                    break;
                case SOUTH:
                    axisalignedbb = ARROWSLIT_WALL_SOUTH_AABBcombined;
                    break;
                case WEST:
                    axisalignedbb = ARROWSLIT_WALL_WEST_AABBcombined;
                    break;
                case EAST:
                    axisalignedbb = ARROWSLIT_WALL_EAST_AABBcombined;
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
        list.add(getLeftBlock(bstate));
        list.add(getRightBlock(bstate));
        return list;
    }

    private static AxisAlignedBB getLeftBlock(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return ARROWSLIT_WALL_NORTH_AABB;
            case SOUTH:
                return ARROWSLIT_WALL_SOUTH_AABB;
            case WEST:
                return ARROWSLIT_WALL_WEST_AABB;
            case EAST:
                return ARROWSLIT_WALL_EAST_AABB;
        }
    }

    private static AxisAlignedBB getRightBlock(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return ARROWSLIT_WALL_NORTH_AABB2;
            case SOUTH:
                return ARROWSLIT_WALL_SOUTH_AABB2;
            case WEST:
                return ARROWSLIT_WALL_WEST_AABB2;
            case EAST:
                return ARROWSLIT_WALL_EAST_AABB2;
        }
    }
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
       
    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	return false;
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
    
    public IBlockState getStateFromMeta(int meta)
    {
    	EnumFacing facing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(FACING, facing);
    }
    
    public int getMetaFromState(IBlockState state)
    {
    	EnumFacing facing = (EnumFacing)state.getValue(FACING);
    	int facingbits = facing.getHorizontalIndex();
    	return facingbits;
    }
    
    @Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) 
    {
    	EnumFacing enumfacing = (placer == null) ? EnumFacing.NORTH : EnumFacing.fromAngle(placer.rotationYaw);
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, enumfacing);
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