package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.ComposterBlock;
import net.player005.vegandelightfabric.blocks.VeganBlocks;

public class VeganDelight implements ModInitializer {
    public static String modID = "vegandelight";

    @Override
    public void onInitialize() {
        VeganItems.initialize();
        VeganFluids.initialise();
        VeganBlocks.initialise();

        registerTrades();


        ComposterBlock.COMPOSTABLES.put(VeganItems.SOYBEAN, 0.45f);
        ComposterBlock.COMPOSTABLES.put(VeganBlocks.WILD_SOYBEAN, 0.65f);
    }

    private void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1,
                (factories) -> factories.add((trader, random) -> new MerchantOffer(
                        new ItemStack(VeganItems.SOYBEAN, random.nextIntBetweenInclusive(16, 24)),
                        new ItemStack(Items.EMERALD, 1),
                        12, 5, 0.05f
                )));
    }
}
