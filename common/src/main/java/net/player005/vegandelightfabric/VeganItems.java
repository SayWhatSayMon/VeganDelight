package net.player005.vegandelightfabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.player005.vegandelightfabric.blocks.VeganBlocks;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;

public class VeganItems {

    // TOFU
    public static final Item TOFU = register("tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item SILKEN_TOFU = register("silken_tofu",
            new ConsumableItem(new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationMod(0.4f)
                            .build())
                    .craftRemainder(Items.BOWL)
                    .stacksTo(16)));
    public static final Item SMOKED_TOFU = register("smoked_tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item COOKED_TOFU = register("cooked_tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item COOKED_SMOKED_TOFU = register("cooked_smoked_tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item TOFU_SLICES = register("tofu_slices",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationMod(0.1f)
                    .build())));
    public static final Item SMOKED_TOFU_SLICES = register("smoked_tofu_slices",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationMod(0.1f)
                    .build())));
    public static final Item COOKED_TOFU_SLICES = register("cooked_tofu_slices",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationMod(0.1f)
                    .build())));
    public static final Item COOKED_SMOKED_TOFU_SLICES = register("cooked_smoked_tofu_slices",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationMod(0.1f)
                    .build())));
    public static final Item MINCED_TOFU = register("minced_tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationMod(0.1f)
                    .build())));
    public static final Item TOFU_PATTY = register("tofu_patty",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationMod(0.1f)
                    .build())));
    public static final Item TOFISH = register("tofish",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item COOKED_TOFISH = register("cooked_tofish",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item SMOKED_TOFISH = register("smoked_tofish",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item COOKED_SMOKED_TOFISH = register("cooked_smoked_tofish",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item TOFISH_ROLL = register("tofish_roll",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationMod(0.4f)
                    .build())));
    public static final Item SMOKED_TOFISH_ROLL = register("smoked_tofish_roll",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationMod(0.4f)
                    .build())));


    // INGREDIENTS
    public static final Item SALT = register("salt",
            new Item(new Item.Properties()));

    public static final Item SOYMILK_BUCKET = register("soymilk_bucket",
            new BucketItem(
                    VeganFluids.SOYMILK,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
            )
    );

    public static final Item SOYMILK_BOTTLE = register("soymilk_bottle",
            new MilkBottleItem(new Item.Properties()
                    .craftRemainder(Items.GLASS_BOTTLE)
                    .stacksTo(16)));

    public static final Item SOYBEAN = register("soybean",
            new ItemNameBlockItem(VeganBlocks.SOYBEAN_CROP,
                    new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationMod(0.1f)
                            .build())));

    public static final Item LEATHER_SUBSTITUTE = register("leather_substitute",
            new Item(new Item.Properties()));

    public static final Item APPLESAUCE = register("applesauce",
            new ConsumableItem(new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(0.4f)
                            .build())
                    .craftRemainder(Items.BOWL)
                    .stacksTo(16)));
    public static final Item APPLESAUCE_BUCKET = register("applesauce_bucket",
            new BucketItem(
                    VeganFluids.APPLESAUCE,
                    new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
            )
    );

    public static ItemLike[] all = {
            VeganBlocks.SOYBEAN_BAG, VeganBlocks.WILD_SOYBEAN, SILKEN_TOFU, TOFU, TOFU_SLICES, COOKED_TOFU,
            COOKED_TOFU_SLICES, SMOKED_TOFU, SMOKED_TOFU_SLICES, COOKED_SMOKED_TOFU, COOKED_SMOKED_TOFU_SLICES,
            MINCED_TOFU, TOFU_PATTY, TOFISH, COOKED_TOFISH, SMOKED_TOFISH, COOKED_SMOKED_TOFISH, TOFISH_ROLL,
            SMOKED_TOFISH_ROLL, SOYMILK_BUCKET, SOYMILK_BOTTLE, SOYBEAN, SALT, APPLESAUCE, APPLESAUCE_BUCKET,
            LEATHER_SUBSTITUTE
    };


    @SuppressWarnings("DataFlowIssue")
    public static final ResourceKey<CreativeModeTab> VEGAN_ITEMS_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.tryBuild(VeganDelightMod.modID, "vegan_ingredients")
    );

    public static final CreativeModeTab VEGAN_ITEM_TAB = VeganDelightMod.platform
            .registerItemTab(new ItemStack(SMOKED_TOFISH_ROLL), Component.translatable("itemGroup.vegan_delight"), all);

    public static @NotNull Item register(String id, Item item) {
        ResourceLocation itemID = ResourceLocation.tryBuild(VeganDelightMod.modID, id);

        assert itemID != null;
        return Registry.register(BuiltInRegistries.ITEM, itemID, item);
    }

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, VEGAN_ITEMS_KEY, VEGAN_ITEM_TAB);
    }
}