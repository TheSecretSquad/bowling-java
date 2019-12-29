package com.disalvo.peter;

public final class NormalFrameBehavior extends FrameBehavior {

    public NormalFrameBehavior() {
        super(new NumericPinCount(10));
    }

    @Override
    public void strike(DefaultFrame frame) {
        frame.complete();
        frame.requestBonusRolls(2);
    }

    @Override
    public void spare(DefaultFrame frame) {
        frame.complete();
        frame.requestBonusRolls(1);
    }
}
