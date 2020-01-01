package com.disalvo.peter;

import com.disalvo.peter.FrameScore.NumericFrameScore;

import java.util.Objects;

public final class NumericPinCount extends PinCount {
    private int count;

    public NumericPinCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumericPinCount that = (NumericPinCount) o;
        return count == that.count;
    }

    @Override
    protected boolean isGreaterThanOrEqualTo(int count) {
        return this.count >= count;
    }

    @Override
    public boolean sameAs(PinCount pinCount) {
        return equals(pinCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public String toString() {
        return "NumericPinCount{" +
                "count=" + count +
                '}';
    }

    @Override
    public PinCount sumWith(PinCount pinCount) {
        return pinCount.sumWith(count);
    }

    @Override
    protected PinCount sumWith(int count) {
        return new NumericPinCount(this.count + count);
    }

    @Override
    public boolean isValidForMaximum(PinCount maximumPinCount) {
        return maximumPinCount.isGreaterThanOrEqualTo(count);
    }

    @Override
    protected FrameScore asFrameScore() {
        return new NumericFrameScore(count);
    }

    @Override
    protected String printString() {
        return Integer.toString(count);
    }
}
