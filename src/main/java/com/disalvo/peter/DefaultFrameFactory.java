package com.disalvo.peter;

public class DefaultFrameFactory implements FrameFactory {
    private final FrameCallback scoreCard;

    public DefaultFrameFactory(FrameCallback scoreCard) {
        this.scoreCard = scoreCard;
    }

    @Override
    public Frame nextFrame(FrameNumber frameNumber, Frame previousFrame) {
        return new DefaultFrame(scoreCard, frameNumber, new NormalFrameBehavior(), previousFrame, 2);
    }

    @Override
    public Frame lastFrame(FrameNumber frameNumber, Frame previousFrame) {
        return new DefaultFrame(scoreCard, frameNumber, new LastFrameBehavior(), previousFrame, 3);
    }

    @Override
    public Frame firstFrame(FrameNumber frameNumber) {
        return new DefaultFrame(scoreCard, frameNumber, new NormalFrameBehavior(), 2);
    }
}
