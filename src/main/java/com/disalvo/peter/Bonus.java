package com.disalvo.peter;

public class Bonus {

    private final ScoreCardBonusCallback scoreCard;
    private final Frame frame;
    private int numberOfRolls;

    public Bonus(ScoreCardBonusCallback scoreCard, Frame frame, int numberOfRolls) {
        this.scoreCard = scoreCard;
        this.frame = frame;
        this.numberOfRolls = numberOfRolls;
    }

    public void roll(NumericPinCount pinCount) {
        if(isComplete())
            return;

        frame.bonusRoll(pinCount);
        countRoll();
        checkComplete();
    }

    private void checkComplete() {
        if(isComplete()) {
            complete();
        }
    }

    private void complete() {
        frame.bonusComplete();
        scoreCard.bonusComplete(this);
    }

    private boolean isComplete() {
        return numberOfRolls == 0;
    }

    private void countRoll() {
        --numberOfRolls;
    }
}
