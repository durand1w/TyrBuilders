package com.tyr.builders;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
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

public class BlockFountainWallStone extends Block
{
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 1);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	//TOP
	protected static final AxisAlignedBB FOUNTAIN_TOP_AABB = new AxisAlignedBB(0.1875F, 0.6875F, 0.0625F, 0.8125, 0.875F, 0.5625F);
	//WALL
	protected static final AxisAlignedBB FOUNTAIN_WALL_AABB = new AxisAlignedBB(0.0625F, 0.0F, 0.0F, 0.9375F, 1.0F, 0.0625F);
	//BOWL
	protected static final AxisAlignedBB FOUNTAIN_BOWL_AABB = new AxisAlignedBB(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.3125F, 0.9375F);
	//BOUNDING
	protected static final AxisAlignedBB FOUNTAIN_BOUNDING_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	
	public BlockFountainWallStone(String blockName)
	{
		super(Material.ROCK);
		setBlockName(this, blockName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEVEL, Integer.valueOf(0)));
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
		this.setHardness(1.5F);
		this.setResistance(30F);
		setSoundType(SoundType.STONE);
		this.setTickRandomly(true);
	}
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb;

            switch ((EnumFacing)state.getValue(FACING))
            {
                case NORTH:
                default:
                    axisalignedbb = FOUNTAIN_BOUNDING_AABB;
                    break;
                case SOUTH:
                    axisalignedbb = FOUNTAIN_BOUNDING_AABB;
                    break;
                case WEST:
                    axisalignedbb = FOUNTAIN_BOUNDING_AABB;
                    break;
                case EAST:
                    axisalignedbb = FOUNTAIN_BOUNDING_AABB;
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
        list.add(getBowl(bstate));
        list.add(getWall(bstate));
        list.add(getTop(bstate));
        return list;
    }
    
    private static AxisAlignedBB getBowl(IBlockState bstate)
    {
    	return FOUNTAIN_BOWL_AABB;
    }
    
    private static AxisAlignedBB getWall(IBlockState bstate)
    {
    	return FOUNTAIN_WALL_AABB;
    }
    
    private static AxisAlignedBB getTop(IBlockState bstate)
    {
    	return FOUNTAIN_TOP_AABB;
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
    
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        EnumFacing enumfacing1 = placer.getHorizontalFacing().rotateY();
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, enumfacing1).withProperty(LEVEL, 0);
        
    }

    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        int i = ((Integer)state.getValue(LEVEL)).intValue();
        //float f = (float)pos.getY() + (6.0F + (float)(3 * i)) / 16.0F;
        float f = (float)pos.getY() + (5.0F / 16.0F);

        if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getEntityBoundingBox().minY <= (double)f)
        {
            entityIn.extinguish();
            this.setWaterLevel(worldIn, pos, state, i - 1);
        }
    }
    
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	//1.12
        ItemStack itemstack = playerIn.getHeldItem(hand);
        if (itemstack == null)
        {
            return true;
        }
        else
        {
            int i = ((Integer)state.getValue(LEVEL)).intValue();
            Item item = itemstack.getItem();

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
            else if (item == Items.GLASS_BOTTLE)
            {
                if (i > 0 && !worldIn.isRemote)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        ItemStack itemstack1 = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
                        //1.12
                        itemstack.shrink(1);
                        if (itemstack.isEmpty())
                        	//if (--heldItem.stackSize == 0)
                        {
                            playerIn.setHeldItem(hand, itemstack1);
                        }
                        else if (!playerIn.inventory.addItemStackToInventory(itemstack1))
                        {
                            playerIn.dropItem(itemstack1, false);
                        }
                        else if (playerIn instanceof EntityPlayerMP)
                        {
                            ((EntityPlayerMP)playerIn).sendContainerToPlayer(playerIn.inventoryContainer);
                        }
                    }

                    //this.setWaterLevel(worldIn, pos, state, i - 1);
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

    public void fillWithRain(World worldIn, BlockPos pos)
    {
        if (worldIn.rand.nextInt(15) == 1)
        {
            float f = worldIn.getBiome(pos).getFloatTemperature(pos);

            if (worldIn.getBiomeProvider().getTemperatureAtHeight(f, pos.getY()) >= 0.15F)
            {
                IBlockState iblockstate = worldIn.getBlockState(pos);

                if (((Integer)iblockstate.getValue(LEVEL)).intValue() < 1)
                {
                    worldIn.setBlockState(pos, iblockstate.cycleProperty(LEVEL), 1);
                }
            }
        }
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
    
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        double d0 = (double)pos.getX();
        double d1 = (double)pos.getY();
        double d2 = (double)pos.getZ();
        if (rand.nextInt(64) == 0)
        {
            worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
        }
    } 

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 3)).withProperty(LEVEL, Integer.valueOf((meta & 15) >> 2));
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
        return new BlockStateContainer(this, new IProperty[] {FACING, LEVEL});
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
