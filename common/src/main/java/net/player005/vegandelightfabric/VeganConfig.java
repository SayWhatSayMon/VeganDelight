package net.player005.vegandelightfabric;

import eu.midnightdust.lib.config.MidnightConfig;

@SuppressWarnings({"NotNullFieldNotInitialized", "unused"})
public class VeganConfig extends MidnightConfig {
    public static final String LABELS = "labels";

    @Entry(category = LABELS) public static DataComponentUsageMode useComponents = DataComponentUsageMode.ALL_ITEMS;
    @Comment(category = LABELS) public static Comment dataComponentExplanation;

    public enum DataComponentUsageMode {
        ALL_ITEMS, NONE
    }
}
