package com.disalvo.peter;

public class LastFrameBehavior implements FrameBehavior {

    private PinCount totalAllowedPerFrame;

    public LastFrameBehavior() {
        totalAllowedPerFrame = new NumericPinCount(10);
    }

    @Override
    public void strike(DefaultFrame frame) {
        totalAllowedPerFrame = new NumericPinCount(30);
    }

    @Override
    public void spare(DefaultFrame frame) {
        totalAllowedPerFrame = new NumericPinCount(20);
    }

    @Override
    public void validateRoll(DefaultFrame defaultFrame, NumericPinCount pinCount) {
        defaultFrame.rejectRollIfOver(totalAllowedPerFrame, pinCount);
    }
}
