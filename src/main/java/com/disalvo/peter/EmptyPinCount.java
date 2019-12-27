package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;

import java.util.Objects;

public final class EmptyPinCount extends PinCount {
    private final String count;

    public EmptyPinCount() {
        count = "";
    }

    @Override
    protected FrameScore asFrameScore() {
        return new EmptyFrameScore();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmptyPinCount that = (EmptyPinCount) o;
        return count.equals(that.count);
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
