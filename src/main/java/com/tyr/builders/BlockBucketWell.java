package com.tyr.builders;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBucketWell extends BlockHorizontal
{
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 1);
    public static final PropertyBool UP = PropertyBool.create("up");
	//TOP
	protected static final AxisAlignedBB BUCKET_TOP_AABB = new AxisAlignedBB(0.125F, 0.375F, 0.125F, 0.875F, 0.625F, 0.875F);
	//BASE
	protected static final AxisAlignedBB BUCKET_BASE_AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 0.1875F, 0.75F);
	//MIDDLE
	protected static final AxisAlignedBB BUCKET_MIDDLE_AABB = new AxisAlignedBB(0.1875F, 0.1875F, 0.1875F, 0.8125F, 0.375F, 0.8125F);
	//LEFT HANDLE
	protected static final AxisAlignedBB BUCKET_HANDLE_LEFT1_AABB = new AxisAlignedBB(0.125F, 0.6875F, 0.375F, 0.1875F, 0.9375F, 0.625F);
	protected static final AxisAlignedBB BUCKET_HANDLE_LEFT2_AABB = new AxisAlignedBB(0.375F, 0.6875F, 0.125F, 0.625F, 0.9375F, 0.1875F);
	//RIGHT HANDLE
	protected static final AxisAlignedBB BUCKET_HANDLE_RIGHT1_AABB = new AxisAlignedBB(0.8125F, 0.6875F, 0.375F, 0.875F, 0.9375F, 0.625F);
	protected static final AxisAlignedBB BUCKET_HANDLE_RIGHT2_AABB = new AxisAlignedBB(0.375F, 0.6875F, 0.8125F, 0.625F, 0.9375F, 0.875F);
	//BAR
	protected static final AxisAlignedBB BUCKET_BAR1_AABB = new AxisAlignedBB(0.1875F, 0.875F, 0.46875F, 0.8125F, 0.9375F, 0.53125F);
	protected static final AxisAlignedBB BUCKET_BAR2_AABB = new AxisAlignedBB(0.46875F, 0.875F, 0.1875F, 0.53125F, 0.9375F, 0.8125F);
	//BOUNDING
	protected static final AxisAlignedBB BUCKET_BOUNDING_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.125F, 0.875F, 1.0F, 0.875F);
	
	public BlockBucketWell(String blockName)
	{
		super(Material.WOOD);
		setBlockName(this, blockName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(0)).withProperty(UP, Boolean.valueOf(false)));
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		this.setHardness(1.5F);
		this.setResistance(30F);
		setSoundType(SoundType.WOOD);
		this.setTickRandomly(true);
	}
		
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb;

            switch ((EnumFacing)state.getValue(FACING))
            {
                case NORTH:
                default:
                    axisalignedbb = BUCKET_BOUNDING_AABB;
                    break;
                case SOUTH:
                    axisalignedbb = BUCKET_BOUNDING_AABB;
                    break;
                case WEST:
                    axisalignedbb = BUCKET_BOUNDING_AABB;
                    break;
                case EAST:
                    axisalignedbb = BUCKET_BOUNDING_AABB;
            }
        return axisalignedbb;
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        return state.withProperty(UP, Boolean.valueOf(this.isHanging(worldIn, pos, state))).withProperty(LEVEL, Integer.valueOf(state.getValue(LEVEL)));
    }
    
    public boolean isHanging(IBlockAccess worldIn, BlockPos pos, IBlockState state)
    {
        return worldIn.getBlockState(pos.up()).getBlock() == TyrBuildersBlocks.rope_climbers;
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return this.canBePlacedOn(worldIn, pos);
    }
    
    private boolean canBePlacedOn(World worldIn, BlockPos pos)
    {
    	return worldIn.getBlockState(pos.down()).isFullBlock() || (worldIn.getBlockState(pos.up()).getBlock() == TyrBuildersBlocks.rope_climbers);
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
        list.add(getBase(bstate));
        list.add(getMiddle(bstate));
        list.add(getTop(bstate));
        list.add(getLeftHandle(bstate));
        list.add(getRightHandle(bstate));
        list.add(getBar(bstate));
        return list;
    }
    
    private static AxisAlignedBB getBase(IBlockState bstate)
    {
    	return BUCKET_BASE_AABB;
    }
    
    private static AxisAlignedBB getMiddle(IBlockState bstate)
    {
    	return BUCKET_MIDDLE_AABB;
    }
    
    private static AxisAlignedBB getTop(IBlockState bstate)
    {
    	return BUCKET_TOP_AABB;
    }
    
    private static AxisAlignedBB getLeftHandle(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return BUCKET_HANDLE_LEFT1_AABB;
            case SOUTH:
                return BUCKET_HANDLE_LEFT1_AABB;
            case WEST:
                return BUCKET_HANDLE_LEFT2_AABB;
            case EAST:
                return BUCKET_HANDLE_LEFT2_AABB;
        }
    }
    
    private static AxisAlignedBB getRightHandle(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return BUCKET_HANDLE_RIGHT1_AABB;
            case SOUTH:
                return BUCKET_HANDLE_RIGHT1_AABB;
            case WEST:
                return BUCKET_HANDLE_RIGHT2_AABB;
            case EAST:
                return BUCKET_HANDLE_RIGHT2_AABB;
        }
    }
    
    private static AxisAlignedBB getBar(IBlockState bstate)
    {
        switch ((EnumFacing)bstate.getValue(FACING))
        {
            case NORTH:
            default:
                return BUCKET_BAR1_AABB;
            case SOUTH:
                return BUCKET_BAR1_AABB;
            case WEST:
                return BUCKET_BAR2_AABB;
            case EAST:
                return BUCKET_BAR2_AABB;
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

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(LEVEL, Integer.valueOf(0)).withProperty(UP, Boolean.valueOf(false));
    }
    
    @Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) 
    {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, placer.getHorizontalFacing()).withProperty(LEVEL, Integer.valueOf(0)).withProperty(UP, Boolean.valueOf(false));
	}

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        int i = ((Integer)state.getValue(LEVEL)).intValue();
        //float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;
        float f = (float)pos.getY() + (9.0F / 16.0F);

        if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getEntityBoundingBox().minY <= (double)f)
        {
            entityIn.extinguish();
            this.setWaterLevel(worldIn, pos, state, i - 1);
        }
    }
	  
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	//1.12
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (heldItem == null)
        {
            return true;
        }
        else
        {
            int i = ((Integer)state.getValue(LEVEL)).intValue();
            Item item = heldItem.getItem();

            if (item == Items.WATER_BUCKET)
            {
                if (i < 1 && !worldIn.isRemote)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }
                    this.setWaterLevel(worldIn, pos, state, 1);
                }

                return true;
            }
            else if (item == Items.BUCKET)
            {
                if (i == 1 && !worldIn.isRemote)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                    	//1.12
                        itemstack.shrink(1);
                        if (itemstack.isEmpty())
                        //--heldItem.stackSize;
                        //if (heldItem.stackSize == 0)
                        {
                            playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                        }
                        else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET)))
                        {
                            playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                        }
                    }
                    this.setWaterLevel(worldIn, pos, state, 0);
                }
                return true;
            }
            else if (item == Item.getItemFromBlock(Blocks.SPONGE))
            {
                if (i >= 1 && !worldIn.isRemote)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        playerIn.setHeldItem(hand, new ItemStack(Blocks.SPONGE, 1, 1));
                    }
                    this.setWaterLevel(worldIn, pos, state, 0);
                }

                return true;
            }
        }
        
		return false;
    }
    
    public void setWaterLevel(World worldIn, BlockPos pos, IBlockState state, int level)
    {
        worldIn.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 1))), 2);
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (!this.canBePlacedOn(worldIn, pos))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
    
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (this.hasWater(worldIn, pos) && (this.isHanging(worldIn, pos, state)))
        {
        	this.setWaterLevel(worldIn, pos, state, 1);
        }
    }
    
    private boolean hasWater(World worldIn, BlockPos pos)
    {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, -8, 1)))
        {
            if (worldIn.getBlockState(blockpos$mutableblockpos).getMaterial() == Material.WATER)
            {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(state.getBlock());
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(state.getBlock());
    }

    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(LEVEL, Integer.valueOf((meta & 15) >> 2));//.withProperty(UP, Boolean.valueOf((meta & 8) != 0));
    }

    public int getMetaFromState(IBlockState state)
    {
        int i = 0;
        i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();
        i = i | ((Integer)state.getValue(LEVEL)).intValue() << 2;
        return i;
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, LEVEL, UP});
    }
    	   
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
}
