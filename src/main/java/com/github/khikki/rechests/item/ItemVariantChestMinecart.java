package com.github.khikki.rechests.item;

import com.github.khikki.rechests.chest.ChestVariant;
import com.github.khikki.rechests.entity.EntityVariantChestMinecart;
import net.minecraft.block.BlockRailBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemVariantChestMinecart extends Item {
    private final ChestVariant variant;

    public ItemVariantChestMinecart(ChestVariant variant) {
        this.variant = variant;
        setUnlocalizedName(variant.getMinecartItemName());
        setTextureName("minecraft:minecart_chest");
        setCreativeTab(CreativeTabs.tabTransport);
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!BlockRailBase.func_150051_a(world.getBlock(x, y, z))) {
            return false;
        }

        if (!world.isRemote) {
            EntityVariantChestMinecart minecart = new EntityVariantChestMinecart(world, x + 0.5D, y + 0.5D, z + 0.5D, variant);
            world.spawnEntityInWorld(minecart);
        }

        stack.stackSize--;
        return true;
    }
}

