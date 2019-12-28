package com.disalvo.peter;

public class BowlingScoreCard implements ScoreCard, FrameCallback, ScoreCardBonusCallback {

    private static final FrameNumber LastFrameNumber = new FrameNumber(10);

    private final Frames frames;
    private final Bonuses bonuses;
    private FrameNumber lastFrameNumber;
    private FrameNumber currentFrameNumber;

    public BowlingScoreCard() {
        this.frames = new Frames(LastFrameNumber, new DefaultFrameFactory(this));
        this.bonuses = new Bonuses();
        this.lastFrameNumber = LastFrameNumber;
        this.currentFrameNumber = new FrameNumber(1);
    }

    public BowlingScoreCard(FrameFactory frameFactory) {
        this.frames = new Frames(LastFrameNumber, frameFactory);
        this.bonuses = new Bonuses();
        this.lastFrameNumber = LastFrameNumber;
        this.currentFrameNumber = new FrameNumber(1);
    }

    public BowlingScoreCard(FrameFactory frameFactory, FrameNumber lastFrameNumber) {
        this.frames = new Frames(lastFrameNumber, frameFactory);
        this.bonuses = new Bonuses();
        this.lastFrameNumber = lastFrameNumber;
        this.currentFrameNumber = new FrameNumber(1);
    }

    @Override
    public void roll(NumericPinCount pinCount) {
        bonuses.roll(pinCount);
        frames.current(frame -> frame.roll(pinCount));
    }

    @Override
    public void printOn(ScoreCardPrintMedia printMedia) {
        frames.each((frameNumber, frame) -> frame.printOn(printMedia));
    }

    @Override
    public void completeFrame() {
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
        return currentFrameNumber.equals(lastFrameNumber);
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
