package me.desht.pneumaticredux.common.semiblock;

public class SemiBlockPassiveProvider extends SemiBlockActiveProvider {
    public static String ID = "logistic_frame_passive_provider";

    @Override
    public int getColor() {
        return 0xFFFF0000;
    }

    @Override
    public boolean shouldProvideTo(int priority) {
        return priority > 2;
    }
}