package com.disalvo.peter;

public class FrameFactory {
    private final FrameCallback scoreCard;

    public FrameFactory(FrameCallback scoreCard) {
        this.scoreCard = scoreCard;
    }

    public Frame nextFrame(FrameNumber frameNumber, Frame previousFrame) {
        return new DefaultFrame(scoreCard, frameNumber, new NormalFrameBehavior(), previousFrame, 2);
    }

    public Frame lastFrame(FrameNumber frameNumber, Frame previousFrame) {
        return new DefaultFrame(scoreCard, frameNumber, new LastFrameBehavior(), previousFrame, 3);
    }

    public Frame firstFrame(FrameNumber frameNumber) {
        return new DefaultFrame(scoreCard, frameNumber, new NormalFrameBehavior(), 2);
    }
}
