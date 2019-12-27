package com.disalvo.peter;

import org.junit.Test;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class BowlingScoreCardScoreCardUnitTest {

    private FrameFactory frameFactoryWithFirstLastNext(Frame first, Frame last, Frame ... nextFrames) {
        FrameFactory frameFactory = mock(FrameFactory.class);
        when(frameFactory.firstFrame(any(FrameNumber.class))).thenReturn(first);
        when(frameFactory.lastFrame(any(FrameNumber.class), any(Frame.class))).thenReturn(last);
        if(nextFrames.length > 0)
            when(frameFactory.nextFrame(any(FrameNumber.class), any(Frame.class))).thenReturn(nextFrames[0], Arrays.copyOfRange(nextFrames, 1, nextFrames.length));
        return frameFactory;
    }

    @Test
    public void whenRolling_rollsInFirstFrame() {
        Frame frame1 = mock(Frame.class);
        FrameFactory frameFactory = frameFactoryWithFirstLastNext(frame1, mock(Frame.class), new Frame[]{});

        BowlingScoreCard scoreCard = new BowlingScoreCard(frameFactory);

        scoreCard.roll(new NumericPinCount(1));

        verify(frame1).roll(new NumericPinCount(1));
    }

    @Test
    public void advancesToNextFrameWhenFrameCompletes() {
        Frame frame1 = mock(Frame.class);
        Frame frame2 = mock(Frame.class);
        FrameFactory frameFactory = frameFactoryWithFirstLastNext(frame1, mock(Frame.class), frame2);
        BowlingScoreCard scoreCard = new BowlingScoreCard(frameFactory);

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.completeFrame();
        scoreCard.roll(new NumericPinCount(2));

        verify(frame1, times(1)).roll(new NumericPinCount(1));
        verify(frame2, times(1)).roll(new NumericPinCount(2));
    }

    @Test
    public void rollsInLastFrameWhenLastFrameReached() {
        Frame frame1 = mock(Frame.class);
        Frame frame2 = mock(Frame.class);
        FrameFactory frameFactory = frameFactoryWithFirstLastNext(frame1, frame2, new Frame[]{});
        BowlingScoreCard scoreCard = new BowlingScoreCard(frameFactory, new FrameNumber(2));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.completeFrame();
        scoreCard.roll(new NumericPinCount(2));

        verify(frame1, times(1)).roll(new NumericPinCount(1));
        verify(frame2, times(1)).roll(new NumericPinCount(2));
    }

    @Test
    public void whenBonusRollsRequested_frameReceivesRequestedBonusRolls() {
        Frame frame1 = mock(Frame.class);
        Frame frame2 = mock(Frame.class);
        FrameFactory frameFactory = frameFactoryWithFirstLastNext(frame1, frame2, new Frame[]{});
        BowlingScoreCard scoreCard = new BowlingScoreCard(frameFactory, new FrameNumber(2));

        scoreCard.requestBonusRolls(frame1, 2);
        scoreCard.completeFrame();
        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(2));
        scoreCard.roll(new NumericPinCount(3));

        verify(frame1, times(1)).bonusRoll(new NumericPinCount(1));
        verify(frame1, times(1)).bonusRoll(new NumericPinCount(2));
        verify(frame1, never()).bonusRoll(new NumericPinCount(3));
    }
}
