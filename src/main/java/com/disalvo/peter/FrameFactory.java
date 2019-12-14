package com.disalvo.peter;

public class FrameFactory {
    private final ScoreCardFrameCallback scoreCard;

    public FrameFactory(ScoreCardFrameCallback scoreCard) {
        this.scoreCard = scoreCard;
    }

    public Frame nextFrame(Frame previousFrame) {
        return new NormalFrame(scoreCard, previousFrame);
    }

    public Frame lastFrame(Frame previousFrame) {
        return new LastFrame(scoreCard, previousFrame);
    }

    public Frame firstFrame() {
        return new NormalFrame(scoreCard);
    }
}
