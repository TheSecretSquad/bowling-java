package com.disalvo.peter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BowlingScoreCard implements ScoreCard, ScoreCardFrameCallback, ScoreCardBonusCallback {

    private static final FrameNumber LastFrameNumber = new FrameNumber(10);
    private static final int NumberOfStrikeBonusRolls = 2;
    private static final int NumberOfSpareBonusRolls = 1;

    private final Frames frames;
    private final Bonuses bonuses;
    private FrameNumber currentFrameNumber;

    public BowlingScoreCard() {
        this.frames = new Frames(LastFrameNumber, new FrameFactory(this));
        this.bonuses = new Bonuses();
        this.currentFrameNumber = new FrameNumber(1);
    }

    @Override
    public void roll(PinCount pinCount) {
        bonuses.roll(pinCount);
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
    public void complete(Frame frame) {
        advanceFrame();
    }

    @Override
    public void strike(Frame frame) {
        completeWithBonus(frame, strikeBonus(frame));
    }

    private Bonus strikeBonus(Frame frame) {
        return bonus(frame, NumberOfStrikeBonusRolls);
    }

    @Override
    public void spare(Frame frame) {
        completeWithBonus(frame, spareBonus(frame));
    }

    private Bonus spareBonus(Frame frame) {
        return bonus(frame, NumberOfSpareBonusRolls);
    }

    private Bonus bonus(Frame frame, int numberOfRolls) {
        return new Bonus(this, frame, numberOfRolls);
    }

    private void completeWithBonus(Frame frame, Bonus bonus) {
        if(!isLastFrame()) {
            frame.complete();
            frame.pendingBonus();
            bonuses.register(bonus);
        }
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
