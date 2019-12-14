package com.disalvo.peter;

import java.util.function.BiConsumer;

public interface ScoreCard {
    void roll(PinCount pinCount);

    void frameScores(BiConsumer<FrameNumber, FrameScore> frameScoreConsumer);
}
