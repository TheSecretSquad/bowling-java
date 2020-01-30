package com.disalvo.peter;

import com.disalvo.peter.ScoreCardPrintMedia3.FrameScorePrintMedia;

import java.util.Objects;
import java.util.function.Consumer;

public abstract class FrameScore {

    public abstract FrameScore sumWith(FrameScore asFrameScore);

    public abstract FrameScore sumWith(PinCount pinCount);

    protected abstract FrameScore sumWith(int score);

    public void print(Consumer<String> printAction) {
        printAction.accept(printString());
    }

    protected abstract String printString();

    protected abstract void printOn(FrameScorePrintMedia printMedia);

    public static final class EmptyFrameScore extends FrameScore {
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
            return "EmptyFrameScore{" +
                    "score='" + score + '\'' +
                    '}';
        }

        @Override
        public FrameScore sumWith(FrameScore other) {
            return this;
        }

        @Override
        public FrameScore sumWith(PinCount pinCount) {
            return this;
        }

        @Override
        protected FrameScore sumWith(int score) {
            return this;
        }

        @Override
        protected String printString() {
            return score;
        }

        @Override
        protected void printOn(FrameScorePrintMedia printMedia) {
            printMedia.printEmptyFrameScore();
        }
    }

    public static final class NumericFrameScore extends FrameScore {
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
            return "NumericFrameScore{" +
                    "score=" + score +
                    '}';
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
        protected String printString() {
            return Integer.toString(score);
        }

        @Override
        protected void printOn(FrameScorePrintMedia printMedia) {
            printMedia.printFrameScore(score);
        }
    }
}
