package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockAccess
{
    @Nullable
    TileEntity getTileEntity(BlockPos pos);

    @SideOnly(Side.CLIENT)
    int getCombinedLight(BlockPos pos, int lightValue);

    IBlockState getBlockState(BlockPos pos);

    boolean isAirBlock(BlockPos pos);

    @SideOnly(Side.CLIENT)
    Biome getBiome(BlockPos pos);

    int getStrongPower(BlockPos pos, EnumFacing direction);

    @SideOnly(Side.CLIENT)
    WorldType getWorldType();
}