package net.player005.vegandelightfabric.labels;

import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.player005.vegandelightfabric.VeganDelightMod;
import org.apache.logging.log4j.util.Lazy;

import java.util.function.Supplier;

import static net.player005.vegandelightfabric.VeganDelightMod.getPlatform;

public class VeganDataComponents {

    public static Holder<DataComponentType<Unit>> is_not_vegan = getPlatform().register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "is_not_vegan"),
        () -> DataComponentType.<Unit>builder()
            .persistent(Codec.unit(Unit.INSTANCE))
            .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
            .build()
    );

    public static final Holder<DataComponentType<Unit>> contains_substitutes = getPlatform().register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "contains_substitutes"),
        () -> DataComponentType.<Unit>builder()
            .persistent(Codec.unit(Unit.INSTANCE))
            .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
            .build()
    );

    public static final Supplier<DataComponentPatch> setIsNotVegan =
        Lazy.lazy(() -> DataComponentPatch.builder().set(is_not_vegan.value(), Unit.INSTANCE).build());

    public static final Supplier<DataComponentPatch> setContainsSubstitutes =
        Lazy.lazy(() -> DataComponentPatch.builder().set(contains_substitutes.value(), Unit.INSTANCE).build());

    public static void initialise() { }
}
