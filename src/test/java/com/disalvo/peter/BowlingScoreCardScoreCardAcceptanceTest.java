package com.disalvo.peter;

import com.disalvo.peter.FrameScore.NumericFrameScore;
import org.junit.Test;

import java.util.function.BiConsumer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BowlingScoreCardScoreCardAcceptanceTest {

    @Test
    public void scoreRegularGame() {
        ScoreCard scoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);

        scoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new NumericFrameScore(2));
        verify(frameScoreConsumer).accept(new FrameNumber(2), new NumericFrameScore(4));
        verify(frameScoreConsumer).accept(new FrameNumber(3), new NumericFrameScore(6));
        verify(frameScoreConsumer).accept(new FrameNumber(4), new NumericFrameScore(8));
        verify(frameScoreConsumer).accept(new FrameNumber(5), new NumericFrameScore(10));
        verify(frameScoreConsumer).accept(new FrameNumber(6), new NumericFrameScore(12));
        verify(frameScoreConsumer).accept(new FrameNumber(7), new NumericFrameScore(14));
        verify(frameScoreConsumer).accept(new FrameNumber(8), new NumericFrameScore(16));
        verify(frameScoreConsumer).accept(new FrameNumber(9), new NumericFrameScore(18));
        verify(frameScoreConsumer).accept(new FrameNumber(10), new NumericFrameScore(20));
    }

    @Test
    public void scoreSpares() {
        ScoreCard scoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(9));
        scoreCard.roll(new NumericPinCount(1));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);

        scoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new NumericFrameScore(11));
        verify(frameScoreConsumer).accept(new FrameNumber(2), new NumericFrameScore(22));
        verify(frameScoreConsumer).accept(new FrameNumber(3), new NumericFrameScore(33));
        verify(frameScoreConsumer).accept(new FrameNumber(4), new NumericFrameScore(44));
        verify(frameScoreConsumer).accept(new FrameNumber(5), new NumericFrameScore(55));
        verify(frameScoreConsumer).accept(new FrameNumber(6), new NumericFrameScore(66));
        verify(frameScoreConsumer).accept(new FrameNumber(7), new NumericFrameScore(77));
        verify(frameScoreConsumer).accept(new FrameNumber(8), new NumericFrameScore(88));
        verify(frameScoreConsumer).accept(new FrameNumber(9), new NumericFrameScore(99));
        verify(frameScoreConsumer).accept(new FrameNumber(10), new NumericFrameScore(110));
    }

    @Test
    public void scoreStrikes() {
        ScoreCard scoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));
        scoreCard.roll(new NumericPinCount(10));
        scoreCard.roll(new NumericPinCount(10));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);

        scoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new NumericFrameScore(30));
        verify(frameScoreConsumer).accept(new FrameNumber(2), new NumericFrameScore(60));
        verify(frameScoreConsumer).accept(new FrameNumber(3), new NumericFrameScore(90));
        verify(frameScoreConsumer).accept(new FrameNumber(4), new NumericFrameScore(120));
        verify(frameScoreConsumer).accept(new FrameNumber(5), new NumericFrameScore(150));
        verify(frameScoreConsumer).accept(new FrameNumber(6), new NumericFrameScore(180));
        verify(frameScoreConsumer).accept(new FrameNumber(7), new NumericFrameScore(210));
        verify(frameScoreConsumer).accept(new FrameNumber(8), new NumericFrameScore(240));
        verify(frameScoreConsumer).accept(new FrameNumber(9), new NumericFrameScore(270));
        verify(frameScoreConsumer).accept(new FrameNumber(10), new NumericFrameScore(300));
    }
}
