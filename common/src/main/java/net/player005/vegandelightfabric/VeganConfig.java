package net.player005.vegandelightfabric;

import eu.midnightdust.lib.config.MidnightConfig;

@SuppressWarnings({"NotNullFieldNotInitialized", "unused"})
public class VeganConfig extends MidnightConfig {
    public static final String LABELS = "labels";

    @Comment(category = LABELS) public static Comment dataComponentExplanation;
    @Comment(category = LABELS) public static Comment spacer1;
    @Comment(category = LABELS) public static Comment spacer2;

    @Entry(category = LABELS) public static DataComponentUsageMode useComponents = DataComponentUsageMode.ONLY_FOODS;

    @Comment(category = LABELS) public static Comment spacer3;
    @Comment(category = LABELS) public static Comment labelExplanation;
    @Comment(category = LABELS) public static Comment spacer4;

    @Entry(category = LABELS) public static VeganLabelMode veganLabelMode = VeganLabelMode.ONLY_WHEN_REPLACEMENT_USED;

    @Entry(category = LABELS) public static NotVeganLabelMode notVeganLabelMode = NotVeganLabelMode.ONLY_EXCEPTIONS;

    public enum DataComponentUsageMode {
        ALL_ITEMS, ONLY_FOODS, NONE
    }

    public enum VeganLabelMode {
        ALL_VEGAN_ITEMS, ONLY_WHEN_REPLACEMENT_USED, NONE
    }

    public enum NotVeganLabelMode {
        ALL_NON_VEGAN_ITEMS, ALL_NON_VEGAN_FOODS, ONLY_EXCEPTIONS, NONE
    }
}
