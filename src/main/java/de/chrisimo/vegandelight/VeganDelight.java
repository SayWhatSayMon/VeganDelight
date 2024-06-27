package de.chrisimo.vegandelight;

import com.mojang.logging.LogUtils;
import de.chrisimo.vegandelight.block.ModBlocks;
import de.chrisimo.vegandelight.fluid.ModFluidTypes;
import de.chrisimo.vegandelight.fluid.ModFluids;
import de.chrisimo.vegandelight.item.ModCreativeTabs;
import de.chrisimo.vegandelight.item.ModItems;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(VeganDelight.MODID)
public class VeganDelight
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "vegandelight";

    public VeganDelight()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModFluidTypes.register(modEventBus);
        ModFluids.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.WILD_SOYBEAN.getId(), ModBlocks.POTTED_WILD_SOYBEAN);
        });
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventSubscriber {
        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                ComposterBlock.COMPOSTABLES.put(ModItems.SOYBEAN.get(), 0.65f); // Adjust the chance as needed
            });
        }
    }

    @Mod.EventBusSubscriber(modid = MODID)
    public static class newSubscriber {
        public newSubscriber() {
            MinecraftForge.EVENT_BUS.register(this);
        }
        @SubscribeEvent
        public static void onVillagerTrades(VillagerTradesEvent event) {
            if (event.getType() == VillagerProfession.FARMER) {
                List<VillagerTrades.ItemListing> level1Trades = event.getTrades().get(1);

                level1Trades.add((entity, random) -> new MerchantOffer(
                        new ItemStack(ModItems.SOYBEAN.get(), 18), // Soybean in quantity
                        new ItemStack(Items.EMERALD, 1), // Resulting Emeralds
                        12, // Max uses
                        10, // Villager XP
                        0.05f // Price multiplier
                ));
            }
        }
    }
}
