package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;
import com.disalvo.peter.ScoreCardPrintMedia3.PinCountPrintMedia;

import java.util.Objects;

public final class EmptyPinCount extends PinCount {
    private final String count;

    public EmptyPinCount() {
        count = "";
    }

    @Override
    public PinCount sumWith(PinCount pinCount) {
        return pinCount.sumWith(0);
    }

    @Override
    protected PinCount sumWith(int count) {
        return new NumericPinCount(count);
    }

    @Override
    public boolean isValidForMaximum(PinCount maximumPinCount) {
        return maximumPinCount.isGreaterThanOrEqualTo(0);
    }

    @Override
    protected FrameScore asFrameScore() {
        return new EmptyFrameScore();
    }

    @Override
    protected String printString() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmptyPinCount that = (EmptyPinCount) o;
        return count.equals(that.count);
    }

    @Override
    protected boolean isGreaterThanOrEqualTo(int count) {
        return 0 >= count;
    }

    @Override
    public boolean sameAs(PinCount pinCount) {
        return equals(pinCount);
    }

    @Override
    public void printOn(PinCountPrintMedia pinCountPrintMedia) {
        pinCountPrintMedia.printEmptyPinCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public String toString() {
        return "EmptyPinCount{" +
                "count='" + count + '\'' +
                '}';
    }
}
