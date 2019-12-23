package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;

import java.util.function.Consumer;

public class DefaultFrame implements Frame {

    protected static EmptyFrameScore emptyFrameScore() {
        return new EmptyFrameScore();
    }

    private static final NumericPinCount TotalNumberOfPins = new NumericPinCount(10);
    private static final int DefaultMaxAllowedRolls = 2;
    private static final int DefaultCompleteAfterNumberOfRolls = DefaultMaxAllowedRolls;
    private static final Frame NullFrame = new NullFrame();

    private final FrameCallback frameCallback;
    private FrameNumber frameNumber;
    private FrameBehavior frameBehavior;
    private final Frame previousFrame;
    private final NumericPinCount[] rolls;
    private final int maxAllowedRolls;
    private int completeAfterNumberOfRolls;
    private int currentRoll;
    private boolean isComplete;
    private boolean waitingForBonus;
    private FrameScore bonus;

    public DefaultFrame(FrameCallback frameCallback, FrameNumber frameNumber, FrameBehavior frameBehavior) {
        this(frameCallback, frameNumber, frameBehavior, NullFrame, DefaultMaxAllowedRolls);
    }

    public DefaultFrame(FrameCallback frameCallback, FrameNumber frameNumber, FrameBehavior frameBehavior, int maxAllowedRolls) {
        this(frameCallback, frameNumber, frameBehavior, NullFrame, maxAllowedRolls);
    }

    public DefaultFrame(FrameCallback frameCallback, FrameNumber frameNumber, FrameBehavior frameBehavior, Frame previousFrame) {
        this(frameCallback, frameNumber, frameBehavior, previousFrame, DefaultMaxAllowedRolls);
    }

    public DefaultFrame(FrameCallback frameCallback,
                        FrameNumber frameNumber,
                        FrameBehavior frameBehavior,
                        Frame previousFrame,
                        int maxAllowedRolls) {
        this.frameCallback = frameCallback;
        this.frameNumber = frameNumber;
        this.frameBehavior = frameBehavior;
        this.previousFrame = previousFrame;
        this.rolls = new NumericPinCount[maxAllowedRolls];
        this.maxAllowedRolls = maxAllowedRolls;
        this.completeAfterNumberOfRolls = DefaultCompleteAfterNumberOfRolls;
        this.currentRoll = 0;
        this.isComplete = false;
        this.waitingForBonus = false;
        this.bonus = emptyFrameScore();
    }

    @Override
    public void roll(NumericPinCount pinCount) {
        addToCurrentRoll(pinCount);
        countRoll();
        checkSpecialConditions();
        checkForAutoComplete();
    }

    @Override
    public void bonusRoll(NumericPinCount pinCount) {
        bonus = bonus.sumWith(pinCount);
    }

    private void addToCurrentRoll(NumericPinCount pinCount) {
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

    private NumericPinCount pinCountForCurrentRoll() {
        return pinCountForRoll(currentRoll - 1);
    }

    private boolean isSpare() {
        return currentRoll == DefaultCompleteAfterNumberOfRolls && sumRolls().sameAs(TotalNumberOfPins);
    }

    private NumericPinCount pinCountForRoll(int index) {
        NumericPinCount pinCount = rolls[index];
        return pinCount != null ? pinCount : new NumericPinCount(0);
    }

    private FrameScore sumRolls() {
        FrameScore rollsScore = new NumericFrameScore(0);
        for(int rollIndex = 0; rollIndex < maxAllowedRolls; rollIndex++) {
            NumericPinCount pinCount = rolls[rollIndex];
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
        frameCallback.complete(this);
    }

    @Override
    public void bonusComplete() {
        waitingForBonus = false;
    }

    @Override
    public void printOn(FramePrintMedia printMedia) {
        score(frameScore -> printMedia.printFrame(frameNumber, frameScore, new Rolls(rolls)));
    }

    public void requestBonusRolls(int numberOfRolls) {
        frameCallback.requestBonusRolls(this, numberOfRolls);
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
        public void roll(NumericPinCount pinCount) {
            // Do nothing
        }

        @Override
        public void bonusRoll(NumericPinCount pinCount) {
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

        @Override
        public void printOn(FramePrintMedia printMedia) {
        }
    }
}
