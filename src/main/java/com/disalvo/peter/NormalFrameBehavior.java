package com.disalvo.peter;

public class NormalFrameBehavior implements FrameBehavior {

    private final PinCount totalAllowedPerFrame;

    public NormalFrameBehavior() {
        totalAllowedPerFrame = new NumericPinCount(10);
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

    @Override
    public void validateRoll(DefaultFrame frame, NumericPinCount pinCount) {
        frame.rejectRollIfOver(totalAllowedPerFrame, pinCount);
    }
}
