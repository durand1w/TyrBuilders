package com.tyr.builders;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockIronGrate extends Block 
{
	public static final PropertyEnum HALF = PropertyEnum.create("half", BlockIronGrate.RoadLevel.class);
	private final String name="iron_grate";
    protected static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.0D, 0.875D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0.0D, 0.75D, 0.0D, 1.0D, 0.875D, 1.0D);
	
    public BlockIronGrate(String blockName)
    {
		super (Material.IRON);
		setBlockName(this, blockName);
		setCreativeTab(TyrBuildersItems.tabTyrBuilders);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, BlockIronGrate.RoadLevel.TOP));
		this.setHardness(5F);
		this.setResistance(30F);
		setSoundType(SoundType.METAL);
		setLightOpacity(1);
    }
       
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        AxisAlignedBB axisalignedbb;

        if (state.getValue(HALF) == BlockIronGrate.RoadLevel.TOP)
        {
            axisalignedbb = TOP_AABB;
        }
        else
        {
            axisalignedbb = BOTTOM_AABB;
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
    
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        IBlockState iblockstate = this.getDefaultState();

        if (facing.getAxis().isHorizontal())
        {
            iblockstate = iblockstate.withProperty(HALF, hitY > 0.5F ? BlockIronGrate.RoadLevel.TOP : BlockIronGrate.RoadLevel.BOTTOM);
        }
        return iblockstate;
    }
        
    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
    	return false;
    }
    
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(HALF, (meta & 8) == 0 ? BlockIronGrate.RoadLevel.BOTTOM : BlockIronGrate.RoadLevel.TOP);
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    public int getMetaFromState(IBlockState state)
    {
        byte b0 = 0;
        int i = b0;

        if (state.getValue(HALF) == BlockIronGrate.RoadLevel.TOP)
        {
            i |= 8;
        }
        return i;
    }
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {HALF});
    }
         
    public static enum RoadLevel implements IStringSerializable
    {
        TOP("top"),
        BOTTOM("bottom");
        private final String name;

        private RoadLevel(String name)
        {
            this.name = name;
        }

        public String toString()
        {
            return this.name;
        }

        public String getName()
        {
            return this.name;
        }
    }
           
	public static void setBlockName(Block block, String blockName) 
	{
		block.setRegistryName(TyrBuilders.MODID, blockName);
		block.setUnlocalizedName(block.getRegistryName().toString());
	}
    
}
