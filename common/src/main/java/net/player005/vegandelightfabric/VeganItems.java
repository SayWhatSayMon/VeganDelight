package net.player005.vegandelightfabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.player005.vegandelightfabric.blocks.VeganBlocks;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;

public class VeganItems {

    // TOFU
    public static final Item TOFU = register("tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item SILKEN_TOFU = register("silken_tofu",
            new ConsumableItem(new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.4f)
                            .build())
                    .craftRemainder(Items.BOWL)
                    .stacksTo(16)));
    public static final Item SMOKED_TOFU = register("smoked_tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item COOKED_TOFU = register("cooked_tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item COOKED_SMOKED_TOFU = register("cooked_smoked_tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(4)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item TOFU_SLICES = register("tofu_slices",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
                    .build())));
    public static final Item SMOKED_TOFU_SLICES = register("smoked_tofu_slices",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
                    .build())));
    public static final Item COOKED_TOFU_SLICES = register("cooked_tofu_slices",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
                    .build())));
    public static final Item COOKED_SMOKED_TOFU_SLICES = register("cooked_smoked_tofu_slices",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
                    .build())));
    public static final Item MINCED_TOFU = register("minced_tofu",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
                    .build())));
    public static final Item TOFU_PATTY = register("tofu_patty",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1f)
                    .build())));
    public static final Item TOFISH = register("tofish",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item COOKED_TOFISH = register("cooked_tofish",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item SMOKED_TOFISH = register("smoked_tofish",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item COOKED_SMOKED_TOFISH = register("cooked_smoked_tofish",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(3)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item TOFISH_ROLL = register("tofish_roll",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(0.4f)
                    .build())));
    public static final Item SMOKED_TOFISH_ROLL = register("smoked_tofish_roll",
            new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(0.4f)
                    .build())));


    // INGREDIENTS
    public static final Item SALT = register("salt",
            new Item(new Item.Properties()));

    public static final Item SOYMILK_BUCKET = register("soymilk_bucket",
            new MilkBottleItem(
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
                            .saturationModifier(0.1f)
                            .build())));

    public static final Item LEATHER_SUBSTITUTE = register("leather_substitute",
            new Item(new Item.Properties()));

    public static final Item APPLESAUCE = register("applesauce",
            new ConsumableItem(new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationModifier(0.4f)
                            .build())
                    .craftRemainder(Items.BOWL)
                    .stacksTo(16)));
    public static final Item APPLESAUCE_BUCKET = register("applesauce_bucket",
            new DrinkableItem(
                    new Item.Properties()
                            .food(new FoodProperties.Builder()
                                    .nutrition(2)
                                    .saturationModifier(0.4f)
                                    .build())
                            .craftRemainder(Items.BUCKET)
                            .stacksTo(1)
            )
    );

    public static Item[] allItems = {
            SOYBEAN, SALT, TOFU, TOFU_SLICES, COOKED_TOFU, COOKED_TOFU_SLICES, SMOKED_TOFU, SMOKED_TOFU_SLICES,
            COOKED_SMOKED_TOFU, COOKED_SMOKED_TOFU_SLICES, SILKEN_TOFU, MINCED_TOFU, TOFU_PATTY, TOFISH, COOKED_TOFISH,
            SMOKED_TOFISH, COOKED_SMOKED_TOFISH, TOFISH_ROLL, SMOKED_TOFISH_ROLL, SOYMILK_BUCKET, SOYMILK_BOTTLE,
            APPLESAUCE_BUCKET, APPLESAUCE, LEATHER_SUBSTITUTE
    };


    public static @NotNull Item register(String id, Item item) {
        ResourceLocation itemID = ResourceLocation.tryBuild(VeganDelightMod.modID, id);

        assert itemID != null;
        return Registry.register(BuiltInRegistries.ITEM, itemID, item);
    }

    public static void initialise() {
    }
}
