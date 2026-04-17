package com.github.khikki.rechests.chest;

// This enum keeps the data that used to be spread across separate chest classes:
// id, block name, localization key, inventory icon, and chest texture paths.
public enum ChestVariant {
    OAK("oak", "oakChest", "tile.oakChest.name", "planks_oak", "textures/entity/chest/moakchest.png", "textures/entity/chest/moakchest_double.png", "oakChestMinecart", 0),
    SPRUCE("spruce", "spruceChest", "tile.spruceChest.name", "planks_spruce", "textures/entity/chest/msprucechest.png", "textures/entity/chest/msprucechest_double.png", "spruceChestMinecart", 0),
    BIRCH("birch", "birchChest", "tile.birchChest.name", "planks_birch", "textures/entity/chest/mbirchchest.png", "textures/entity/chest/mbirchchest_double.png", "birchChestMinecart", 0),
    JUNGLE("jungle", "jungleChest", "tile.jungleChest.name", "planks_jungle", "textures/entity/chest/mjunglechest.png", "textures/entity/chest/mjunglechest_double.png", "jungleChestMinecart", 0),
    ACACIA("acacia", "acaciaChest", "tile.acaciaChest.name", "planks_acacia", "textures/entity/chest/macaciachest.png", "textures/entity/chest/macaciachest_double.png", "acaciaChestMinecart", 0),
    DARK_OAK("dark_oak", "darkOakChest", "tile.darkOakChest.name", "planks_dark_oak", "textures/entity/chest/mdarkoakchest.png", "textures/entity/chest/mdarkoakchest_double.png", "darkOakChestMinecart", 0),
    TRAPPED_OAK("trapped_oak", "trappedOakChest", "tile.trappedOakChest.name", "planks_oak", "textures/entity/chest/moaktrappedchest.png", "textures/entity/chest/moaktrappedchest_double.png", null, 1),
    TRAPPED_SPRUCE("trapped_spruce", "trappedSpruceChest", "tile.trappedSpruceChest.name", "planks_spruce", "textures/entity/chest/msprucetrappedchest.png", "textures/entity/chest/msprucetrappedchest_double.png", null, 1),
    TRAPPED_BIRCH("trapped_birch", "trappedBirchChest", "tile.trappedBirchChest.name", "planks_birch", "textures/entity/chest/mbirchtrappedchest.png", "textures/entity/chest/mbirchtrappedchest_double.png", null, 1),
    TRAPPED_JUNGLE("trapped_jungle", "trappedJungleChest", "tile.trappedJungleChest.name", "planks_jungle", "textures/entity/chest/mjungletrappedchest.png", "textures/entity/chest/mjungletrappedchest_double.png", null, 1),
    TRAPPED_ACACIA("trapped_acacia", "trappedAcaciaChest", "tile.trappedAcaciaChest.name", "planks_acacia", "textures/entity/chest/macaciatrappedchest.png", "textures/entity/chest/macaciatrappedchest_double.png", null, 1),
    TRAPPED_DARK_OAK("trapped_dark_oak", "trappedDarkOakChest", "tile.trappedDarkOakChest.name", "planks_dark_oak", "textures/entity/chest/mdarkoaktrappedchest.png", "textures/entity/chest/mdarkoaktrappedchest_double.png", null, 1);

    private final String id;
    private final String blockName;
    private final String inventoryName;
    private final String iconName;
    private final String singleTexturePath;
    private final String doubleTexturePath;
    private final String minecartItemName;
    private final int chestType;

    private ChestVariant(String id, String blockName, String inventoryName, String iconName, String singleTexturePath, String doubleTexturePath, String minecartItemName, int chestType) {
        this.id = id;
        this.blockName = blockName;
        this.inventoryName = inventoryName;
        this.iconName = iconName;
        this.singleTexturePath = singleTexturePath;
        this.doubleTexturePath = doubleTexturePath;
        this.minecartItemName = minecartItemName;
        this.chestType = chestType;
    }

    public String getId() {
        return id;
    }

    // Used by setBlockName(...) so Forge registers the block under a stable name.
    public String getBlockName() {
        return blockName;
    }

    // Localization key used by the chest GUI.
    public String getInventoryName() {
        return inventoryName;
    }

    // Inventory/block-atlas icon, even though the chest itself is rendered with a TESR.
    public String getIconName() {
        return iconName;
    }

    // Texture path for the single chest model in textures/entity/chest.
    public String getSingleTexturePath() {
        return singleTexturePath;
    }

    // Texture path for the double chest model. Null would fall back to vanilla.
    public String getDoubleTexturePath() {
        return doubleTexturePath;
    }

    public String getMinecartItemName() {
        return minecartItemName;
    }

    public int getChestType() {
        return chestType;
    }

    public boolean isTrapped() {
        return chestType == 1;
    }

    public boolean supportsMinecart() {
        return minecartItemName != null;
    }
}

