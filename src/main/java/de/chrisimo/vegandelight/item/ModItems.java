package de.chrisimo.vegandelight.item;

import de.chrisimo.vegandelight.VeganDelight;
import de.chrisimo.vegandelight.block.ModBlocks;
import de.chrisimo.vegandelight.fluid.ModFluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import java.util.ArrayList;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, VeganDelight.MODID);

    //TOFU
    public static final RegistryObject<Item> TOFU = ITEMS.register("tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> SILKEN_TOFU = ITEMS.register("silken_tofu",
        () -> new ConsumableItem(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(4)
                .saturationMod(0.4f)
                .build())
            .craftRemainder(Items.BOWL)
            .stacksTo(16)));
    public static final RegistryObject<Item> SMOKED_TOFU = ITEMS.register("smoked_tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> COOKED_TOFU = ITEMS.register("cooked_tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> COOKED_SMOKED_TOFU = ITEMS.register("cooked_smoked_tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> TOFU_SLICES = ITEMS.register("tofu_slices",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.1f)
            .build())));
    public static final RegistryObject<Item> SMOKED_TOFU_SLICES = ITEMS.register("smoked_tofu_slices",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.1f)
            .build())));
    public static final RegistryObject<Item> COOKED_TOFU_SLICES = ITEMS.register("cooked_tofu_slices",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.1f)
            .build())));
    public static final RegistryObject<Item> COOKED_SMOKED_TOFU_SLICES = ITEMS.register("cooked_smoked_tofu_slices",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.1f)
            .build())));
    public static final RegistryObject<Item> MINCED_TOFU = ITEMS.register("minced_tofu",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.1f)
            .build())));
    public static final RegistryObject<Item> TOFU_PATTY = ITEMS.register("tofu_patty",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(2)
            .saturationMod(0.1f)
            .build())));
    public static final RegistryObject<Item> TOFISH = ITEMS.register("tofish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> COOKED_TOFISH = ITEMS.register("cooked_tofish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> SMOKED_TOFISH = ITEMS.register("smoked_tofish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> COOKED_SMOKED_TOFISH = ITEMS.register("cooked_smoked_tofish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> TOFISH_ROLL = ITEMS.register("tofish_roll",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.4f)
            .build())));
    public static final RegistryObject<Item> SMOKED_TOFISH_ROLL = ITEMS.register("smoked_tofish_roll",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(5)
            .saturationMod(0.4f)
            .build())));


    //INGREDIENTS
    public static final RegistryObject<Item> SALT = ITEMS.register("salt",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOYMILK_BUCKET = ITEMS.register("soymilk_bucket",
        () -> new DrinkableItem(new Item.Properties()
            .craftRemainder(Items.BUCKET)
            .stacksTo(1)) {
            @Override
            public void affectConsumer(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
                var toRemove = new ArrayList<MobEffect>();
                for (MobEffectInstance effect : consumer.getActiveEffects()) {
                    if (effect.isCurativeItem(Items.MILK_BUCKET.getDefaultInstance())) {
                        toRemove.add(effect.getEffect());
                    }
                }
                for (var effect : toRemove) {
                    consumer.removeEffect(effect);
                }
            }

            @Override
            public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
                return new VeganFluidHandler(SOYMILK_BUCKET.get(), Items.BUCKET, true,
                    ModFluids.SOYMILK_FLUID.get(), 1000);
            }
        });

    public static final RegistryObject<Item> SOYMILK_BOTTLE = ITEMS.register("soymilk_bottle",
        () -> new DrinkableItem(new Item.Properties()
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(16)) {
            @Override
            public void affectConsumer(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity consumer) {
                var toRemove = new ArrayList<MobEffect>();
                for (MobEffectInstance effect : consumer.getActiveEffects()) {
                    if (effect.isCurativeItem(Items.MILK_BUCKET.getDefaultInstance())) {
                        toRemove.add(effect.getEffect());
                    }
                }
                for (var effect : toRemove) {
                    consumer.removeEffect(effect);
                }
            }

            @Override
            public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
                return new VeganFluidHandler(SOYMILK_BOTTLE.get(), Items.GLASS_BOTTLE, true,
                    ModFluids.SOYMILK_FLUID.get(), 250);
            }
        });

    public static final RegistryObject<Item> SOYBEAN = ITEMS.register("soybean",
        () -> new ItemNameBlockItem(ModBlocks.SOYBEAN_CROP.get(),
            new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(1)
                .saturationMod(0.1f)
                .build())));

    public static final RegistryObject<Item> LEATHER_SUBSTITUTE = ITEMS.register("leather_substitute",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> APPLESAUCE = ITEMS.register("applesauce",
        () -> new ConsumableItem(new Item.Properties().food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(0.4f)
                .build())
            .craftRemainder(Items.BOWL)
            .stacksTo(16)) {
            @Override
            public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
                return new VeganFluidHandler(APPLESAUCE.get(), Items.BOWL, true,
                    ModFluids.APPLESAUCE_FLUID.get(), 250);
            }
        });
    public static final RegistryObject<Item> APPLESAUCE_BUCKET = ITEMS.register("applesauce_bucket",
        () -> new DrinkableItem(new Item.Properties()
            .food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(0.4f)
                .build())
            .craftRemainder(Items.BUCKET)
            .stacksTo(1)) {
            @Override
            public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
                return new VeganFluidHandler(APPLESAUCE_BUCKET.get(), Items.BUCKET, true,
                    ModFluids.APPLESAUCE_FLUID.get(), 250);
            }
        });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
