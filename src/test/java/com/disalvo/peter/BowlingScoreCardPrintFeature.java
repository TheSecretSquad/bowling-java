package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class BowlingScoreCardPrintFeature {

    @Test
    public void emptyScoreCard() {
        BowlingScoreCard scoreCard = new BowlingScoreCard();

        ScoreCardPrintMedia printMedia = mock(ScoreCardPrintMedia.class);

        scoreCard.printOn(printMedia);

        InOrder inOrder = inOrder(printMedia);
        inOrder.verify(printMedia).printFrame(new FrameNumber(1), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(2), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(3), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(4), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(5), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(6), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(7), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(8), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(9), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(10), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount(), new EmptyPinCount()));
    }

    @Test
    public void scoreRegularGame() {
        BowlingScoreCard scoreCard = new BowlingScoreCard();

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

        ScoreCardPrintMedia printMedia = mock(ScoreCardPrintMedia.class);

        scoreCard.printOn(printMedia);

        InOrder inOrder = inOrder(printMedia);
        inOrder.verify(printMedia).printFrame(new FrameNumber(1), new NumericFrameScore(2), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(2), new NumericFrameScore(4), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(3), new NumericFrameScore(6), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(4), new NumericFrameScore(8), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(5), new NumericFrameScore(10), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(6), new NumericFrameScore(12), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(7), new NumericFrameScore(14), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(8), new NumericFrameScore(16), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(9), new NumericFrameScore(18), new Rolls(new NumericPinCount(1), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(10), new NumericFrameScore(20), new Rolls(new NumericPinCount(1), new NumericPinCount(1), new EmptyPinCount()));
    }

    @Test
    public void scoreSpares() {
        ScoreCard scoreCard = new BowlingScoreCard();

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

        ScoreCardPrintMedia scoreCardPrintMedia = mock(ScoreCardPrintMedia.class);

        scoreCard.printOn(scoreCardPrintMedia);

        InOrder inOrder = inOrder(scoreCardPrintMedia);
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(1), new NumericFrameScore(11), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(2), new NumericFrameScore(22), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(3), new NumericFrameScore(33), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(4), new NumericFrameScore(44), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(5), new NumericFrameScore(55), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(6), new NumericFrameScore(66), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(7), new NumericFrameScore(77), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(8), new NumericFrameScore(88), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(9), new NumericFrameScore(99), new Rolls(new NumericPinCount(1), new NumericPinCount(9)));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(10), new NumericFrameScore(110), new Rolls(new NumericPinCount(1), new NumericPinCount(9), new NumericPinCount(1)));
    }

    @Test
    public void scoreStrikes() {
        ScoreCard scoreCard = new BowlingScoreCard();

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

        ScoreCardPrintMedia scoreCardPrintMedia = mock(ScoreCardPrintMedia.class);

        scoreCard.printOn(scoreCardPrintMedia);

        InOrder inOrder = inOrder(scoreCardPrintMedia);
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(1), new NumericFrameScore(30), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(2), new NumericFrameScore(60), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(3), new NumericFrameScore(90), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(4), new NumericFrameScore(120), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(5), new NumericFrameScore(150), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(6), new NumericFrameScore(180), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(7), new NumericFrameScore(210), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(8), new NumericFrameScore(240), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(9), new NumericFrameScore(270), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(scoreCardPrintMedia).printFrame(new FrameNumber(10), new NumericFrameScore(300), new Rolls(new NumericPinCount(10), new NumericPinCount(10), new NumericPinCount(10)));
    }

    @Test
    public void randomGame() {
        BowlingScoreCard scoreCard = new BowlingScoreCard();

        scoreCard.roll(new NumericPinCount(3));
        scoreCard.roll(new NumericPinCount(6));

        scoreCard.roll(new NumericPinCount(3));
        scoreCard.roll(new NumericPinCount(6));

        scoreCard.roll(new NumericPinCount(8));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(3));
        scoreCard.roll(new NumericPinCount(0));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(10));

        scoreCard.roll(new NumericPinCount(7));
        scoreCard.roll(new NumericPinCount(3));

        scoreCard.roll(new NumericPinCount(4));
        scoreCard.roll(new NumericPinCount(0));

        scoreCard.roll(new NumericPinCount(9));
        scoreCard.roll(new NumericPinCount(0));

        scoreCard.roll(new NumericPinCount(10));
        scoreCard.roll(new NumericPinCount(8));
        scoreCard.roll(new NumericPinCount(1));

        ScoreCardPrintMedia printMedia = mock(ScoreCardPrintMedia.class);

        scoreCard.printOn(printMedia);

        InOrder inOrder = inOrder(printMedia);
        inOrder.verify(printMedia).printFrame(new FrameNumber(1), new NumericFrameScore(9), new Rolls(new NumericPinCount(3), new NumericPinCount(6)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(2), new NumericFrameScore(18), new Rolls(new NumericPinCount(3), new NumericPinCount(6)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(3), new NumericFrameScore(27), new Rolls(new NumericPinCount(8), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(4), new NumericFrameScore(30), new Rolls(new NumericPinCount(3), new NumericPinCount(0)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(5), new NumericFrameScore(57), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(6), new NumericFrameScore(77), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(7), new NumericFrameScore(91), new Rolls(new NumericPinCount(7),new NumericPinCount(3)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(8), new NumericFrameScore(95), new Rolls(new NumericPinCount(4), new NumericPinCount(0)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(9), new NumericFrameScore(104), new Rolls(new NumericPinCount(9), new NumericPinCount(0)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(10), new NumericFrameScore(123), new Rolls(new NumericPinCount(10), new NumericPinCount(8), new NumericPinCount(1)));
    }

    @Test
    public void partialGame() {
        BowlingScoreCard scoreCard = new BowlingScoreCard();

        scoreCard.roll(new NumericPinCount(3));
        scoreCard.roll(new NumericPinCount(6));

        scoreCard.roll(new NumericPinCount(3));
        scoreCard.roll(new NumericPinCount(6));

        scoreCard.roll(new NumericPinCount(8));
        scoreCard.roll(new NumericPinCount(1));

        scoreCard.roll(new NumericPinCount(3));
        scoreCard.roll(new NumericPinCount(0));

        scoreCard.roll(new NumericPinCount(10));

        ScoreCardPrintMedia printMedia = mock(ScoreCardPrintMedia.class);

        scoreCard.printOn(printMedia);

        InOrder inOrder = inOrder(printMedia);
        inOrder.verify(printMedia).printFrame(new FrameNumber(1), new NumericFrameScore(9), new Rolls(new NumericPinCount(3), new NumericPinCount(6)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(2), new NumericFrameScore(18), new Rolls(new NumericPinCount(3), new NumericPinCount(6)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(3), new NumericFrameScore(27), new Rolls(new NumericPinCount(8), new NumericPinCount(1)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(4), new NumericFrameScore(30), new Rolls(new NumericPinCount(3), new NumericPinCount(0)));
        inOrder.verify(printMedia).printFrame(new FrameNumber(5), new EmptyFrameScore(), new Rolls(new NumericPinCount(10), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(6), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(7), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(8), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(9), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount()));
        inOrder.verify(printMedia).printFrame(new FrameNumber(10), new EmptyFrameScore(), new Rolls(new EmptyPinCount(), new EmptyPinCount(), new EmptyPinCount()));
    }
}
