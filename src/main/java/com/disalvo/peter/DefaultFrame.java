package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import com.disalvo.peter.Roll.PinCountRoll;
import com.disalvo.peter.Roll.SymbolRoll;

import java.util.Arrays;

public class DefaultFrame implements Frame {

    protected static EmptyFrameScore emptyFrameScore() {
        return new EmptyFrameScore();
    }

    private static final NumericPinCount TotalNumberOfPins = new NumericPinCount(10);
    private static final int DefaultMaxAllowedRollIndex = 1;
    private static final int DefaultCompleteAfterRollIndex = DefaultMaxAllowedRollIndex;
    private static final Frame NullFrame = new NullFrame();

    private final FrameCallback frameCallback;
    private final FrameNumber frameNumber;
    private final FrameBehavior frameBehavior;
    private final Frame previousFrame;
    private final PinCount[] rawRolls;
    private final Roll[] recordedRolls;
    private final int maxAllowedRollIndex;
    private int completeAfterRollIndex;
    private int currentRollIndex;
    private boolean isComplete;
    private boolean waitingForBonus;
    private FrameScore bonus;

    public DefaultFrame(FrameCallback frameCallback, FrameNumber frameNumber, FrameBehavior frameBehavior, int maxAllowedRolls) {
        this(frameCallback, frameNumber, frameBehavior, NullFrame, maxAllowedRolls);
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
        this.rawRolls = new PinCount[maxAllowedRolls];
        this.recordedRolls = new Roll[maxAllowedRolls];
        Arrays.fill(rawRolls, new EmptyPinCount());
        this.maxAllowedRollIndex = maxAllowedRolls - 1;
        this.completeAfterRollIndex = DefaultCompleteAfterRollIndex;
        this.currentRollIndex = 0;
        this.isComplete = false;
        this.waitingForBonus = false;
        this.bonus = new NumericFrameScore(0);
    }

    @Override
    public void roll(NumericPinCount pinCount) {
        validateRoll(pinCount);
        addToCurrentRoll(pinCount);
        checkSpecialConditions();
        checkForAutoComplete();
        countRoll();
    }

    private void validateRoll(NumericPinCount pinCount) {
        frameBehavior.validateRoll(sumRolls().sumWith(pinCount));
    }

    @Override
    public void bonusRoll(NumericPinCount pinCount) {
        bonus = bonus.sumWith(pinCount);
    }

    private void addToCurrentRoll(NumericPinCount pinCount) {
        if(isComplete())
            throw new TooManyRollsException();
        rawRolls[currentRollIndex] = pinCount;
        recordedRolls[currentRollIndex] = new PinCountRoll(pinCount);
    }

    private boolean isComplete() {
        return isComplete;
    }

    private void countRoll() {
        ++currentRollIndex;
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
        recordedRolls[currentRollIndex] = new SymbolRoll("/");
        completeAfterRollIndex = maxAllowedRollIndex;
        frameBehavior.spare(this);
    }

    private void reportStrike() {
        recordedRolls[currentRollIndex] = new SymbolRoll("X");
        completeAfterRollIndex = maxAllowedRollIndex;
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
        return currentRollIndex == completeAfterRollIndex;
    }

    private boolean isStrike() {
        return currentRoll().sameAs(TotalNumberOfPins);
    }

    private PinCount currentRoll() {
        return rollAtIndex(currentRollIndex);
    }

    private boolean isSpare() {
        return currentRollIndex == DefaultCompleteAfterRollIndex && sumRolls().sameAs(TotalNumberOfPins);
    }

    private PinCount rollAtIndex(int index) {
        PinCount roll = rawRolls[index];
        return roll != null ? roll : new NumericPinCount(0);
    }

    private PinCount sumRolls() {
        PinCount rollsScore = new NumericPinCount(0);
        for(int rollIndex = 0; rollIndex <= maxAllowedRollIndex; rollIndex++) {
            PinCount pinCount = rawRolls[rollIndex];
            if(pinCount != null)
                rollsScore = rollsScore.sumWith(pinCount);
        }
        return rollsScore;
    }

    @Override
    public FrameScore score() {
        return shouldAppearEmpty() ? emptyFrameScore() : previousFrame.score().sumWith(localScore());
    }

    private FrameScore localScore() {
        return sumRolls().sumWith(bonus);
    }

    public void complete() {
        isComplete = true;
        frameCallback.completeFrame();
    }

    @Override
    public void bonusComplete() {
        waitingForBonus = false;
    }

    @Override
    public void printOn(ScoreCardPrintMedia printMedia) {
        printMedia.printFrame(frameNumber, score(), new Rolls(rawRolls));
    }

    @Override
    public void printOn(ScoreCardPrintMedia2 printMedia) {
        printMedia.beginPrintFrame();
        printMedia.printFrameNumber(frameNumber);
        printMedia.printFrameScore(score());
        printRolls(printMedia);
        printMedia.endPrintFrame();
    }

    private void printRolls(ScoreCardPrintMedia2 printMedia) {
        for(Roll roll : recordedRolls) {
            printMedia.printRoll(roll != null ? roll : new PinCountRoll(new EmptyPinCount()));
        }
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
        public void bonusComplete() {
            // Do nothing
        }

        @Override
        public void printOn(ScoreCardPrintMedia printMedia) {
        }

        @Override
        public void printOn(ScoreCardPrintMedia2 printMedia) {

        }

        @Override
        public FrameScore score() {
            return new NumericFrameScore(0);
        }
    }
}
