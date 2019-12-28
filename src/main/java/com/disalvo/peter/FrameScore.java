package com.disalvo.peter;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class FrameScore {

    public abstract FrameScore sumWith(FrameScore asFrameScore);

    public abstract FrameScore sumWith(PinCount pinCount);

    protected abstract FrameScore sumWith(int score);

    public abstract boolean sameAs(PinCount pinCount);

    public void print(Consumer<String> printAction) {
        printAction.accept(toString());
    }

    public static class EmptyFrameScore extends FrameScore {
        private final String score;

        public EmptyFrameScore() {
            score = "";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EmptyFrameScore that = (EmptyFrameScore) o;
            return score.equals(that.score);
        }

        @Override
        public int hashCode() {
            return Objects.hash(score);
        }

        @Override
        public String toString() {
            return score;
        }

        @Override
        public FrameScore sumWith(FrameScore other) {
            return other;
        }

        @Override
        public FrameScore sumWith(PinCount pinCount) {
            return pinCount.sumWith(new NumericFrameScore(0));
        }

        @Override
        protected FrameScore sumWith(int score) {
            return new NumericFrameScore(score);
        }

        @Override
        public boolean sameAs(PinCount pinCount) {
            return false;
        }
    }

    public static class NumericFrameScore extends FrameScore {
        private int score;

        public NumericFrameScore(int score) {
            validate(score);
            this.score = score;
        }

        private void validate(int score) {
            if(score < 0) {
                throw new InvalidFrameScoreException();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NumericFrameScore that = (NumericFrameScore) o;
            return score == that.score;
        }

        @Override
        public int hashCode() {
            return Objects.hash(score);
        }

        @Override
        public String toString() {
            return Integer.toString(score);
        }

        @Override
        public FrameScore sumWith(FrameScore other) {
            return other.sumWith(score);
        }

        @Override
        public FrameScore sumWith(PinCount pinCount) {
            return pinCount.sumWith(this);
        }

        @Override
        protected FrameScore sumWith(int score) {
            return new NumericFrameScore(this.score + score);
        }

        @Override
        public boolean sameAs(PinCount pinCount) {
            return equals(pinCount.asFrameScore());
        }
    }
}
