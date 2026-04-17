package com.github.khikki.rechests;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import com.github.khikki.rechests.block.VariantChestBlock;
import com.github.khikki.rechests.chest.ChestVariant;
import com.github.khikki.rechests.entity.EntityVariantChestMinecart;
import com.github.khikki.rechests.item.ItemVariantChestMinecart;
import com.github.khikki.rechests.proxy.CommonProxy;
import com.github.khikki.rechests.tile.VariantChestTileEntity;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

// Forge discovers this class through @Mod and uses it as the entry point for the mod lifecycle.
@Mod(modid = ReChests.MODID, name = ReChests.NAME, version = ReChests.VERSION)
public class ReChests {
    // MODID is used in registry names, resource paths, and logging.
    public static final String MODID = "rechests";
    public static final String NAME = "Re:Chests";
    public static final String VERSION = "1.0.1";

    private static final ChestVariant[] NORMAL_VARIANTS = {
        ChestVariant.OAK,
        ChestVariant.SPRUCE,
        ChestVariant.BIRCH,
        ChestVariant.JUNGLE,
        ChestVariant.ACACIA,
        ChestVariant.DARK_OAK
    };

    private static final ChestVariant[] TRAPPED_VARIANTS = {
        ChestVariant.TRAPPED_OAK,
        ChestVariant.TRAPPED_SPRUCE,
        ChestVariant.TRAPPED_BIRCH,
        ChestVariant.TRAPPED_JUNGLE,
        ChestVariant.TRAPPED_ACACIA,
        ChestVariant.TRAPPED_DARK_OAK
    };

    @SidedProxy(
        clientSide = "com.github.khikki.rechests.proxy.ClientProxy",
        serverSide = "com.github.khikki.rechests.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    private static final Map<ChestVariant, Block> CHEST_BLOCKS = new EnumMap<ChestVariant, Block>(ChestVariant.class);
    private static final Map<ChestVariant, Item> CHEST_MINECART_ITEMS = new EnumMap<ChestVariant, Item>(ChestVariant.class);

    // These fields are kept as direct references because the rest of the codebase already uses them.
    public static Item oakChestMinecartItem;
    public static Item spruceChestMinecartItem;
    public static Item birchChestMinecartItem;
    public static Item jungleChestMinecartItem;
    public static Item acaciaChestMinecartItem;
    public static Item darkOakChestMinecartItem;
    public static Block oakChest;
    public static Block spruceChest;
    public static Block birchChest;
    public static Block jungleChest;
    public static Block acaciaChest;
    public static Block darkOakChest;
    public static Block trappedOakChest;
    public static Block trappedSpruceChest;
    public static Block trappedBirchChest;
    public static Block trappedJungleChest;
    public static Block trappedAcaciaChest;
    public static Block trappedDarkOakChest;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        createMinecartItems();
        createChestBlocks();
        registerMinecartItems();
        registerChestBlocks();
        registerOreDictionaryEntries();

        GameRegistry.registerTileEntity(VariantChestTileEntity.class, "rechests_variant_chest");
        EntityRegistry.registerModEntity(EntityVariantChestMinecart.class, "variantChestMinecart", 0, this, 80, 3, true);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerRenderers();

        removeVanillaChestRecipe();
        addConversionRecipes();
        addCraftingRecipes();
        addCompatibilityRecipes();
    }

    private void createMinecartItems() {
        for (ChestVariant variant : NORMAL_VARIANTS) {
            CHEST_MINECART_ITEMS.put(variant, new ItemVariantChestMinecart(variant));
        }

        oakChestMinecartItem = CHEST_MINECART_ITEMS.get(ChestVariant.OAK);
        spruceChestMinecartItem = CHEST_MINECART_ITEMS.get(ChestVariant.SPRUCE);
        birchChestMinecartItem = CHEST_MINECART_ITEMS.get(ChestVariant.BIRCH);
        jungleChestMinecartItem = CHEST_MINECART_ITEMS.get(ChestVariant.JUNGLE);
        acaciaChestMinecartItem = CHEST_MINECART_ITEMS.get(ChestVariant.ACACIA);
        darkOakChestMinecartItem = CHEST_MINECART_ITEMS.get(ChestVariant.DARK_OAK);
    }

    private void createChestBlocks() {
        for (ChestVariant variant : ChestVariant.values()) {
            CHEST_BLOCKS.put(variant, new VariantChestBlock(variant));
        }

        oakChest = CHEST_BLOCKS.get(ChestVariant.OAK);
        spruceChest = CHEST_BLOCKS.get(ChestVariant.SPRUCE);
        birchChest = CHEST_BLOCKS.get(ChestVariant.BIRCH);
        jungleChest = CHEST_BLOCKS.get(ChestVariant.JUNGLE);
        acaciaChest = CHEST_BLOCKS.get(ChestVariant.ACACIA);
        darkOakChest = CHEST_BLOCKS.get(ChestVariant.DARK_OAK);
        trappedOakChest = CHEST_BLOCKS.get(ChestVariant.TRAPPED_OAK);
        trappedSpruceChest = CHEST_BLOCKS.get(ChestVariant.TRAPPED_SPRUCE);
        trappedBirchChest = CHEST_BLOCKS.get(ChestVariant.TRAPPED_BIRCH);
        trappedJungleChest = CHEST_BLOCKS.get(ChestVariant.TRAPPED_JUNGLE);
        trappedAcaciaChest = CHEST_BLOCKS.get(ChestVariant.TRAPPED_ACACIA);
        trappedDarkOakChest = CHEST_BLOCKS.get(ChestVariant.TRAPPED_DARK_OAK);
    }

    private void registerMinecartItems() {
        for (ChestVariant variant : NORMAL_VARIANTS) {
            Item item = CHEST_MINECART_ITEMS.get(variant);
            if (item != null) {
                GameRegistry.registerItem(item, getRegistryName(variant) + "_minecart");
            }
        }
    }

    private void registerChestBlocks() {
        for (ChestVariant variant : ChestVariant.values()) {
            Block block = CHEST_BLOCKS.get(variant);
            if (block != null) {
                GameRegistry.registerBlock(block, getRegistryName(variant));
            }
        }
    }

    private void registerOreDictionaryEntries() {
        for (ChestVariant variant : NORMAL_VARIANTS) {
            OreDictionary.registerOre("chestWood", getChestBlock(variant));
        }
    }

    private void addConversionRecipes() {
        GameRegistry.addShapelessRecipe(new ItemStack(oakChest), Blocks.chest);

        for (ChestVariant variant : NORMAL_VARIANTS) {
            GameRegistry.addShapelessRecipe(new ItemStack(Blocks.chest), getChestBlock(variant));
        }

        GameRegistry.addShapelessRecipe(new ItemStack(trappedOakChest), Blocks.trapped_chest);

        for (ChestVariant variant : TRAPPED_VARIANTS) {
            GameRegistry.addShapelessRecipe(new ItemStack(Blocks.trapped_chest), getChestBlock(variant));
        }
    }

    private void addCraftingRecipes() {
        addChestRecipe(ChestVariant.OAK, 0);
        addChestRecipe(ChestVariant.SPRUCE, 1);
        addChestRecipe(ChestVariant.BIRCH, 2);
        addChestRecipe(ChestVariant.JUNGLE, 3);
        addChestRecipe(ChestVariant.ACACIA, 4);
        addChestRecipe(ChestVariant.DARK_OAK, 5);

        for (ChestVariant variant : NORMAL_VARIANTS) {
            addChestMinecartRecipe(variant);
        }

        addTrappedChestRecipe(ChestVariant.OAK, ChestVariant.TRAPPED_OAK);
        addTrappedChestRecipe(ChestVariant.SPRUCE, ChestVariant.TRAPPED_SPRUCE);
        addTrappedChestRecipe(ChestVariant.BIRCH, ChestVariant.TRAPPED_BIRCH);
        addTrappedChestRecipe(ChestVariant.JUNGLE, ChestVariant.TRAPPED_JUNGLE);
        addTrappedChestRecipe(ChestVariant.ACACIA, ChestVariant.TRAPPED_ACACIA);
        addTrappedChestRecipe(ChestVariant.DARK_OAK, ChestVariant.TRAPPED_DARK_OAK);
    }

    private void addCompatibilityRecipes() {
        for (ChestVariant variant : NORMAL_VARIANTS) {
            addHopperRecipe(getChestBlock(variant));
        }

        for (ChestVariant variant : TRAPPED_VARIANTS) {
            GameRegistry.addShapelessRecipe(new ItemStack(Blocks.trapped_chest), getChestBlock(variant));
        }
    }

    private void removeVanillaChestRecipe() {
        List recipes = CraftingManager.getInstance().getRecipeList();
        Iterator iterator = recipes.iterator();

        while (iterator.hasNext()) {
            IRecipe recipe = (IRecipe) iterator.next();
            ItemStack output = recipe.getRecipeOutput();

            if (output != null && output.getItem() == Item.getItemFromBlock(Blocks.chest)) {
                iterator.remove();
            }
        }
    }

    private void addChestRecipe(ChestVariant variant, int plankMeta) {
        GameRegistry.addRecipe(
            new ItemStack(getChestBlock(variant)),
            "XXX", "X X", "XXX",
            'X', new ItemStack(Blocks.planks, 1, plankMeta)
        );
    }

    private void addHopperRecipe(Block chestBlock) {
        GameRegistry.addRecipe(new ItemStack(Blocks.hopper), "I I", "ICI", " I ", 'I', Items.iron_ingot, 'C', chestBlock);
    }

    private void addChestMinecartRecipe(ChestVariant variant) {
        Block chestBlock = getChestBlock(variant);
        Item minecartItem = getChestMinecartItem(variant);

        if (chestBlock != null && minecartItem != null) {
            GameRegistry.addRecipe(new ItemStack(minecartItem), "A", "B", 'A', chestBlock, 'B', Items.minecart);
        }
    }

    private void addTrappedChestRecipe(ChestVariant source, ChestVariant trapped) {
        Block chestBlock = getChestBlock(source);
        Block trappedChestBlock = getChestBlock(trapped);
        GameRegistry.addRecipe(new ItemStack(trappedChestBlock), "#-", '#', chestBlock, '-', Blocks.tripwire_hook);
    }

    private String getRegistryName(ChestVariant variant) {
        return variant.getId() + "_chest";
    }

    public static Block getChestBlock(ChestVariant variant) {
        if (variant == null) {
            return oakChest;
        }

        Block block = CHEST_BLOCKS.get(variant);
        return block != null ? block : oakChest;
    }

    public static Item getChestMinecartItem(ChestVariant variant) {
        if (variant == null) {
            return oakChestMinecartItem;
        }

        return CHEST_MINECART_ITEMS.get(variant);
    }
}
