package com.disalvo.peter;

import com.disalvo.peter.FrameScore.NumericFrameScore;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class LastFrameTest {

    @Test
    public void givenRolledASpare_canRoll1AdditionalTime() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new PinCount(1));
        frame.roll(new PinCount(9));
        frame.roll(new PinCount(1));

        verify(scoreCard).complete(frame);

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(11));
    }

    @Test
    public void givenRolledAStrike_canRoll2AdditionalTimes() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new PinCount(10));
        frame.roll(new PinCount(5));
        frame.roll(new PinCount(5));

        verify(scoreCard).complete(frame);

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(20));
    }

    @Test
    public void givenRolledAStrike_frameIsNotYetComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new PinCount(10));

        verify(scoreCard, never()).complete(frame);
    }

    @Test
    public void givenRolledTwoStrikes_frameIsNotYetComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new PinCount(10));
        frame.roll(new PinCount(10));

        verify(scoreCard, never()).complete(frame);
    }

    @Test
    public void givenRolledThreeStrikes_frameIsComplete() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new PinCount(10));
        frame.roll(new PinCount(10));
        frame.roll(new PinCount(10));

        verify(scoreCard).complete(frame);
    }

    @Test
    public void givenRolledAStrike_noBonusRequested() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new PinCount(10));

        verify(scoreCard, never()).requestBonusRolls(frame, 2);
    }

    @Test
    public void givenRolledASpare_noBonusRequested() {
        ScoreCardFrameCallback scoreCard = mock(ScoreCardFrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new PinCount(6));
        frame.roll(new PinCount(4));

        verify(scoreCard, never()).requestBonusRolls(frame, 1);
    }
}
