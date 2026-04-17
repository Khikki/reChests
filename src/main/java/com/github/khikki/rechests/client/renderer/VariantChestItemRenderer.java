package com.github.khikki.rechests.client.renderer;

import com.github.khikki.rechests.ReChests;
import com.github.khikki.rechests.chest.ChestVariant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

// The chest item uses a dedicated renderer so it can appear as a chest model
// instead of a flat inventory sprite.
public class VariantChestItemRenderer implements IItemRenderer {
    private final ModelChest chestModel = new ModelChest();
    private final ResourceLocation texture;

    public VariantChestItemRenderer(ChestVariant variant) {
        this.texture = new ResourceLocation(ReChests.MODID, variant.getSingleTexturePath());
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

        // Forge uses different coordinate spaces for each item render mode,
        // so the model needs a small manual offset per mode.
        if (type == ItemRenderType.INVENTORY) {
            // Inventory rendering uses GUI slot coordinates.
            GL11.glTranslatef(0.0F, 0.0F, 0.0F);
        } else if (type == ItemRenderType.ENTITY) {
            // Entity rendering is used for dropped items in the world.
            GL11.glTranslatef(0.0F, 0.0F, 0.0F);
        } else {
            // Hand-held rendering expects the model to be centered around the player's hand.
            GL11.glTranslatef(0.0F, 0.5F, 0.7F);
        }

        // Chest models use the same flipped axes as the world renderer.
        GL11.glScalef(1.0F, -1.0F, -1.0F);

        // Move the model back to its local center after the axis flip.
        GL11.glTranslatef(0.5F, -0.5F, -0.5F);

        // Rotate the front face so the item preview points to the desired side.
        GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);

        // Item rendering always uses a closed lid.
        chestModel.chestLid.rotateAngleX = 0.0F;
        chestModel.renderAll();
        GL11.glPopMatrix();
    }
}

