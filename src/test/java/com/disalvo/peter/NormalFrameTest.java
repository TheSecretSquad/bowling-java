package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import net.bytebuddy.pool.TypePool.Empty;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class NormalFrameTest {

    @Test(expected = TooManyRollsException.class)
    public void givenRolledTwice_cantRollAnymore() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));
    }

    @Test
    public void givenAFrameIsNotComplete_frameScoreIsEmpty() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);

        frame.roll(new PinCount(1));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new EmptyFrameScore());
    }

    @Test
    public void givenTwoRollsMade_frameIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(scoreCard).complete(frame);
    }

    @Test
    public void givenAFrameIsComplete_frameIsScored() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(1));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(2));
    }

    @Test
    public void givenRolledASpare_reportsSpare() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));

        verify(scoreCard).requestBonusRolls(frame, 1);
    }

    @Test
    public void givenRolledAStrike_reportsStrike() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);

        frame.roll(new PinCount(10));

        verify(scoreCard).requestBonusRolls(frame, 2);
    }

    @Test
    public void givenFrameIsComplete_andHasAPendingBonus_frameScoreIsEmptyUntilBonusIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);

        frame.roll(new PinCount(10));

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
        Frame previousFrame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);

        previousFrame.roll(new PinCount(1));
        previousFrame.roll(new PinCount(1));

        Frame frame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), previousFrame, 2);

        frame.roll(new PinCount(2));
        frame.roll(new PinCount(2));

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(6));
    }
}
