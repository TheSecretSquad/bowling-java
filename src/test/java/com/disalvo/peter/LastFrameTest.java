package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class LastFrameTest {

    @Test(expected = TooManyRollsException.class)
    public void givenRolledThreeTimes_andDidntKnockDownAllPins_TooManyRolls() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenNormalRolls_frameScoreIsEmptyUntilFrameIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

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

        Frame frame = new LastFrame(scoreCard, previousFrame);
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(4));
    }

    @Test
    public void givenRolledASpare_frameIsNotComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));

        verify(scoreCard, never()).frameComplete(frame);
    }

    @Test
    public void givenRolledASpare_aThirdRollCompletesTheFrame() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));
        frame.roll(new PinCount(1));

        verify(scoreCard).frameComplete(frame);
    }

    @Test(expected = TooManyRollsException.class)
    public void givenRolledASpare_aFourthRollIsTooManyTimes() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenRolledASpare_doesNotRequestBonus() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));

        verify(scoreCard, never()).requestBonusForFrame(frame);
    }

    @Test(expected = TooManyRollsException.class)
    public void givenRolledAStrike_aFourthRollIsTooManyTimes() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(10));
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenRolledAStrike_frameIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(10));

        verify(scoreCard, never()).frameComplete(frame);
    }

    @Test
    public void givenRolledAStrike_requestsBonus() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(10));

        verify(scoreCard, never()).requestBonusForFrame(frame);
    }

    @Test(expected = InvalidBonusRollException.class)
    public void givenLastFrame_bonusRollsAreInvalid() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.bonusRoll(new PinCount(1));
    }

    @Test
    public void givenRolledAStrike_frameScoreIsSumOfRolls() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new NormalFrame(scoreCard);
        Frame frame = new LastFrame(scoreCard, previousFrame);

        frame.roll(new PinCount(10));
        frame.roll(new PinCount(10));
        frame.roll(new PinCount(10));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(30));
    }
}
