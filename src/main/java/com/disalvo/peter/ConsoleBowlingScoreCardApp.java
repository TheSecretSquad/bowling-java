package com.disalvo.peter;

public class ConsoleBowlingScoreCardApp {
    private final ScoreCard scoreCard;
    private final Console console;

    public ConsoleBowlingScoreCardApp(ScoreCard scoreCard, Console console) {
        this.scoreCard = scoreCard;
        this.console = console;
    }

    public void roll(int pinCount) {
        scoreCard.roll(new NumericPinCount(pinCount));
        StringMedia stringMedia = new StringMedia();
        scoreCard.printOn(stringMedia);
        stringMedia.printOnConsole(console);
    }
    
    private static class StringMedia implements ScoreCardPrintMedia {

        private final StringBuilder topLine;
        private final StringBuilder rollsLine;
        private final StringBuilder rollsBottomLine;
        private final StringBuilder frameScoreLine;
        private final StringBuilder endLine;
        private boolean isFirstRoll;

        private StringMedia() {
            topLine = new StringBuilder();
            rollsLine = new StringBuilder();
            frameScoreLine = new StringBuilder();
            rollsBottomLine = new StringBuilder();
            endLine = new StringBuilder();
        }

        @Override
        public void printFrame(FrameNumber frameNumber, FrameScore frameScore, Rolls rolls) {
            isFirstRoll = true;
            writeRollString(rolls);
            writeFrameScore(frameScore);
            writeFrameEnd();
        }

        private void writeFrameScore(FrameScore frameScore) {
            final int widthOfFrame = 9;
            String stringValue = frameScore.toString();
            int spaceAvailableForPadding =  widthOfFrame - stringValue.length();
            int leftPad = (int)Math.ceil(spaceAvailableForPadding / (double)2) + stringValue.length();
            int rightPad = widthOfFrame - leftPad;
            frameScoreLine.append(String.format("│%" + leftPad + "s%" + rightPad + "s│", stringValue, ""));
        }

        private void writeFrameEnd() {
            topLine.append("┐");
            rollsLine.append("│");
            rollsBottomLine.append("┤");
            endLine.append("└─────────┘");
        }

        private void writeRollString(Rolls rolls) {
            int adjustedSpacingForNumberOfRolls = rolls.count() == 2 ? 5 : 3;
            topLine.append("┌");
            topLine.append("─".repeat(adjustedSpacingForNumberOfRolls));
            rollsLine.append("│");
            rollsLine.append(" ".repeat(adjustedSpacingForNumberOfRolls));
            rollsBottomLine.append("│");
            rollsBottomLine.append(" ".repeat(adjustedSpacingForNumberOfRolls));
            rolls.each(pinCount -> writeRollBoxFor(pinCount));
        }

        private void writeRollBoxFor(PinCount pinCount) {
            topLine.append("┬");
            topLine.append("─");
            rollsLine.append("│");
            rollsBottomLine.append(isFirstRoll ? "└" : "┴");
            rollsBottomLine.append("─");
            rollsLine.append(adjustForEmpty(pinCount));
            isFirstRoll = false;
        }

        private String adjustForEmpty(PinCount pinCount) {
            String stringValue = pinCount.toString();
            return stringValue.equals("") ? " " : stringValue;
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
