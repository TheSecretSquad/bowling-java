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
    protected FrameScore asFrameScore() {
        return new NumericFrameScore(count);
    }
}
