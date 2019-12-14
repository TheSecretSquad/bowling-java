package com.disalvo.peter;

import com.disalvo.peter.FrameScore.NumericFrameScore;

import java.util.Objects;

public class PinCount {
    private int count;

    public PinCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PinCount pinCount = (PinCount) o;
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

    FrameScore sumWith(FrameScore frameScore) {
        return frameScore.sumWith(asFrameScore());
    }

    FrameScore asFrameScore() {
        return new NumericFrameScore(count);
    }
}
