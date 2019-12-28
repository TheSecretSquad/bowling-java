package com.disalvo.peter;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConsoleBowlingScoreCardApp {
    private final ScoreCard scoreCard;
    private final Console console;

    public ConsoleBowlingScoreCardApp(ScoreCard scoreCard, Console console) {
        this.scoreCard = scoreCard;
        this.console = console;
    }

    public void roll(int pinCount) {
        scoreCard.roll(new NumericPinCount(pinCount));
        StringMedia2 stringMedia = new StringMedia2();
        scoreCard.printOn(stringMedia);
        stringMedia.printOnConsole(console);
    }

    private static class StringMedia2 implements ScoreCardPrintMedia2 {

        private final ArrayList<String> rolls;
        private String frameScore;
        private final StringBuilder topLine;
        private final StringBuilder rollsLine;
        private final StringBuilder rollsBottomLine;
        private final StringBuilder frameScoreLine;
        private final StringBuilder endLine;

        private StringMedia2() {
            rolls = new ArrayList<>();
            topLine = new StringBuilder();
            rollsLine = new StringBuilder();
            frameScoreLine = new StringBuilder();
            rollsBottomLine = new StringBuilder();
            endLine = new StringBuilder();
        }

        @Override
        public void beginPrintFrame() {
            rolls.clear();
        }

        @Override
        public void printFrameNumber(String frameNumber) {

        }

        @Override
        public void printFrameScore(String frameScore) {
            this.frameScore = frameScore;
        }

        @Override
        public void printRoll(String pinCount) {
            rolls.add(pinCount);
        }

        @Override
        public void printEmptyRoll() {
            rolls.add(" ");
        }

        @Override
        public void endPrintFrame() {
            writeRolls();
            writeFrameScore();
            writeFrameEnd();
        }

        private void writeRolls() {
            int adjustedSpacingForNumberOfRolls = rolls.size() == 2 ? 5 : 3;
            topLine.append("┌");
            topLine.append("─".repeat(adjustedSpacingForNumberOfRolls));
            rollsLine.append("│");
            rollsLine.append(" ".repeat(adjustedSpacingForNumberOfRolls));
            rollsBottomLine.append("│");
            rollsBottomLine.append(" ".repeat(adjustedSpacingForNumberOfRolls));
            writeRollBoxes();
        }

        private void writeRollBoxes() {
            boolean isFirstRoll = true;
            for(String pinCount : rolls) {
                writeRollBoxFor(pinCount, isFirstRoll);
                isFirstRoll = false;
            }
        }

        private void writeRollBoxFor(String pinCount, boolean isFirstRoll) {
            topLine.append("┬");
            topLine.append("─");
            rollsLine.append("│");
            rollsBottomLine.append(isFirstRoll ? "└" : "┴");
            rollsBottomLine.append("─");
            rollsLine.append(pinCount);
        }

        private void writeFrameScore() {
            frameScoreLine.append(approximateCenterFormattedString());
        }

        private String approximateCenterFormattedString() {
            final int widthOfFrame = 9;
            int spaceAvailableForPadding =  widthOfFrame - frameScore.length();
            int leftPad = (int)Math.ceil(spaceAvailableForPadding / (double)2) + frameScore.length();
            int rightPad = widthOfFrame - leftPad;
            return String.format("│%" + leftPad + "s%" + rightPad + "s│", frameScore, "");
        }

        private void writeFrameEnd() {
            topLine.append("┐");
            rollsLine.append("│");
            rollsBottomLine.append("┤");
            endLine.append("└─────────┘");
        }

        public void printOnConsole(Console console) {
            console.printLine(topLine.toString());
            console.printLine(rollsLine.toString());
            console.printLine(rollsBottomLine.toString());
            console.printLine(frameScoreLine.toString());
            console.printLine(endLine.toString());
        }
    }

    private static class StringMedia implements ScoreCardPrintMedia {

        private final StringBuilder topLine;
        private final StringBuilder rollsLine;
        private final StringBuilder rollsBottomLine;
        private final StringBuilder frameScoreLine;
        private final StringBuilder endLine;

        private StringMedia() {
            topLine = new StringBuilder();
            rollsLine = new StringBuilder();
            frameScoreLine = new StringBuilder();
            rollsBottomLine = new StringBuilder();
            endLine = new StringBuilder();
        }

        @Override
        public void printFrame(FrameNumber frameNumber, FrameScore frameScore, Rolls rolls) {
            writeRolls(rolls);
            writeFrameScore(frameScore);
            writeFrameEnd();
        }

        private void writeFrameScore(FrameScore frameScore) {
            frameScoreLine.append(approximateCenterFormattedString(frameScore));
        }

        private String approximateCenterFormattedString(FrameScore frameScore) {
            final int widthOfFrame = 9;
            String stringValue = frameScore.toString();
            int spaceAvailableForPadding =  widthOfFrame - stringValue.length();
            int leftPad = (int)Math.ceil(spaceAvailableForPadding / (double)2) + stringValue.length();
            int rightPad = widthOfFrame - leftPad;
            return String.format("│%" + leftPad + "s%" + rightPad + "s│", frameScore, "");
        }

        private void writeFrameEnd() {
            topLine.append("┐");
            rollsLine.append("│");
            rollsBottomLine.append("┤");
            endLine.append("└─────────┘");
        }

        private void writeRolls(Rolls rolls) {
            int adjustedSpacingForNumberOfRolls = rolls.count() == 2 ? 5 : 3;
            topLine.append("┌");
            topLine.append("─".repeat(adjustedSpacingForNumberOfRolls));
            rollsLine.append("│");
            rollsLine.append(" ".repeat(adjustedSpacingForNumberOfRolls));
            rollsBottomLine.append("│");
            rollsBottomLine.append(" ".repeat(adjustedSpacingForNumberOfRolls));
            writeRollBoxes(rolls);
        }

        private void writeRollBoxes(Rolls rolls) {
            AtomicBoolean isFirstRoll = new AtomicBoolean(true);
            rolls.each(pinCount -> {writeRollBoxFor(pinCount, isFirstRoll.get()); isFirstRoll.set(false);});
        }

        private void writeRollBoxFor(PinCount pinCount, boolean isFirstRoll) {
            topLine.append("┬");
            topLine.append("─");
            rollsLine.append("│");
            rollsBottomLine.append(isFirstRoll ? "└" : "┴");
            rollsBottomLine.append("─");
            rollsLine.append(adjustForEmpty(pinCount));
        }

        private String adjustForEmpty(PinCount pinCount) {
            String stringValue = pinCount.toString();
            return stringValue.isBlank() ? " " : stringValue;
        }

        public void printOnConsole(Console console) {
            console.printLine(topLine.toString());
            console.printLine(rollsLine.toString());
            console.printLine(rollsBottomLine.toString());
            console.printLine(frameScoreLine.toString());
            console.printLine(endLine.toString());
        }
    }
}
