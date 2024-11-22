package net.player005.vegandelightfabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;


public class RatsCompat {

    static void initialise() {
        if (isRatsModInstalled())
            registerSoymilkCheeseCauldron();
    }

    public static boolean isRatsModInstalled() {
        return FabricLoader.getInstance().isModLoaded("rats");
    }

    static void registerSoymilkCheeseCauldron() {
        // copied from rats mod with little modification
        // https://github.com/AlexModGuy/Rats/blob/fcf305643c6055f37a6c05d803914bf1a2e0c75b/src/main/java/com/github/alexthe666/rats/registry/RatsCauldronRegistry.java#L27
        CauldronInteraction.EMPTY.put(VeganItems.SOYMILK_BUCKET, (state, level, pos, player, hand, stack) -> {
            if (!level.isClientSide()) {
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(Items.BUCKET)));
                player.awardStat(Stats.USE_CAULDRON);
                level.setBlockAndUpdate(pos, getRatsMilkCauldron().defaultBlockState());
                level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        });
    }

    public static @NotNull Block getRatsMilkCauldron() {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.tryParse("rats:cauldron_milk"));
    }
}
