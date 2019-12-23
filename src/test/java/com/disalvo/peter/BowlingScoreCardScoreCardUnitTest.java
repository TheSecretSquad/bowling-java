package com.disalvo.peter;

import org.junit.Test;

import static org.mockito.Mockito.*;

public class BowlingScoreCardScoreCardUnitTest {

    @Test
    public void whenRolling_reportsRoll() {
        ScoreCardListener scoreCardListener = mock(ScoreCardListener.class);
        BowlingScoreCard scoreCard = new BowlingScoreCard(scoreCardListener);

        scoreCard.roll(new NumericPinCount(1));

        verify(scoreCardListener).rolled(scoreCard);
    }

    @Test
    public void whenRegularRolls_shouldHaveAdvancedTheFrameAfterSecondRoll() {
        ScoreCardListener scoreCardListener = mock(ScoreCardListener.class);
        BowlingScoreCard scoreCard = new BowlingScoreCard(scoreCardListener);

        scoreCard.roll(new NumericPinCount(1));
        scoreCard.roll(new NumericPinCount(1));

        verify(scoreCardListener).advancedFrame(scoreCard);
    }
}
