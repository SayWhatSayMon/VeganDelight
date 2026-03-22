package net.player005.vegandelightfabric.labels;

import net.minecraft.util.Unit;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.player005.vegandelightfabric.VeganDelightMod;

import static net.player005.vegandelightfabric.VeganDelightMod.getPlatform;

public class VeganDataComponents {

    public static Holder<DataComponentType<Boolean>> is_vegan = getPlatform().register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(VeganDelightMod.modID, "is_vegan"),
        () -> DataComponentType.<Boolean>builder()
            .persistent(Codec.BOOL)
            .networkSynchronized(ByteBufCodecs.BOOL)
            .build()
    );

    public static final Holder<DataComponentType<Unit>> contains_substitutes = getPlatform().register(
        BuiltInRegistries.DATA_COMPONENT_TYPE,
        Identifier.fromNamespaceAndPath(VeganDelightMod.modID, "contains_substitutes"),
        () -> DataComponentType.<Unit>builder()
            .persistent(Unit.CODEC)
            .networkSynchronized(Unit.STREAM_CODEC)
            .build()
    );

    public static void initialize() { }
}
