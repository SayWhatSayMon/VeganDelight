package net.player005.vegandelightfabric;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.item.MilkBottleItem;

import java.util.function.Supplier;

public class VeganItems {

    // TOFU
    public static final Holder<Item> TOFU = register("tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> SILKEN_TOFU = register("silken_tofu",
        () -> new ConsumableItem(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(4)
                .saturationModifier(0.4f)
                .build())
            .craftRemainder(Items.BOWL)
            .stacksTo(16)));
    public static final Holder<Item> SMOKED_TOFU = register("smoked_tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> COOKED_TOFU = register("cooked_tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> COOKED_SMOKED_TOFU = register("cooked_smoked_tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> TOFU_SLICES = register("tofu_slices",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build())));
    public static final Holder<Item> SMOKED_TOFU_SLICES = register("smoked_tofu_slices",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build())));
    public static final Holder<Item> COOKED_TOFU_SLICES = register("cooked_tofu_slices",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build())));
    public static final Holder<Item> COOKED_SMOKED_TOFU_SLICES = register("cooked_smoked_tofu_slices",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build())));
    public static final Holder<Item> MINCED_TOFU = register("minced_tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build())));
    public static final Holder<Item> TOFU_PATTY = register("tofu_patty",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationModifier(0.1f)
            .build())));
    public static final Holder<Item> TOFISH = register("tofish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> COOKED_TOFISH = register("cooked_tofish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> SMOKED_TOFISH = register("smoked_tofish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> COOKED_SMOKED_TOFISH = register("cooked_smoked_tofish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> TOFISH_ROLL = register("tofish_roll",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.4f)
            .build())));
    public static final Holder<Item> SMOKED_TOFISH_ROLL = register("smoked_tofish_roll",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(0.4f)
            .build())));


    // INGREDIENTS
    public static final Holder<Item> SALT = register("salt",
        () -> new Item(new Item.Properties()));

    public static final Holder<Item> SOYMILK_BUCKET = register("soymilk_bucket",
        () -> new MilkBottleItem(
            new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)
        )
    );

    public static final Holder<Item> SOYMILK_BOTTLE = register("soymilk_bottle",
        () -> new MilkBottleItem(new Item.Properties()
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(16)));

    public static final Holder<Item> SOYBEAN = register("soybean",
        () -> new ItemNameBlockItem(VeganBlocks.SOYBEAN_CROP.value(),
            new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(1)
                .saturationModifier(0.1f)
                .build())));

    public static final Holder<Item> LEATHER_SUBSTITUTE = register("leather_substitute",
        () -> new Item(new Item.Properties()));

    public static final Holder<Item> APPLESAUCE = register("applesauce",
        () -> new ConsumableItem(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationModifier(0.4f)
                .build())
            .craftRemainder(Items.BOWL)
            .stacksTo(16)));
    public static final Holder<Item> APPLESAUCE_BUCKET = register("applesauce_bucket",
        () -> new DrinkableItem(
            new Item.Properties()
                .food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.4f)
                    .build())
                .craftRemainder(Items.BUCKET)
                .stacksTo(1)
        )
    );


    public static Item[] getAllItems() {
        return new Item[]{
            SOYBEAN.value(), SALT.value(), TOFU.value(), TOFU_SLICES.value(), COOKED_TOFU.value(),
            COOKED_TOFU_SLICES.value(), SMOKED_TOFU.value(), SMOKED_TOFU_SLICES.value(),
            COOKED_SMOKED_TOFU.value(), COOKED_SMOKED_TOFU_SLICES.value(), SILKEN_TOFU.value(),
            MINCED_TOFU.value(), TOFU_PATTY.value(), TOFISH.value(), COOKED_TOFISH.value(),
            SMOKED_TOFISH.value(), COOKED_SMOKED_TOFISH.value(), TOFISH_ROLL.value(),
            SMOKED_TOFISH_ROLL.value(), SOYMILK_BUCKET.value(), SOYMILK_BOTTLE.value(),
            APPLESAUCE_BUCKET.value(), APPLESAUCE.value(), LEATHER_SUBSTITUTE.value()
        };
    }

    public static @NotNull Holder<Item> register(String id, Supplier<Item> item) {
        ResourceLocation itemID = ResourceLocation.tryBuild(VeganDelightMod.modID, id);

        assert itemID != null;
        return VeganDelightMod.getPlatform().register(BuiltInRegistries.ITEM, itemID, item);
    }

    static void initialise() { }
}
