package com.github.khikki.rechests.bookshelf;

public enum BookshelfVariant {
    OAK("oak", "oakBookshelf"),
    SPRUCE("spruce", "spruceBookshelf"),
    BIRCH("birch", "brichBookshelf"),
    JUNGLE("jungle", "jungleBookshelf"),
    ACACIA("acacia", "acaciaBookshelf"),
    DarkOAK("dark_oak", "darkOakBookshelf");

    private final String id;
    private final String blockName;

    private BookshelfVariant(String id, String blockName) {
        this.id = id;
        this.blockName = blockName;
    }

    public String getId() {
        return id;
    }

    public String getBlockName() {
        return blockName;
    }
}
