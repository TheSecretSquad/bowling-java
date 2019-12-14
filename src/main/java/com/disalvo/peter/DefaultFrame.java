package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;

import java.util.function.Consumer;

public class DefaultFrame implements Frame {

    protected static EmptyFrameScore emptyFrameScore() {
        return new EmptyFrameScore();
    }

    private static final PinCount TotalNumberOfPins = new PinCount(10);
    private static final int DefaultMaxAllowedRolls = 2;
    private static final int DefaultCompleteAfterNumberOfRolls = DefaultMaxAllowedRolls;
    private static final Frame NullFrame = new NullFrame();

    private final ScoreCardFrameCallback scoreCard;
    private FrameBehavior frameBehavior;
    private final Frame previousFrame;
    private final PinCount[] rolls;
    private final int maxAllowedRolls;
    private int completeAfterNumberOfRolls;
    private int currentRoll;
    private boolean isComplete;
    private boolean waitingForBonus;
    private FrameScore bonus;

    public DefaultFrame(ScoreCardFrameCallback scoreCard, FrameBehavior frameBehavior) {
        this(scoreCard, frameBehavior, NullFrame, DefaultMaxAllowedRolls);
    }

    public DefaultFrame(ScoreCardFrameCallback scoreCard, FrameBehavior frameBehavior, int maxAllowedRolls) {
        this(scoreCard, frameBehavior, NullFrame, maxAllowedRolls);
    }

    public DefaultFrame(ScoreCardFrameCallback scoreCard, FrameBehavior frameBehavior, Frame previousFrame) {
        this(scoreCard, frameBehavior, previousFrame, DefaultMaxAllowedRolls);
    }

    public DefaultFrame(ScoreCardFrameCallback scoreCard, FrameBehavior frameBehavior, Frame previousFrame, int maxAllowedRolls) {
        this.scoreCard = scoreCard;
        this.frameBehavior = frameBehavior;
        this.previousFrame = previousFrame;
        this.rolls = new PinCount[maxAllowedRolls];
        this.maxAllowedRolls = maxAllowedRolls;
        this.completeAfterNumberOfRolls = DefaultCompleteAfterNumberOfRolls;
        this.currentRoll = 0;
        this.isComplete = false;
        this.waitingForBonus = false;
        this.bonus = emptyFrameScore();
    }

    @Override
    public void roll(PinCount pinCount) {
        addToCurrentRoll(pinCount);
        countRoll();
        checkSpecialConditions();
        checkForAutoComplete();
    }

    @Override
    public void bonusRoll(PinCount pinCount) {
        bonus = bonus.sumWith(pinCount);
    }

    private void addToCurrentRoll(PinCount pinCount) {
        if(isComplete())
            throw new TooManyRollsException();
        rolls[currentRoll] = pinCount;
    }

    private boolean isComplete() {
        return isComplete;
    }

    private void countRoll() {
        ++currentRoll;
    }

    private void checkSpecialConditions() {
        if(isStrike()) {
            reportStrike();
        }
        else if(isSpare()) {
            reportSpare();
        }
    }

    private void reportSpare() {
        completeAfterNumberOfRolls = maxAllowedRolls;
        frameBehavior.spare(this);
    }

    private void reportStrike() {
        completeAfterNumberOfRolls = maxAllowedRolls;
        frameBehavior.strike(this);
    }

    private void checkForAutoComplete() {
        if(shouldComplete()) {
            complete();
        }
    }

    private boolean shouldComplete() {
        return filledAllowedRolls() && !waitingForBonus();
    }

    private boolean filledAllowedRolls() {
        return currentRoll == completeAfterNumberOfRolls;
    }

    private boolean isStrike() {
        return pinCountForCurrentRoll().equals(TotalNumberOfPins);
    }

    private PinCount pinCountForCurrentRoll() {
        return pinCountForRoll(currentRoll - 1);
    }

    private boolean isSpare() {
        return currentRoll == DefaultCompleteAfterNumberOfRolls && sumRolls().sameAs(TotalNumberOfPins);
    }

    private PinCount pinCountForRoll(int index) {
        PinCount pinCount = rolls[index];
        return pinCount != null ? pinCount : new PinCount(0);
    }

    private FrameScore sumRolls() {
        FrameScore rollsScore = new NumericFrameScore(0);
        for(int rollIndex = 0; rollIndex < maxAllowedRolls; rollIndex++) {
            PinCount pinCount = rolls[rollIndex];
            if(pinCount != null)
                rollsScore = rollsScore.sumWith(pinCount);
        }
        return rollsScore;
    }

    @Override
    public void score(Consumer<FrameScore> frameScoreConsumer) {
        if(shouldAppearEmpty())
            frameScoreConsumer.accept(emptyFrameScore());
        else
            previousFrame.score(sumWithPreviousFrameScore(frameScoreConsumer));
    }

    public void complete() {
        isComplete = true;
        scoreCard.complete(this);
    }

    @Override
    public void bonusComplete() {
        waitingForBonus = false;
    }

    public void requestBonusRolls(int numberOfRolls) {
        scoreCard.requestBonusRolls(this, numberOfRolls);
        waitingForBonus = true;
    }

    private boolean shouldAppearEmpty() {
        return !isComplete() || waitingForBonus();
    }

    private boolean waitingForBonus() {
        return waitingForBonus;
    }

    private Consumer<FrameScore> sumWithPreviousFrameScore(Consumer<FrameScore> frameScoreConsumer) {
        return previousFrameScore -> frameScoreConsumer.accept(frameScore(previousFrameScore));
    }

    private FrameScore frameScore(FrameScore previousFrameScore) {
        return sumRolls().sumWith(bonus).sumWith(previousFrameScore);
    }

    private static class NullFrame implements Frame {

        @Override
        public void roll(PinCount pinCount) {
            // Do nothing
        }

        @Override
        public void bonusRoll(PinCount pinCount) {
            // Do nothing
        }

        @Override
        public void score(Consumer<FrameScore> frameScoreConsumer) {
            frameScoreConsumer.accept(emptyFrameScore());
        }

        @Override
        public void bonusComplete() {
            // Do nothing
        }
    }
}
