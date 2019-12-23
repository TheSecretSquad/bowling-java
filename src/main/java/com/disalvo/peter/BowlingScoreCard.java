package com.disalvo.peter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BowlingScoreCard implements ScoreCard, FrameCallback, ScoreCardBonusCallback {

    private static final FrameNumber LastFrameNumber = new FrameNumber(10);
    private static final int NumberOfStrikeBonusRolls = 2;
    private static final int NumberOfSpareBonusRolls = 1;

    private final ScoreCardListener scoreCardListener;
    private final Frames frames;
    private final Bonuses bonuses;
    private FrameNumber currentFrameNumber;

    public BowlingScoreCard(ScoreCardListener scoreCardListener) {
        this.scoreCardListener = scoreCardListener;
        this.frames = new Frames(LastFrameNumber, new FrameFactory(this));
        this.bonuses = new Bonuses();
        this.currentFrameNumber = new FrameNumber(1);
    }

    @Override
    public void roll(NumericPinCount pinCount) {
        bonuses.roll(pinCount);
        frames.current(frame -> frame.roll(pinCount));
    }

    @Override
    public void frameScores(BiConsumer<FrameNumber, FrameScore> frameScoreConsumer) {
        frames.each(eachFrameWithNumber(frameScoreConsumer));
    }

    @Override
    public void printOn(ScoreCardPrintMedia printMedia) {
        frames.each((frameNumber, frame) -> frame.printOn(printMedia));
    }

    private BiConsumer<FrameNumber, Frame> eachFrameWithNumber(BiConsumer<FrameNumber, FrameScore> frameScoreConsumer) {
        return (frameNumber, frame) -> frame.score(eachFrameScoreWithNumber(frameScoreConsumer, frameNumber));
    }

    private Consumer<FrameScore> eachFrameScoreWithNumber(BiConsumer<FrameNumber, FrameScore> frameScoreConsumer, FrameNumber frameNumber) {
        return frameScore -> frameScoreConsumer.accept(frameNumber, frameScore);
    }

    @Override
    public void complete(Frame frame) {
        advanceFrame();
    }

    @Override
    public void requestBonusRolls(Frame frame, int numberOfRolls) {
        bonuses.register(bonus(frame, numberOfRolls));
    }

    private Bonus bonus(Frame frame, int numberOfRolls) {
        return new Bonus(this, frame, numberOfRolls);
    }

    private boolean isLastFrame() {
        return currentFrameNumber.equals(LastFrameNumber);
    }

    private void advanceFrame() {
        if(isGameOver())
            return;
        currentFrameNumber = currentFrameNumber.advanced();
        frames.advance();
    }

    private boolean isGameOver() {
        return isLastFrame();
    }

    @Override
    public void bonusComplete(Bonus bonus) {
        bonuses.unregister(bonus);
    }
}
