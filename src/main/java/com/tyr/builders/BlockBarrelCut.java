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

public class BlockBarrelCut extends Block
{
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 2);
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	//EDGES
	protected static final AxisAlignedBB BARREL_EDGE1_AABB = new AxisAlignedBB(0.125F, 0.0625F, 0.0625F, 0.875F, 0.5625F, 0.125F);
	protected static final AxisAlignedBB BARREL_EDGE2_AABB = new AxisAlignedBB(0.125F, 0.0625F, 0.875F, 0.875F, 0.5625F, 0.9375F);
	protected static final AxisAlignedBB BARREL_EDGE3_AABB = new AxisAlignedBB(0.0625F, 0.0625F, 0.125F, 0.125F, 0.5625F, 0.875F);
	protected static final AxisAlignedBB BARREL_EDGE4_AABB = new AxisAlignedBB(0.875F, 0.0625F, 0.125F, 0.9375F, 0.5625F, 0.875F);
	//BASE
	protected static final AxisAlignedBB BARREL_BASE_AABB = new AxisAlignedBB(0.125F, 0.0F, 0.125F, 0.875F, 0.0625F, 0.875F);
	//BOUNDING
	protected static final AxisAlignedBB BARREL_BOUNDING_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
	
	public BlockBarrelCut(String blockName)
	{
		super(Material.WOOD);
		setBlockName(this, blockName);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEVEL, Integer.valueOf(0)));
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
                    axisalignedbb = BARREL_BOUNDING_AABB;
                    break;
                case SOUTH:
                    axisalignedbb = BARREL_BOUNDING_AABB;
                    break;
                case WEST:
                    axisalignedbb = BARREL_BOUNDING_AABB;
                    break;
                case EAST:
                    axisalignedbb = BARREL_BOUNDING_AABB;
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
        list.add(getBase(bstate));
        list.add(getEdge1(bstate));
        list.add(getEdge2(bstate));
        list.add(getEdge3(bstate));
        list.add(getEdge4(bstate));
        return list;
    }
    
    private static AxisAlignedBB getBase(IBlockState bstate)
    {
    	return BARREL_BASE_AABB;
    }
        
    private static AxisAlignedBB getEdge1(IBlockState bstate)
    {
    	return BARREL_EDGE1_AABB;
    }
    
    private static AxisAlignedBB getEdge2(IBlockState bstate)
    {
    	return BARREL_EDGE2_AABB;
    }
    
    private static AxisAlignedBB getEdge3(IBlockState bstate)
    {
    	return BARREL_EDGE3_AABB;
    }
    
    private static AxisAlignedBB getEdge4(IBlockState bstate)
    {
    	return BARREL_EDGE4_AABB;
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
        float f = (float)pos.getY() + (1.0F + (float)(3 * i)) / 16.0F;

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
                if (i < 2 && !worldIn.isRemote)
                {
                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }
                    this.setWaterLevel(worldIn, pos, state, 2);
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

                    this.setWaterLevel(worldIn, pos, state, i - 1);
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
        worldIn.setBlockState(pos, state.withProperty(LEVEL, Integer.valueOf(MathHelper.clamp(level, 0, 2))), 2);
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
        
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return this.canBePlacedOn(worldIn, pos.down());
    }

    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
    {
        if (!this.canBePlacedOn(worldIn, pos.down()))
        {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean canBePlacedOn(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos).isFullBlock();
    }

    @Nullable
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(TyrBuildersBlocks.barrel_cut_oak); 
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
    {
        return new ItemStack(Item.getItemFromBlock(TyrBuildersBlocks.barrel_cut_oak));
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
