package com.github.khikki.rechests.client.renderer;

import com.github.khikki.rechests.ReChests;
import com.github.khikki.rechests.chest.ChestVariant;
import com.github.khikki.rechests.tile.VariantChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

// One TESR handles every chest variant.
// Single vs double rendering is resolved directly from the world state so the renderer
// does not depend on TileEntityChest's cached adjacent references.
public class VariantChestRenderer extends TileEntitySpecialRenderer {
    private static final ResourceLocation VANILLA_SINGLE =
        new ResourceLocation("textures/entity/chest/normal.png");
    private static final ResourceLocation VANILLA_DOUBLE =
        new ResourceLocation("textures/entity/chest/normal_double.png");

    private final ModelChest singleChest = new ModelChest();
    private final ModelChest doubleChest = new ModelLargeChest();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        renderChest((TileEntityChest) tileEntity, x, y, z, partialTicks);
    }

    public void renderChest(TileEntityChest chest, double x, double y, double z, float partialTicks) {
        int meta = chest.hasWorldObj() ? chest.getBlockMetadata() : 0;
        TileEntityChest adjacentXNeg = getAdjacentChest(chest, -1, 0);
        TileEntityChest adjacentXPos = getAdjacentChest(chest, 1, 0);
        TileEntityChest adjacentZNeg = getAdjacentChest(chest, 0, -1);
        TileEntityChest adjacentZPos = getAdjacentChest(chest, 0, 1);

        // Only render the large chest from the primary half.
        if (adjacentZNeg != null || adjacentXNeg != null) {
            return;
        }

        boolean isDouble = adjacentXPos != null || adjacentZPos != null;
        ModelChest model = isDouble ? doubleChest : singleChest;

        bindTexture(resolveTexture(chest, isDouble));

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

        short rotation = 0;
        if (meta == 2) rotation = 180;
        if (meta == 3) rotation = 0;
        if (meta == 4) rotation = 90;
        if (meta == 5) rotation = -90;

        if (meta == 2 && adjacentXPos != null) {
            GL11.glTranslatef(1.0F, 0.0F, 0.0F);
        }

        if (meta == 5 && adjacentZPos != null) {
            GL11.glTranslatef(0.0F, 0.0F, -1.0F);
        }

        GL11.glRotatef(rotation, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        float lid = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * partialTicks;

        if (adjacentZNeg != null) {
            float other = adjacentZNeg.prevLidAngle + (adjacentZNeg.lidAngle - adjacentZNeg.prevLidAngle) * partialTicks;
            if (other > lid) lid = other;
        }

        if (adjacentXNeg != null) {
            float other = adjacentXNeg.prevLidAngle + (adjacentXNeg.lidAngle - adjacentXNeg.prevLidAngle) * partialTicks;
            if (other > lid) lid = other;
        }

        lid = 1.0F - lid;
        lid = 1.0F - lid * lid * lid;
        model.chestLid.rotateAngleX = -(lid * (float) Math.PI / 2.0F);
        model.renderAll();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }

    private TileEntityChest getAdjacentChest(TileEntityChest chest, int dx, int dz) {
        if (!chest.hasWorldObj()) {
            return null;
        }

        int x = chest.xCoord + dx;
        int y = chest.yCoord;
        int z = chest.zCoord + dz;

        Block ownBlock = chest.getBlockType();
        Block otherBlock = chest.getWorldObj().getBlock(x, y, z);
        if (ownBlock == null || ownBlock != otherBlock) {
            return null;
        }

        TileEntity tileEntity = chest.getWorldObj().getTileEntity(x, y, z);
        return tileEntity instanceof TileEntityChest ? (TileEntityChest) tileEntity : null;
    }

    private ResourceLocation resolveTexture(TileEntityChest chest, boolean isDouble) {
        if (chest instanceof VariantChestTileEntity) {
            ChestVariant variant = ((VariantChestTileEntity) chest).getChestVariant();
            if (variant != null) {
                if (isDouble && variant.getDoubleTexturePath() != null) {
                    return new ResourceLocation(ReChests.MODID, variant.getDoubleTexturePath());
                }
                if (!isDouble) {
                    return new ResourceLocation(ReChests.MODID, variant.getSingleTexturePath());
                }
            }
        }

        return isDouble ? VANILLA_DOUBLE : VANILLA_SINGLE;
    }
}

