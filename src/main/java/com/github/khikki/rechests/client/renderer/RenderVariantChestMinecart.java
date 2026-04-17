package com.github.khikki.rechests.client.renderer;

import com.github.khikki.rechests.ReChests;
import com.github.khikki.rechests.chest.ChestVariant;
import com.github.khikki.rechests.entity.EntityVariantChestMinecart;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.entity.RenderMinecart;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderVariantChestMinecart extends RenderMinecart {
    private final ModelChest chestModel = new ModelChest();

    @Override
    protected void func_147910_a(EntityMinecart minecart, float partialTicks, Block block, int metadata) {
        if (!(minecart instanceof EntityVariantChestMinecart)) {
            super.func_147910_a(minecart, partialTicks, block, metadata);
            return;
        }

        ChestVariant variant = ((EntityVariantChestMinecart) minecart).getChestVariant();
        ResourceLocation texture = new ResourceLocation(ReChests.MODID, variant.getSingleTexturePath());

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        this.bindTexture(texture);

        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, -0.5F, -0.5F);
        GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);

        chestModel.chestLid.rotateAngleX = 0.0F;
        chestModel.renderAll();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
}

