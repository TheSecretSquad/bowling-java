package com.disalvo.peter;

import org.junit.Assert;
import org.junit.Test;

public class FrameNumberTest {
    @Test(expected = InvalidFrameNumberException.class)
    public void mustBePositive() {
        new FrameNumber(-1);
    }

    @Test(expected = InvalidFrameNumberException.class)
    public void cannotBeZero() {
        new FrameNumber(0);
    }

    @Test
    public void advancingTheFrameNumberReturnsNextFrameNumber() {
        FrameNumber frameNumber = new FrameNumber(1);

        Assert.assertEquals(new FrameNumber(2), frameNumber.advanced());
    }

    @Test
    public void equality() {
        Assert.assertEquals(new FrameNumber(2), new FrameNumber(2));
        Assert.assertEquals(new FrameNumber(3), new FrameNumber(3));
        Assert.assertNotEquals(new FrameNumber(1), new FrameNumber(3));
    }
}
