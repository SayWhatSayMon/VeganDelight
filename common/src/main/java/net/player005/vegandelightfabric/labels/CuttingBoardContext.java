package net.player005.vegandelightfabric.labels;

public class CuttingBoardContext {
    public static final ThreadLocal<Boolean> inputIsVegan = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<Boolean> inputHasSubstitutes = ThreadLocal.withInitial(() -> null);
}
