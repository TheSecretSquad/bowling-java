package com.disalvo.peter;

public interface ScoreCardPrintMedia2 {
    void beginPrintFrame();

    void printFrameNumber(String frameNumber);

    void printFrameScore(String frameScore);

    void printRoll(String pinCount);

    void printEmptyRoll();

    void endPrintFrame();

}
