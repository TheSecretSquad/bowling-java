package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.FrameScore.NumericFrameScore;
import net.bytebuddy.pool.TypePool.Empty;
import org.junit.Assert;
import org.junit.Test;

public class NumericFrameScoreTest {

    @Test(expected = InvalidFrameScoreException.class)
    public void mustBePositive() {
        new NumericFrameScore(-1);
    }

    @Test
    public void canBeZero() {
        new NumericFrameScore(0);
    }

    @Test
    public void zeroIsNotTheSameAsEmpty() {
        Assert.assertNotEquals(new EmptyFrameScore(), new NumericFrameScore(0));
    }

    @Test
    public void testSum() {
        FrameScore frameScore = new NumericFrameScore(4);

        Assert.assertEquals(new NumericFrameScore(8), frameScore.sumWith(new PinCount(4)));
        Assert.assertEquals(new NumericFrameScore(10), frameScore.sumWith(new NumericFrameScore(6)));
    }

    @Test
    public void testSumWithEmpty() {
        FrameScore frameScore = new NumericFrameScore(4);
        FrameScore emptyFrameScore = new EmptyFrameScore();

        Assert.assertEquals(new NumericFrameScore(4), frameScore.sumWith(emptyFrameScore));
        Assert.assertEquals(new NumericFrameScore(4), emptyFrameScore.sumWith(frameScore));
        Assert.assertEquals(new NumericFrameScore(4), emptyFrameScore.sumWith(new PinCount(4)));
    }
}
