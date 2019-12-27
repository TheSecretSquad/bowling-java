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

        @Override
        public void printFrame(FrameNumber frameNumber, FrameScore numericFrameScore, Rolls rolls) {
            
        }

        public void printOnConsole(Console console) {
        }
    }
}
