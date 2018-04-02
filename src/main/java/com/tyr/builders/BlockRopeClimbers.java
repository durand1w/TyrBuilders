package com.tyr.builders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRopeClimbers extends BlockDirectional
{
	protected static final AxisAlignedBB ROPE_CLIMBERS_SOUTH_AABB = new AxisAlignedBB(0.46875F, 0.0F, 0.0F, 0.53125F, 1.0F, 0.0625F);
	protected static final AxisAlignedBB ROPE_CLIMBERS_WEST_AABB = new AxisAlignedBB(0.9375F, 0.0F, 0.46875F, 1.0F, 1.0F, 0.53125F);
	protected static final AxisAlignedBB ROPE_CLIMBERS_NORTH_AABB = new AxisAlignedBB(0.46875F, 0.0F, 1.0F, 0.53125F, 1.0F, 0.9375F);
	protected static final AxisAlignedBB ROPE_CLIMBERS_EAST_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.46875F, 0.0625F, 1.0F, 0.53125F);
	protected static final AxisAlignedBB ROPE_CLIMBERS_CENTER_AABB = new AxisAlignedBB(0.46875F, 0.0F, 0.46875F, 0.53125F, 1.0F, 0.53125F);
	
	public BlockRopeClimbers(String blockName)
	{
		super(Material.CLOTH);
		setBlockName(this, blockName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		setHardness(0.8F);
		setResistance(4.0F);
		setSoundType(SoundType.CLOTH);
		
	}
	
    @Override public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) { return true; }
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb;

            switch ((EnumFacing)state.getValue(FACING))
            {
                case NORTH:
                    axisalignedbb = ROPE_CLIMBERS_NORTH_AABB;
                    break;
                case SOUTH:
                    axisalignedbb = ROPE_CLIMBERS_SOUTH_AABB;
                    break;
                case WEST:
                    axisalignedbb = ROPE_CLIMBERS_WEST_AABB;
                    break;
                case EAST:
                    axisalignedbb = ROPE_CLIMBERS_EAST_AABB;
                    break;
                case DOWN:
                    axisalignedbb = ROPE_CLIMBERS_CENTER_AABB;
                    break;
                case UP:
                default:
                    axisalignedbb = ROPE_CLIMBERS_CENTER_AABB;

            }
        return axisalignedbb;
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
        
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
    {
        return canPlaceBlock(worldIn, pos, side.getOpposite());
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
       
    protected static boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction)
    {
    	IBlockState state = worldIn.getBlockState(pos.up());
        Block block = state.getBlock();
        if (block == TyrBuildersBlocks.rope_climbers || state.isSideSolid(worldIn, pos, direction) )
        {
        	return true;
        }
        else
        	return false;
    }


    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return canPlaceBlock(worldIn, pos, facing.getOpposite()) ? this.getDefaultState().withProperty(FACING, facing) : this.getDefaultState().withProperty(FACING, EnumFacing.DOWN);
    }
    
    @Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) 
    {
    	IBlockState state = world.getBlockState(pos.up());
        Block block = state.getBlock();
        if (block == TyrBuildersBlocks.rope_climbers)
        {
        	return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, (EnumFacing)state.getValue(FACING));
        }
        else
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
        	case DOWN:
        		i = 0;
        		break;
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
