package com.disalvo.peter;

public final class LastFrameBehavior extends FrameBehavior {

    public LastFrameBehavior() {
        super(new NumericPinCount(10));
    }

    @Override
    public void strike(DefaultFrame frame) {
        allowTotalPerFrame(new NumericPinCount(30));
    }

    @Override
    public void spare(DefaultFrame frame) {
        allowTotalPerFrame(new NumericPinCount(20));
    }
}
