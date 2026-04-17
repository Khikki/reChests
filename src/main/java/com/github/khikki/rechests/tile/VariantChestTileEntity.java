package com.github.khikki.rechests.tile;

import com.github.khikki.rechests.chest.ChestVariant;
import com.github.khikki.rechests.chest.IVariantChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;

// One TileEntity implementation serves every chest variant.
// Inventory contents, lid animation, and double-chest behavior still come from TileEntityChest.
public class VariantChestTileEntity extends TileEntityChest {
    @Override
    public void checkForAdjacentChests() {
        if (!this.adjacentChestChecked) {
            this.adjacentChestChecked = true;
            this.adjacentChestZNeg = null;
            this.adjacentChestXPos = null;
            this.adjacentChestXNeg = null;
            this.adjacentChestZPos = null;

            if (isSameChestBlock(this.xCoord - 1, this.yCoord, this.zCoord)) {
                this.adjacentChestXNeg = (TileEntityChest) this.worldObj.getTileEntity(this.xCoord - 1, this.yCoord, this.zCoord);
            }
            if (isSameChestBlock(this.xCoord + 1, this.yCoord, this.zCoord)) {
                this.adjacentChestXPos = (TileEntityChest) this.worldObj.getTileEntity(this.xCoord + 1, this.yCoord, this.zCoord);
            }
            if (isSameChestBlock(this.xCoord, this.yCoord, this.zCoord - 1)) {
                this.adjacentChestZNeg = (TileEntityChest) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord - 1);
            }
            if (isSameChestBlock(this.xCoord, this.yCoord, this.zCoord + 1)) {
                this.adjacentChestZPos = (TileEntityChest) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord + 1);
            }

            if (this.adjacentChestZNeg instanceof VariantChestTileEntity) {
                ((VariantChestTileEntity) this.adjacentChestZNeg).validateAdjacentChest(this, 0);
            }
            if (this.adjacentChestZPos instanceof VariantChestTileEntity) {
                ((VariantChestTileEntity) this.adjacentChestZPos).validateAdjacentChest(this, 2);
            }
            if (this.adjacentChestXPos instanceof VariantChestTileEntity) {
                ((VariantChestTileEntity) this.adjacentChestXPos).validateAdjacentChest(this, 1);
            }
            if (this.adjacentChestXNeg instanceof VariantChestTileEntity) {
                ((VariantChestTileEntity) this.adjacentChestXNeg).validateAdjacentChest(this, 3);
            }
        }
    }

    @Override
    public String getInventoryName() {
        ChestVariant variant = getChestVariant();
        if (variant != null) {
            return variant.getInventoryName();
        }
        return super.getInventoryName();
    }

    public ChestVariant getChestVariant() {
        // The TileEntity does not store a separate variant field.
        // Instead, it reads the current variant from the block placed in the world.
        Block block = getBlockType();
        if (block instanceof IVariantChest) {
            return ((IVariantChest) block).getChestVariant();
        }
        return null;
    }

    private boolean isSameChestBlock(int x, int y, int z) {
        if (this.worldObj == null) {
            return false;
        }

        Block ownBlock = this.getBlockType();
        Block otherBlock = this.worldObj.getBlock(x, y, z);
        return ownBlock != null && ownBlock == otherBlock;
    }

    private void validateAdjacentChest(TileEntityChest otherChest, int direction) {
        if (otherChest.isInvalid()) {
            this.adjacentChestChecked = false;
            return;
        }

        if (!this.adjacentChestChecked) {
            return;
        }

        switch (direction) {
            case 0:
                if (this.adjacentChestZPos != otherChest) {
                    this.adjacentChestChecked = false;
                }
                break;
            case 1:
                if (this.adjacentChestXNeg != otherChest) {
                    this.adjacentChestChecked = false;
                }
                break;
            case 2:
                if (this.adjacentChestZNeg != otherChest) {
                    this.adjacentChestChecked = false;
                }
                break;
            case 3:
                if (this.adjacentChestXPos != otherChest) {
                    this.adjacentChestChecked = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        // TESR chests need a larger render box than a regular 1x1x1 block.
        // Without it, Minecraft culls the model too early near the edge of the camera.
        return AxisAlignedBB.getBoundingBox(
            this.xCoord - 1,
            this.yCoord,
            this.zCoord - 1,
            this.xCoord + 2,
            this.yCoord + 2,
            this.zCoord + 2
        );
    }
}

