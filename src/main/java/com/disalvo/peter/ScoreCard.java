package com.disalvo.peter;

public interface ScoreCard {
    void roll(NumericPinCount pinCount);

    void printOn(ScoreCardPrintMedia printMedia);

    void printOn(ScoreCardPrintMedia2 printMedia);

    void printOn(ScoreCardPrintMedia3 printMedia);
}
