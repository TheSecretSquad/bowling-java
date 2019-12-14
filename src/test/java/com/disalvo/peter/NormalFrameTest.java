package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class NormalFrameTest {

    @Test(expected = TooManyRollsException.class)
    public void givenRolledThreeTimes_andDidntKnockDownAllPins_TooManyRolls() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenNormalRolls_frameScoreIsEmptyUntilFrameIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(1));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new EmptyFrameScore());

        frame.roll(new PinCount(1));

        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(2));
    }

    @Test
    public void givenPreviousFrame_scoreIsSumOfFrameAndPreviousFrame() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        previousFrame.roll(new PinCount(1));
        previousFrame.roll(new PinCount(1));

        Frame frame = new NormalFrame(scoreCard, previousFrame);
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(4));
    }

    @Test
    public void givenRolledASpare_frameIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));

        verify(scoreCard).frameComplete(frame);
    }

    @Test(expected = TooManyRollsException.class)
    public void givenRolledASpare_oneMoreRollIsTooMany() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenRolledASpare_requestsBonusRolls() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));

        verify(scoreCard).requestBonusForFrame(frame);
    }

    @Test
    public void givenHasSpareBonus_bonusIsCompleteAfterOneBonusRoll() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));
        frame.bonusRoll(new PinCount(1));

        verify(scoreCard).bonusComplete(frame);
    }

    @Test
    public void givenRolledASpare_frameScoreIsSumOfRollsAndBonus() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));
        frame.bonusRoll(new PinCount(3));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);
        verify(frameScoreConsumer).accept(new NumericFrameScore(13));
    }

    @Test
    public void givenRolledASpare_frameScoreIsEmptyUntilBonusIsFulfilled() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new EmptyFrameScore());
        frame.bonusRoll(new PinCount(3));

        frame.score(frameScoreConsumer);
        verify(frameScoreConsumer).accept(new NumericFrameScore(13));
    }

    @Test(expected = TooManyRollsException.class)
    public void givenRolledAStrike_rollingOnceMoreIsTooManyTimes() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(10));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenRolledAStrike_frameIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(10));

        verify(scoreCard).frameComplete(frame);
    }

    @Test
    public void givenRolledAStrike_requestsBonus() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(10));

        verify(scoreCard).requestBonusForFrame(frame);
    }

    @Test
    public void givenRolledAStrike_bonusIsCompleteAfterTwoBonusRolls() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(10));
        frame.bonusRoll(new PinCount(1));
        frame.bonusRoll(new PinCount(1));

        verify(scoreCard).bonusComplete(frame);
    }

    @Test
    public void givenRolledAStrike_frameScoreIsSumOfRollsAndBonus() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(10));
        frame.bonusRoll(new PinCount(3));
        frame.bonusRoll(new PinCount(3));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(16));
    }

    @Test
    public void givenRolledAStrike_frameScoreIsEmptyUntilBonusIsFulfilled() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new NormalFrame(scoreCard);

        frame.roll(new PinCount(10));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new EmptyFrameScore());

        reset(frameScoreConsumer);

        frame.bonusRoll(new PinCount(3));

        frame.score(frameScoreConsumer);
        verify(frameScoreConsumer).accept(new EmptyFrameScore());

        reset(frameScoreConsumer);

        frame.bonusRoll(new PinCount(3));

        frame.score(frameScoreConsumer);
        verify(frameScoreConsumer).accept(new NumericFrameScore(16));
    }
}
