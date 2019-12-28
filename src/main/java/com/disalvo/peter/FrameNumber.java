package com.disalvo.peter;

import java.util.Objects;

public class FrameNumber {
    private int number;

    public FrameNumber(int number) {
        validate(number);
        this.number = number;
    }

    private void validate(int number) {
        if(number <= 0)
            throw new InvalidFrameNumberException();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FrameNumber that = (FrameNumber) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "FrameNumber{" +
                "number=" + number +
                '}';
    }

    public FrameNumber advanced() {
        return new FrameNumber(number + 1);
    }
}
