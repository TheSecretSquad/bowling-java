package com.disalvo.peter;

import java.util.function.Consumer;

public interface Frame {

    void roll(NumericPinCount pinCount);

    void bonusRoll(NumericPinCount pinCount);

    void bonusComplete();

    void score(Consumer<FrameScore> frameScoreConsumer);

    void printOn(FramePrintMedia printMedia);
}
