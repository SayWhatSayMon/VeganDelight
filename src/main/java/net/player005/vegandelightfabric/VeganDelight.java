package net.player005.vegandelightfabric;

import net.fabricmc.api.ModInitializer;

public class VeganDelight implements ModInitializer {
    public static String modID = "vegandelight";

    @Override
    public void onInitialize() {
        VeganItems.initialize();
    }
}
