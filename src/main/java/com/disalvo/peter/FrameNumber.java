package com.disalvo.peter;

import java.util.Objects;
import java.util.function.Consumer;

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

    public void toDo(FrameNumber toFrameNumber, Consumer<FrameNumber> frameNumberConsumer) {
        for(int i = this.number; i <= toFrameNumber.number; i++) {
            frameNumberConsumer.accept(new FrameNumber(i));
        }
    }

}
