package com.disalvo.peter;

public class FrameFactory {
    private final ScoreCardFrameCallback scoreCard;

    public FrameFactory(ScoreCardFrameCallback scoreCard) {
        this.scoreCard = scoreCard;
    }

    public Frame nextFrame(Frame previousFrame) {
        return new DefaultFrame(scoreCard, previousFrame, 2);
    }

    public Frame lastFrame(Frame previousFrame) {
        return new DefaultFrame(scoreCard, previousFrame, 3);
    }

    public Frame firstFrame() {
        return new DefaultFrame(scoreCard, 2);
    }
}
