package com.github.khikki.rechests.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import com.github.khikki.rechests.ReChests;
import com.github.khikki.rechests.chest.ChestVariant;
import com.github.khikki.rechests.client.renderer.RenderVariantChestMinecart;
import com.github.khikki.rechests.client.renderer.VariantChestItemRenderer;
import com.github.khikki.rechests.client.renderer.VariantChestRenderer;
import com.github.khikki.rechests.entity.EntityVariantChestMinecart;
import com.github.khikki.rechests.tile.VariantChestTileEntity;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityVariantChestMinecart.class, new RenderVariantChestMinecart());

        ClientRegistry.bindTileEntitySpecialRenderer(VariantChestTileEntity.class, new VariantChestRenderer());

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.oakChest), new VariantChestItemRenderer(ChestVariant.OAK));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.spruceChest), new VariantChestItemRenderer(ChestVariant.SPRUCE));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.birchChest), new VariantChestItemRenderer(ChestVariant.BIRCH));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.jungleChest), new VariantChestItemRenderer(ChestVariant.JUNGLE));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.acaciaChest), new VariantChestItemRenderer(ChestVariant.ACACIA));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.darkOakChest), new VariantChestItemRenderer(ChestVariant.DARK_OAK));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.trappedOakChest), new VariantChestItemRenderer(ChestVariant.TRAPPED_OAK));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.trappedSpruceChest), new VariantChestItemRenderer(ChestVariant.TRAPPED_SPRUCE));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.trappedBirchChest), new VariantChestItemRenderer(ChestVariant.TRAPPED_BIRCH));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.trappedJungleChest), new VariantChestItemRenderer(ChestVariant.TRAPPED_JUNGLE));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.trappedAcaciaChest), new VariantChestItemRenderer(ChestVariant.TRAPPED_ACACIA));
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ReChests.trappedDarkOakChest), new VariantChestItemRenderer(ChestVariant.TRAPPED_DARK_OAK));
    }
}

