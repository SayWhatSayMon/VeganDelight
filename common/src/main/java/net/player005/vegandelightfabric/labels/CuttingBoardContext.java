package net.player005.vegandelightfabric.labels;

public class CuttingBoardContext {
    public static final ThreadLocal<VeganLabels.VeganStatus> inputStatus = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<Boolean> inputHasSubstitutes = ThreadLocal.withInitial(() -> null);
}
