package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import org.junit.Test;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

public class NormalFrameTest {

    private FrameNumber anyFrameNumber() {
        return new FrameNumber(1);
    }

    @Test(expected = TooManyRollsException.class)
    public void givenRolledTwice_cantRollAnymore() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);

        frame.roll(new NumericPinCount(1));
        frame.roll(new NumericPinCount(1));
        frame.roll(new NumericPinCount(1));
    }

    @Test
    public void givenAFrameIsNotComplete_whenPrinting_frameScoreIsEmpty() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);

        frame.roll(new NumericPinCount(1));

        ScoreCardPrintMedia2 scoreCardPrintMedia = mock(ScoreCardPrintMedia2.class);
        frame.printOn(scoreCardPrintMedia);

        verify(scoreCardPrintMedia).printFrameScore(new EmptyFrameScore());
    }

    @Test
    public void givenTwoRollsMade_frameIsComplete() {
        FrameCallback frameCallback = mock(FrameCallback.class);
        Frame frame = new DefaultFrame(frameCallback, anyFrameNumber(), new NormalFrameBehavior(), 2);

        frame.roll(new NumericPinCount(1));
        frame.roll(new NumericPinCount(1));

        verify(frameCallback).completeFrame();
    }

    @Test
    public void givenAFrameIsComplete_whenPrinting_printsScore() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);

        frame.roll(new NumericPinCount(1));
        frame.roll(new NumericPinCount(1));

        ScoreCardPrintMedia2 scoreCardPrintMedia = mock(ScoreCardPrintMedia2.class);
        frame.printOn(scoreCardPrintMedia);

        verify(scoreCardPrintMedia).printFrameScore(new NumericFrameScore(2));
    }

    @Test
    public void givenRolledASpare_reportsSpare() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);

        frame.roll(new NumericPinCount(1));
        frame.roll(new NumericPinCount(9));

        verify(scoreCard).requestBonusRolls(frame, 1);
    }

    @Test
    public void givenRolledAStrike_reportsStrike() {
        FrameCallback frameCallback = mock(FrameCallback.class);
        Frame frame = new DefaultFrame(frameCallback, anyFrameNumber(), new NormalFrameBehavior(), 2);

        frame.roll(new NumericPinCount(10));

        verify(frameCallback).requestBonusRolls(frame, 2);
    }

    @Test
    public void givenFrameIsComplete_andHasAPendingBonus_whenPrinting_frameScoreIsEmptyUntilBonusIsComplete() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);

        frame.roll(new NumericPinCount(10));

        ScoreCardPrintMedia2 scoreCardPrintMedia = mock(ScoreCardPrintMedia2.class);
        frame.printOn(scoreCardPrintMedia);

        verify(scoreCardPrintMedia).printFrameScore(new EmptyFrameScore());

        frame.bonusComplete();

        frame.printOn(scoreCardPrintMedia);
        verify(scoreCardPrintMedia).printFrameScore(new NumericFrameScore(10));
    }

    @Test
    public void givenPreviousFrame_whenPrinting_scoreIsSumOfFrameAndPreviousFrame() {
        FrameCallback scoreCard = mock(FrameCallback.class);
        Frame previousFrame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), 2);

        previousFrame.roll(new NumericPinCount(1));
        previousFrame.roll(new NumericPinCount(1));

        Frame frame = new DefaultFrame(scoreCard, anyFrameNumber(), new NormalFrameBehavior(), previousFrame, 2);

        frame.roll(new NumericPinCount(2));
        frame.roll(new NumericPinCount(2));

        ScoreCardPrintMedia2 scoreCardPrintMedia = mock(ScoreCardPrintMedia2.class);
        frame.printOn(scoreCardPrintMedia);

        verify(scoreCardPrintMedia).printFrameScore(new NumericFrameScore(6));
    }
}
