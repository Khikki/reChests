package com.github.khikki.rechests.chest;

// Minimal contract: any variant chest can report which chest variant it represents.
public interface IVariantChest {
    ChestVariant getChestVariant();
}

