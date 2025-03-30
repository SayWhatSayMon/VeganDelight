package net.player005.vegandelightfabric.labels;

import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.player005.vegandelightfabric.VeganDelightMod;

public class VeganDataComponents {

    public static DataComponentType<Boolean> vegan = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "is_vegan"),
        DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
            .build()
    );

    public static final DataComponentType<Unit> containsSubstitutes = Registry.register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "contains_substitutes"),
        DataComponentType.<Unit>builder()
            .persistent(Codec.unit(Unit.INSTANCE))
            .networkSynchronized(StreamCodec.unit(Unit.INSTANCE))
            .build()
    );

    public static final DataComponentPatch setIsVegan = DataComponentPatch.builder().set(vegan, true).build();
    public static final DataComponentPatch setIsNotVegan = DataComponentPatch.builder().set(vegan, false).build();

    public static final DataComponentPatch setContainsSubstitutes =
        DataComponentPatch.builder().set(containsSubstitutes, Unit.INSTANCE).build();

    public static void initialise() { }
}
