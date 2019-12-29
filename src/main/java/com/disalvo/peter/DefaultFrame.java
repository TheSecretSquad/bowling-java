package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.Roll.PinCountRoll;
import com.disalvo.peter.Roll.SymbolRoll;

import java.util.Arrays;
import java.util.function.Consumer;

public class DefaultFrame implements Frame {

    protected static EmptyFrameScore emptyFrameScore() {
        return new EmptyFrameScore();
    }

    private static final NumericPinCount TotalNumberOfPins = new NumericPinCount(10);
    private static final int DefaultMaxAllowedRollIndex = 1;
    private static final int DefaultCompleteAfterRollIndex = DefaultMaxAllowedRollIndex;
    private static final Frame NullFrame = new NullFrame();

    private final FrameCallback frameCallback;
    private FrameNumber frameNumber;
    private FrameBehavior frameBehavior;
    private final Frame previousFrame;
    private final PinCount[] rawRolls;
    private final Roll[] recordedRolls;
    private final int maxAllowedRollIndex;
    private int completeAfterRollIndex;
    private int currentRoll;
    private boolean isComplete;
    private boolean waitingForBonus;
    private FrameScore bonus;

    public DefaultFrame(FrameCallback frameCallback, FrameNumber frameNumber, FrameBehavior frameBehavior, int maxAllowedRollIndex) {
        this(frameCallback, frameNumber, frameBehavior, NullFrame, maxAllowedRollIndex);
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
        this.currentRoll = 0;
        this.isComplete = false;
        this.waitingForBonus = false;
        this.bonus = emptyFrameScore();
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
        rawRolls[currentRoll] = pinCount;
        recordedRolls[currentRoll] = new PinCountRoll(pinCount);
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
        recordedRolls[currentRoll] = new SymbolRoll("/");
        completeAfterRollIndex = maxAllowedRollIndex;
        frameBehavior.spare(this);
    }

    private void reportStrike() {
        recordedRolls[currentRoll] = new SymbolRoll("X");
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
        return currentRoll == completeAfterRollIndex;
    }

    private boolean isStrike() {
        return currentRoll().sameAs(TotalNumberOfPins);
    }

    private PinCount currentRoll() {
        return rollAtIndex(currentRoll);
    }

    private boolean isSpare() {
        return currentRoll == DefaultCompleteAfterRollIndex && sumRolls().sameAs(TotalNumberOfPins);
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
    public void score(Consumer<FrameScore> frameScoreConsumer) {
        if(shouldAppearEmpty())
            frameScoreConsumer.accept(emptyFrameScore());
        else
            previousFrame.score(sumWithPreviousFrameScore(frameScoreConsumer));
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
        score(frameScore -> printMedia.printFrame(frameNumber, frameScore, new Rolls(rawRolls)));
    }

    @Override
    public void printOn(ScoreCardPrintMedia2 printMedia) {
        score(frameScore -> {
                printMedia.beginPrintFrame();
                printMedia.printFrameNumber(frameNumber);
                printMedia.printFrameScore(frameScore);
                printRolls(printMedia);
                printMedia.endPrintFrame();
            }
        );
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

    private Consumer<FrameScore> sumWithPreviousFrameScore(Consumer<FrameScore> frameScoreConsumer) {
        return previousFrameScore -> frameScoreConsumer.accept(frameScore(previousFrameScore));
    }

    private FrameScore frameScore(FrameScore previousFrameScore) {
        return sumRolls().sumWith(bonus).sumWith(previousFrameScore);
    }

    protected void rejectRollIfOver(NumericPinCount pinCount, PinCount totalAllowedPerFrame) {
        if(!sumRolls().sumWith(pinCount).isValidWithin(totalAllowedPerFrame))
            throw new InvalidRollAttemptException();
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
        public void printOn(ScoreCardPrintMedia printMedia) {
        }

        @Override
        public void printOn(ScoreCardPrintMedia2 printMedia) {

        }
    }
}
