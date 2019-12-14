package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import net.bytebuddy.pool.TypePool.Empty;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class DefaultFrameTest {

    @Test(expected = TooManyRollsException.class)
    public void givenACompletedFrame_cantRollAnymore() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, 2);

        frame.complete();
        frame.roll(new PinCount(1));
    }

    @Test(expected = TooManyRollsException.class)
    public void givenRolledMaximumNumberOfRolls_cantRollAnymore() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, 2);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenAFrameIsNotComplete_frameScoreIsEmpty() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);
    }

    @Test
    public void givenAFrameIsComplete_frameIsScored() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
        frame.complete();

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(2));
    }

    @Test(expected = TooManyRollsException.class)
    public void givenRollingMaximumNumberOfRolls_frameIsCompleteAutomatically() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, 3);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenRolledASpare_andHas3MaxRolls_canRollAnAdditionalTime() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, 3);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));
        frame.roll(new PinCount(1));

        verify(scoreCard).spare(frame);
        verify(scoreCard).completeAutomatically(frame);

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(11));
    }

    @Test
    public void givenRolledAStrike_andHas3MaxRolls_canRoll2AdditionalTimes() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, 3);

        frame.roll(new PinCount(10));
        frame.roll(new PinCount(5));
        frame.roll(new PinCount(5));

        verify(scoreCard).strike(frame);
        verify(scoreCard).completeAutomatically(frame);

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(20));
    }

    @Test
    public void givenRolledASpare_reportsSpare() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, 2);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));

        verify(scoreCard).spare(frame);
    }

    @Test
    public void givenRolledAStrike_reportsStrike() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, 2);

        frame.roll(new PinCount(10));

        verify(scoreCard).strike(frame);
    }

    @Test
    public void givenFrameIsComplete_andHasAPendingBonus_frameScoreIsEmptyUntilBonusIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, 2);

        frame.roll(new PinCount(10));
        frame.complete();
        frame.pendingBonus();

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new EmptyFrameScore());

        frame.bonusComplete();

        frame.score(frameScoreConsumer);
        verify(frameScoreConsumer).accept(new NumericFrameScore(10));
    }

    @Test
    public void givenPreviousFrame_scoreIsSumOfFrameAndPreviousFrame() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, 2);

        previousFrame.roll(new PinCount(1));
        previousFrame.roll(new PinCount(1));
        previousFrame.complete();

        Frame frame = new DefaultFrame(scoreCard, previousFrame, 2);

        frame.roll(new PinCount(2));
        frame.roll(new PinCount(2));
        frame.complete();

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(6));
    }
}
