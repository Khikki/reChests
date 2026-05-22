package com.github.khikki.rechests.bookshelf;

public enum BookshelfVariant {
    OAK("oak", "oakBookshelf", "tile.oakBookshelf.name"),
    SPRUCE("spruce", "spruceBookshelf", "tile.spruceBookshelf.name"),
    BIRCH("birch", "birchBookshelf", "tile.birchBookshelf.name"),
    JUNGLE("jungle", "jungleBookshelf", "tile.jungleBookshelf.name"),
    ACACIA("acacia", "acaciaBookshelf", "tile.acaciaBookshelf.name"),
    DARK_OAK("dark_oak", "darkOakBookshelf", "tile.darkOakBookshelf.name");

    private final String id;
    private final String blockName;
    private final String inventoryName;

    private BookshelfVariant(String id, String blockName, String inventoryName) {
        this.id = id;
        this.blockName = blockName;
        this.inventoryName = inventoryName;
    }

    public String getId() {
        return id;
    }

    public String getBlockName() {
        return blockName;
    }
    public String getInventoryName() {
        return inventoryName;
    }
}
