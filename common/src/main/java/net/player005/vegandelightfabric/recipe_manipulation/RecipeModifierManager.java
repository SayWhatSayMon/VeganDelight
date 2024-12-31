package net.player005.vegandelightfabric.recipe_manipulation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.util.Map;

public class RecipeModifierManager extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private static final Logger LOGGER = LogUtils.getLogger();

    public RecipeModifierManager() {
        super(GSON, "recipe_modifiers");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        var list = NonNullList.<RecipeModifier>create();

        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            var id = entry.getKey();

            try {
                RecipeModifier recipeModifier = null; // TODO
                list.add(recipeModifier);
            } catch (IllegalArgumentException | JsonParseException exception) {
                LOGGER.error("Parsing error loading recipe modifier {}", id, exception);
            }
        }

        RecipeModification.updateModifiers(list);
    }
}
