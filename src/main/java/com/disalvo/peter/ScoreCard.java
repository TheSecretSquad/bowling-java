package com.disalvo.peter;

import java.util.function.BiConsumer;

public interface ScoreCard {
    void roll(NumericPinCount pinCount);

    void frameScores(BiConsumer<FrameNumber, FrameScore> frameScoreConsumer);

    void printOn(ScoreCardPrintMedia printMedia);
}
