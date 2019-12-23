package com.disalvo.peter;

import com.disalvo.peter.FrameScore.NumericFrameScore;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class LastFrameTest {

    private FrameNumber anyFrameNumber() {
        return new FrameNumber(1);
    }

    @Test
    public void givenRolledASpare_canRoll1AdditionalTime() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new NumericPinCount(1));
        frame.roll(new NumericPinCount(9));
        frame.roll(new NumericPinCount(1));

        verify(scoreCard).complete(frame);

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(11));
    }

    @Test
    public void givenRolledAStrike_canRoll2AdditionalTimes() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new NumericPinCount(10));
        frame.roll(new NumericPinCount(5));
        frame.roll(new NumericPinCount(5));

        verify(scoreCard).complete(frame);

        Consumer<FrameScore> frameScoreConsumer = mock(Consumer.class);
        frame.score(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new NumericFrameScore(20));
    }

    @Test
    public void givenRolledAStrike_frameIsNotYetComplete() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new NumericPinCount(10));

        verify(scoreCard, never()).complete(frame);
    }

    @Test
    public void givenRolledTwoStrikes_frameIsNotYetComplete() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new NumericPinCount(10));
        frame.roll(new NumericPinCount(10));

        verify(scoreCard, never()).complete(frame);
    }

    @Test
    public void givenRolledThreeStrikes_frameIsComplete() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new NumericPinCount(10));
        frame.roll(new NumericPinCount(10));
        frame.roll(new NumericPinCount(10));

        verify(scoreCard).complete(frame);
    }

    @Test
    public void givenRolledAStrike_noBonusRequested() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new NumericPinCount(10));

        verify(scoreCard, never()).requestBonusRolls(frame, 2);
    }

    @Test
    public void givenRolledASpare_noBonusRequested() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new LastFrameBehavior(), previousFrame, 3);

        frame.roll(new NumericPinCount(6));
        frame.roll(new NumericPinCount(4));

        verify(scoreCard, never()).requestBonusRolls(frame, 1);
    }
}
