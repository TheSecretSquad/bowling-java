package com.disalvo.peter;

public interface Frame {

    void roll(NumericPinCount pinCount);

    void bonusRoll(NumericPinCount pinCount);

    void bonusComplete();

    FrameScore score();

    void printOn(ScoreCardPrintMedia printMedia);

    void printOn(ScoreCardPrintMedia2 printMedia);
}
