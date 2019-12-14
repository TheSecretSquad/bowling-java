package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;

import java.util.function.Consumer;

public abstract class AbstractFrame implements Frame {

    protected static EmptyFrameScore emptyFrameScore() {
        return new EmptyFrameScore();
    }

    private static final PinCount TotalNumberOfPins = new PinCount(10);
    private static final int DefaultCompleteAfterNumberOfRolls = 2;

    private final ScoreCardFrameCallback scoreCard;
    private final Frame previousFrame;
    private final PinCount[] rolls;
    private final int maxPossibleRolls;
    private int completeAfterNumberOfRolls;
    private int currentRoll;
    private boolean isComplete;

    public AbstractFrame(ScoreCardFrameCallback scoreCard, int maxPossibleRolls) {
        this(scoreCard, new NullFrame(), maxPossibleRolls);
    }

    public AbstractFrame(ScoreCardFrameCallback scoreCard, Frame previousFrame) {
        this(scoreCard, previousFrame, DefaultCompleteAfterNumberOfRolls);
    }

    public AbstractFrame(
            ScoreCardFrameCallback scoreCard,
            Frame previousFrame,
            int maxPossibleRolls) {
        this.scoreCard = scoreCard;
        this.previousFrame = previousFrame;
        this.rolls = new PinCount[maxPossibleRolls];
        this.maxPossibleRolls = maxPossibleRolls;
        this.completeAfterNumberOfRolls = DefaultCompleteAfterNumberOfRolls;
        this.currentRoll = 0;

        this.isComplete = false;
    }

    public void roll(PinCount pinCount) {
        addToCurrentRoll(pinCount);
        countRoll();
        checkBonuses();
        checkComplete();
    }

    private void addToCurrentRoll(PinCount pinCount) {
        if(isComplete())
            throw new TooManyRollsException();
        rolls[currentRoll] = pinCount;
    }

    private void countRoll() {
        ++currentRoll;
    }

    private void checkBonuses() {
        if(isStrike()) {
            handleStrike();
        }
        else if(isSpare()) {
            handleSpare();
        }
    }

    private boolean isStrike() {
        return pinCountForCurrentRoll().equals(TotalNumberOfPins);
    }

    private PinCount pinCountForCurrentRoll() {
        return pinCountForRoll(currentRoll - 1);
    }

    private PinCount pinCountForRoll(int index) {
        PinCount pinCount = rolls[index];
        return pinCount != null ? pinCount : new PinCount(0);
    }

    private void handleStrike() {
        completeAfterNumberOfRolls = maxPossibleRolls;
        handleStrike(scoreCard);
    }

    protected abstract void handleStrike(ScoreCardFrameCallback scoreCard);

    private boolean isSpare() {
        return currentRoll == DefaultCompleteAfterNumberOfRolls && sumRolls().sameAs(TotalNumberOfPins);
    }

    private boolean filledAllowedRolls() {
        return currentRoll == completeAfterNumberOfRolls;
    }

    private void handleSpare() {
        completeAfterNumberOfRolls = maxPossibleRolls;
        handleSpare(scoreCard);
    }

    protected abstract void handleSpare(ScoreCardFrameCallback scoreCard);

    private void checkComplete() {
        if(shouldComplete())
            completeFrame();
    }

    private boolean shouldComplete() {
        return filledAllowedRolls();
    }

    protected void completeFrame() {
        isComplete = true;
        scoreCard.frameComplete(this);
    }

    public void score(Consumer<FrameScore> frameScoreConsumer) {
        if(shouldAppearEmpty())
            frameScoreConsumer.accept(emptyFrameScore());
        else
            previousFrame.score(sumWithPreviousFrameScore(frameScoreConsumer));
    }

    private boolean shouldAppearEmpty() {
        return !isComplete() || hasPendingBonus();
    }

    private boolean isComplete() {
        return isComplete;
    }

    protected abstract boolean hasPendingBonus();

    private Consumer<FrameScore> sumWithPreviousFrameScore(Consumer<FrameScore> frameScoreConsumer) {
        return previousFrameScore -> frameScoreConsumer.accept(frameScore(previousFrameScore));
    }

    private FrameScore frameScore(FrameScore previousFrameScore) {
        return sumRolls().sumWith(previousFrameScore).sumWith(bonusScore());
    }

    protected abstract FrameScore bonusScore();

    private FrameScore sumRolls() {
        FrameScore rollsScore = new NumericFrameScore(0);
        for(int rollIndex = 0; rollIndex < completeAfterNumberOfRolls; rollIndex++) {
            PinCount pinCount = rolls[rollIndex];
            if(pinCount != null)
                rollsScore = rollsScore.sumWith(pinCount);
        }
        return rollsScore;
    }

    @Override
    public void bonusRoll(PinCount pinCount) {
        doBonusRoll(pinCount, scoreCard);
    }

    protected abstract void doBonusRoll(PinCount pinCount, ScoreCardFrameCallback scoreCard);

    protected void completeAfterNumberOfRolls(int number) {
        this.completeAfterNumberOfRolls = number;
    }

    protected abstract void pendingBonus(int numberOfRolls);

    private static class NullFrame implements Frame {

        @Override
        public void roll(PinCount pinCount) {
            // Do nothing
        }

        @Override
        public void score(Consumer<FrameScore> frameScoreConsumer) {
            frameScoreConsumer.accept(emptyFrameScore());
        }

        @Override
        public void bonusRoll(PinCount pinCount) {
            // Do nothing
        }
    }
}
