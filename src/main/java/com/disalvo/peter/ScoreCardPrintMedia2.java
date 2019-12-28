package com.disalvo.peter;

public interface ScoreCardPrintMedia2 {

    void beginPrintFrame();

    void printFrameNumber(FrameNumber frameNumber);

    void printFrameScore(FrameScore frameScore);

    void printRoll(PinCount pinCount);

    void endPrintFrame();
}
