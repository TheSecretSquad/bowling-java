package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import org.junit.Test;

import java.util.function.BiConsumer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BowlingScoreCardTest {

    @Test
    public void startsWithTenEmptyFrames() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);
        verify(frameScoreConsumer).accept(new FrameNumber(1), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(2), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }

    @Test
    public void bowlingRegularFrame() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        bowlingScoreCard.roll(new NumericPinCount(1));
        bowlingScoreCard.roll(new NumericPinCount(1));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);
        verify(frameScoreConsumer).accept(new FrameNumber(1), new NumericFrameScore(2));
        verify(frameScoreConsumer).accept(new FrameNumber(2), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }

    @Test
    public void bowlingTwoRegularFrames_secondFrameIncludesScoreOfFirst() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        bowlingScoreCard.roll(new NumericPinCount(1));
        bowlingScoreCard.roll(new NumericPinCount(1));

        bowlingScoreCard.roll(new NumericPinCount(2));
        bowlingScoreCard.roll(new NumericPinCount(3));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new NumericFrameScore(2));
        verify(frameScoreConsumer).accept(new FrameNumber(2), new NumericFrameScore(7));
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }

    @Test
    public void bowlingSpareFrameScoreIsEmptyUntilMoreRolls() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        bowlingScoreCard.roll(new NumericPinCount(1));
        bowlingScoreCard.roll(new NumericPinCount(9));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(2), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }

    @Test
    public void bowlingSpare_getsNextRollAsBonus() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        bowlingScoreCard.roll(new NumericPinCount(1));
        bowlingScoreCard.roll(new NumericPinCount(9));

        bowlingScoreCard.roll(new NumericPinCount(2));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new NumericFrameScore(12));
        verify(frameScoreConsumer).accept(new FrameNumber(2), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }

    @Test
    public void bowlingStrike_getsNextTwoRollsAsBonus() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        bowlingScoreCard.roll(new NumericPinCount(10));

        bowlingScoreCard.roll(new NumericPinCount(1));
        bowlingScoreCard.roll(new NumericPinCount(1));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new NumericFrameScore(12));
        verify(frameScoreConsumer).accept(new FrameNumber(2), new NumericFrameScore(14));
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }

    @Test
    public void bowlingStrikeFrameScoreIsEmptyUntilMoreRolls() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        bowlingScoreCard.roll(new NumericPinCount(10));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(2), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }

    @Test
    public void bowlingStrikeFrameScoreIsEmptyEvenAfterNextRoll() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        bowlingScoreCard.roll(new NumericPinCount(10));
        bowlingScoreCard.roll(new NumericPinCount(1));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(2), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }

    @Test
    public void bowlingStrikeFrameScoreIsAvailableAfterSecondRoll() {
        BowlingScoreCard bowlingScoreCard = new BowlingScoreCard(mock(ScoreCardListener.class));

        bowlingScoreCard.roll(new NumericPinCount(10));

        bowlingScoreCard.roll(new NumericPinCount(1));
        bowlingScoreCard.roll(new NumericPinCount(1));

        BiConsumer<FrameNumber, FrameScore> frameScoreConsumer = mock(BiConsumer.class);
        bowlingScoreCard.frameScores(frameScoreConsumer);

        verify(frameScoreConsumer).accept(new FrameNumber(1), new NumericFrameScore(12));
        verify(frameScoreConsumer).accept(new FrameNumber(2), new NumericFrameScore(14));
        verify(frameScoreConsumer).accept(new FrameNumber(3), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(4), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(5), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(6), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(7), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(8), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(9), new EmptyFrameScore());
        verify(frameScoreConsumer).accept(new FrameNumber(10), new EmptyFrameScore());
    }
}
