package com.github.khikki.rechests.block;

import com.github.khikki.rechests.bookshelf.BookshelfVariant;
import net.minecraft.block.BlockBookshelf;

public class VariantBookshelfBlock extends BlockBookshelf {
    private final BookshelfVariant variant;

    public VariantBookshelfBlock(BookshelfVariant variant) {
        this.variant = variant;
        setBlockName(variant.getBlockName());
        setHardness(1.5F);
        setStepSound(soundTypeWood);
    }
    public BookshelfVariant getBookshelfVariant() {
        return variant;
    }
}
