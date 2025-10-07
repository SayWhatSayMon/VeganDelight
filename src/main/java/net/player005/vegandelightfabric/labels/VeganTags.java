package net.player005.vegandelightfabric.labels;

import de.chrisimo.vegandelight.VeganDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class VeganTags {

    public static final TagKey<Item> SHOULD_HAVE_DATA_COMPONENTS_ADDED = get("has_vegan_data_components");

    public static final TagKey<Item> VEGAN_ALTERNATIVE = get("vegan_alternative");

    public static final TagKey<Item> VEGAN = get("vegan");

    public static final TagKey<Item> NOT_VEGAN = get("not_vegan");

    private static TagKey<Item> get(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(VeganDelight.MODID, name));
    }
}
