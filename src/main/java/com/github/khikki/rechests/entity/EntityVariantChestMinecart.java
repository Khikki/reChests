package com.github.khikki.rechests.entity;

import com.github.khikki.rechests.ReChests;
import com.github.khikki.rechests.chest.ChestVariant;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityMinecartContainer;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityVariantChestMinecart extends EntityMinecartContainer {
    private static final int VARIANT_WATCHER_ID = 23;

    public EntityVariantChestMinecart(World world) {
        super(world);
    }

    public EntityVariantChestMinecart(World world, double x, double y, double z, ChestVariant variant) {
        super(world, x, y, z);
        setChestVariant(variant);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(VARIANT_WATCHER_ID, Integer.valueOf(ChestVariant.OAK.ordinal()));
    }

    public ChestVariant getChestVariant() {
        int ordinal = this.dataWatcher.getWatchableObjectInt(VARIANT_WATCHER_ID);
        ChestVariant[] variants = ChestVariant.values();

        if (ordinal < 0 || ordinal >= variants.length) {
            return ChestVariant.OAK;
        }

        ChestVariant variant = variants[ordinal];
        return variant.supportsMinecart() ? variant : ChestVariant.OAK;
    }

    public void setChestVariant(ChestVariant variant) {
        ChestVariant safeVariant = (variant != null && variant.supportsMinecart()) ? variant : ChestVariant.OAK;
        this.dataWatcher.updateObject(VARIANT_WATCHER_ID, Integer.valueOf(safeVariant.ordinal()));
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        setChestVariant(resolveVariant(tag.getString("ChestVariant")));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setString("ChestVariant", getChestVariant().getId());
    }

    @Override
    public void killMinecart(DamageSource source) {
        super.killMinecart(source);

        if (!this.worldObj.isRemote) {
            Item item = ReChests.getChestMinecartItem(getChestVariant());
            if (item != null) {
                this.func_145778_a(item, 1, 0.0F);
            }
        }
    }

    @Override
    public Block func_145817_o() {
        return ReChests.getChestBlock(getChestVariant());
    }

    @Override
    public int getMinecartType() {
        return 1;
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public int getDefaultDisplayTileOffset() {
        return 8;
    }

    private ChestVariant resolveVariant(String id) {
        for (ChestVariant variant : ChestVariant.values()) {
            if (variant.getId().equals(id) && variant.supportsMinecart()) {
                return variant;
            }
        }

        return ChestVariant.OAK;
    }
}

