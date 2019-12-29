package com.disalvo.peter;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class Roll {

    public abstract void print(Consumer<String> printAction, Runnable printEmptyAction);

    public final static class PinCountRoll extends Roll {

        private final PinCount pinCount;

        public PinCountRoll(PinCount pinCount) {
            this.pinCount = pinCount;
        }

        @Override
        public String toString() {
            return "PinCountRoll{" +
                    "pinCount=" + pinCount +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PinCountRoll that = (PinCountRoll) o;
            return pinCount.equals(that.pinCount);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pinCount);
        }

        @Override
        public void print(Consumer<String> printAction, Runnable printEmptyAction) {
            pinCount.print(printAction, printEmptyAction);
        }
    }

    public final static class SymbolRoll extends Roll {

        private final String symbol;

        public SymbolRoll(String symbol) {
            if(symbol == null || symbol.isBlank())
                throw new InvalidSymbolRollException();
            this.symbol = symbol;
        }

        @Override
        public String toString() {
            return "SymbolRoll{" +
                    "symbol='" + symbol + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SymbolRoll that = (SymbolRoll) o;
            return symbol.equals(that.symbol);
        }

        @Override
        public int hashCode() {
            return Objects.hash(symbol);
        }

        @Override
        public void print(Consumer<String> printAction, Runnable printEmptyAction) {
            printAction.accept(symbol);
        }
    }
}
