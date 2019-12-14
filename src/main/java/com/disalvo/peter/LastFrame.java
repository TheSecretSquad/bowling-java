package com.disalvo.peter;

public final class LastFrame extends AbstractFrame {

    private static final int MaxNumberOfRolls = 3;

    public LastFrame(ScoreCardFrameCallback scoreCard, Frame previousFrame) {
        super(scoreCard, previousFrame, MaxNumberOfRolls);
    }

    @Override
    protected void handleStrike(ScoreCardFrameCallback scoreCard) {
        // Do nothing
    }

    @Override
    protected void handleSpare(ScoreCardFrameCallback scoreCard) {
        // Do nothing
    }

    @Override
    protected boolean hasPendingBonus() {
        return false;
    }

    @Override
    protected FrameScore bonusScore() {
        return emptyFrameScore();
    }

    @Override
    protected void doBonusRoll(PinCount pinCount, ScoreCardFrameCallback scoreCard) {
        throw new InvalidBonusRollException();
    }

    @Override
    protected void pendingBonus(int numberOfRolls) {
        throw new InvalidBonusRollException();
    }
}
