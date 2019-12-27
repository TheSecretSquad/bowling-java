package com.disalvo.peter;

import java.util.Arrays;
import java.util.function.Consumer;

public final class Rolls {

    private static PinCount[] makeArrayWhereNullEntriesAreEmptyPinCounts(PinCount[] rolls) {
        return Arrays.stream(rolls).map(pinCount -> pinCount == null ? new EmptyPinCount() : pinCount).toArray(PinCount[]::new);
    }

    private final PinCount[] rolls;

    public Rolls(PinCount... rolls) {
        this.rolls = makeArrayWhereNullEntriesAreEmptyPinCounts(rolls);
    }

    public void each(Consumer<PinCount> pinCountConsumer) {
        for(PinCount pinCount : rolls) {
            pinCountConsumer.accept(pinCount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rolls rolls1 = (Rolls) o;
        return Arrays.equals(rolls, rolls1.rolls);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rolls);
    }

    @Override
    public String toString() {
        return "Rolls{" +
                "rolls=" + Arrays.toString(rolls) +
                '}';
    }
}
