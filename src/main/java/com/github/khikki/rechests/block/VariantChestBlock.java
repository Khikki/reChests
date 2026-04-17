package com.github.khikki.rechests.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.github.khikki.rechests.ReChests;
import com.github.khikki.rechests.chest.ChestVariant;
import com.github.khikki.rechests.chest.IVariantChest;
import com.github.khikki.rechests.tile.VariantChestTileEntity;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import static net.minecraftforge.common.util.ForgeDirection.DOWN;

// A single block class handles every chest variant.
// The actual wood type is selected only through the variant field.
public class VariantChestBlock extends BlockChest implements IVariantChest {
    private final ChestVariant variant;

    public VariantChestBlock(ChestVariant variant) {
        super(variant.getChestType());
        this.variant = variant;
        setBlockName(variant.getBlockName());
        setHardness(2.5F);
        setStepSound(Block.soundTypeWood);
    }

    public ChestVariant getChestVariant() {
        return variant;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        // Every variant uses the same TileEntity implementation.
        // The active chest type is resolved later from the block in the world.
        return new VariantChestTileEntity();
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        int adjacentChests = 0;

        if (isSameChest(world, x - 1, y, z)) adjacentChests++;
        if (isSameChest(world, x + 1, y, z)) adjacentChests++;
        if (isSameChest(world, x, y, z - 1)) adjacentChests++;
        if (isSameChest(world, x, y, z + 1)) adjacentChests++;

        // Never allow a chest to be placed next to two matching chests at once.
        // This is the classic protection against forming an invalid triple chest.
        if (adjacentChests > 1) {
            return false;
        }

        // Also reject placement next to a chest that is already part of a double chest.
        return !hasAdjacentSameChest(world, x - 1, y, z)
            && !hasAdjacentSameChest(world, x + 1, y, z)
            && !hasAdjacentSameChest(world, x, y, z - 1)
            && !hasAdjacentSameChest(world, x, y, z + 1);
    }

    @Override
    public IInventory func_149951_m(World world, int x, int y, int z) {
        Object inventory = (TileEntityChest) world.getTileEntity(x, y, z);

        if (inventory == null) {
            return null;
        } else if (world.isSideSolid(x, y + 1, z, DOWN)) {
            return null;
        } else if (isBlockedBySittingCat(world, x, y, z)) {
            return null;
        } else if (world.getBlock(x - 1, y, z) == this && (world.isSideSolid(x - 1, y + 1, z, DOWN) || isBlockedBySittingCat(world, x - 1, y, z))) {
            return null;
        } else if (world.getBlock(x + 1, y, z) == this && (world.isSideSolid(x + 1, y + 1, z, DOWN) || isBlockedBySittingCat(world, x + 1, y, z))) {
            return null;
        } else if (world.getBlock(x, y, z - 1) == this && (world.isSideSolid(x, y + 1, z - 1, DOWN) || isBlockedBySittingCat(world, x, y, z - 1))) {
            return null;
        } else if (world.getBlock(x, y, z + 1) == this && (world.isSideSolid(x, y + 1, z + 1, DOWN) || isBlockedBySittingCat(world, x, y, z + 1))) {
            return null;
        }

        String inventoryName = variant.getInventoryName();

        if (world.getBlock(x - 1, y, z) == this) {
            inventory = new InventoryLargeChest(inventoryName, (TileEntityChest) world.getTileEntity(x - 1, y, z), (IInventory) inventory);
        }

        if (world.getBlock(x + 1, y, z) == this) {
            inventory = new InventoryLargeChest(inventoryName, (IInventory) inventory, (TileEntityChest) world.getTileEntity(x + 1, y, z));
        }

        if (world.getBlock(x, y, z - 1) == this) {
            inventory = new InventoryLargeChest(inventoryName, (TileEntityChest) world.getTileEntity(x, y, z - 1), (IInventory) inventory);
        }

        if (world.getBlock(x, y, z + 1) == this) {
            inventory = new InventoryLargeChest(inventoryName, (IInventory) inventory, (TileEntityChest) world.getTileEntity(x, y, z + 1));
        }

        return (IInventory) inventory;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        // This is the regular block/item icon used in the texture atlas,
        // not the chest model texture from textures/entity/chest.
        this.blockIcon = iconRegister.registerIcon(ReChests.MODID + ":" + variant.getIconName());
    }

    private boolean isSameChest(World world, int x, int y, int z) {
        return world.getBlock(x, y, z) == this;
    }

    private boolean hasAdjacentSameChest(World world, int x, int y, int z) {
        if (!isSameChest(world, x, y, z)) {
            return false;
        }

        int adjacent = 0;
        if (isSameChest(world, x - 1, y, z)) adjacent++;
        if (isSameChest(world, x + 1, y, z)) adjacent++;
        if (isSameChest(world, x, y, z - 1)) adjacent++;
        if (isSameChest(world, x, y, z + 1)) adjacent++;
        return adjacent > 0;
    }

    private static boolean isBlockedBySittingCat(World world, int x, int y, int z) {
        Iterator iterator = world.getEntitiesWithinAABB(
            EntityOcelot.class,
            AxisAlignedBB.getBoundingBox(x, y + 1, z, x + 1, y + 2, z + 1)
        ).iterator();

        while (iterator.hasNext()) {
            EntityOcelot ocelot = (EntityOcelot) iterator.next();

            if (ocelot.isSitting()) {
                return true;
            }
        }

        return false;
    }
}

