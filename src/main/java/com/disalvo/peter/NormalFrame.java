package com.disalvo.peter;

public final class NormalFrame extends AbstractFrame {

    private static final int MaxNumberOfRolls = 2;
    private static final int StrikeBonusRolls = 2;
    private static final int SpareBonusRolls = 1;

    private FrameScore bonusScore;
    private int expectingBonusRolls;

    public NormalFrame(ScoreCardFrameCallback scoreCard) {
        super(scoreCard, MaxNumberOfRolls);
        this.bonusScore = emptyFrameScore();
        this.expectingBonusRolls = 0;
    }

    public NormalFrame(ScoreCardFrameCallback scoreCard, Frame previousFrame) {
        super(scoreCard, previousFrame);
        this.bonusScore = emptyFrameScore();
        this.expectingBonusRolls = 0;
    }

    @Override
    protected void handleStrike(ScoreCardFrameCallback scoreCard) {
        completeFrame();
        requestBonusRolls(scoreCard, StrikeBonusRolls);
    }

    @Override
    protected void handleSpare(ScoreCardFrameCallback scoreCard) {
        requestBonusRolls(scoreCard, SpareBonusRolls);
    }

    @Override
    protected boolean hasPendingBonus() {
        return expectingBonusRolls > 0;
    }

    @Override
    protected FrameScore bonusScore() {
        return bonusScore;
    }

    @Override
    protected void doBonusRoll(PinCount pinCount, ScoreCardFrameCallback scoreCard) {
        bonusScore = bonusScore.sumWith(pinCount);
        --expectingBonusRolls;
        if(expectingBonusRolls == 0)
            scoreCard.bonusComplete(this);
    }

    @Override
    protected void pendingBonus(int numberOfRolls) {
        expectingBonusRolls = numberOfRolls;
    }

    private void requestBonusRolls(ScoreCardFrameCallback scoreCard, int numberOfRolls) {
        scoreCard.requestBonusForFrame(this);
        pendingBonus(numberOfRolls);
    }
}
