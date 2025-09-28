package net.player005.vegandelightfabric.labels;

import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.player005.vegandelightfabric.VeganDelightMod;

import static net.player005.vegandelightfabric.VeganDelightMod.getPlatform;

public class VeganDataComponents {

    public static Holder<DataComponentType<Boolean>> is_vegan = getPlatform().register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        ResourceLocation.fromNamespaceAndPath(VeganDelightMod.modID, "is_vegan"),
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
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

    public static void initialize() { }
}
