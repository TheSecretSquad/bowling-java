package com.disalvo.peter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BowlingScoreCard implements ScoreCard, ScoreCardFrameCallback {

    private static final FrameNumber LastFrameNumber = new FrameNumber(10);

    private final Frames frames;
    private final BonusesFrames bonusesFrames;
    private FrameNumber currentFrameNumber;

    public BowlingScoreCard() {
        this.frames = new Frames(LastFrameNumber, new FrameFactory(this));
        this.bonusesFrames = new BonusesFrames();
        this.currentFrameNumber = new FrameNumber(1);
    }

    @Override
    public void roll(PinCount pinCount) {
        bonusesFrames.roll(pinCount);
        frames.current(frame -> frame.roll(pinCount));
    }

    @Override
    public void frameScores(BiConsumer<FrameNumber, FrameScore> frameScoreConsumer) {
        frames.each(eachFrameWithNumber(frameScoreConsumer));
    }

    private BiConsumer<FrameNumber, Frame> eachFrameWithNumber(BiConsumer<FrameNumber, FrameScore> frameScoreConsumer) {
        return (frameNumber, frame) -> frame.score(eachFrameScoreWithNumber(frameScoreConsumer, frameNumber));
    }

    private Consumer<FrameScore> eachFrameScoreWithNumber(BiConsumer<FrameNumber, FrameScore> frameScoreConsumer, FrameNumber frameNumber) {
        return frameScore -> frameScoreConsumer.accept(frameNumber, frameScore);
    }

    @Override
    public void frameComplete(Frame frame) {
        if(isGameOver())
            return;
        currentFrameNumber = currentFrameNumber.advanced();
        frames.advance();
    }

    @Override
    public void requestBonusForFrame(Frame frame) {
        bonusesFrames.register(frame);
    }

    @Override
    public void bonusComplete(Frame frame) {
        bonusesFrames.unregister(frame);
    }

    private boolean isGameOver() {
        return currentFrameNumber.equals(LastFrameNumber);
    }
}
