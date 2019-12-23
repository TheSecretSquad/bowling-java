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
        NumericPinCount pinCount = (NumericPinCount) o;
        return count == pinCount.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public String toString() {
        return "PinCount{" +
                "count=" + count +
                '}';
    }

    @Override
    protected FrameScore asFrameScore() {
        return new NumericFrameScore(count);
    }
}
