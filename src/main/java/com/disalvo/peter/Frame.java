package com.disalvo.peter;

import java.util.function.Consumer;

public interface Frame {

    void roll(PinCount pinCount);

    void score(Consumer<FrameScore> frameScoreConsumer);

    void bonusRoll(PinCount pinCount);
}
