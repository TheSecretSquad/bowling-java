package com.disalvo.peter;

public abstract class FrameBehavior {

    private PinCount totalAllowedPerFrame;

    protected FrameBehavior(PinCount totalAllowedPerFrame) {
        this.totalAllowedPerFrame = totalAllowedPerFrame;
    }

    public abstract void strike(DefaultFrame frame);
    public abstract void spare(DefaultFrame frame);

    public void validateRoll(PinCount currentRollTotal) {
        if(!currentRollTotal.isValidForMaximum(totalAllowedPerFrame))
            throw new InvalidRollAttemptException();
    }

    protected void allowTotalPerFrame(PinCount pinCount) {
        totalAllowedPerFrame = pinCount;
    }
}
