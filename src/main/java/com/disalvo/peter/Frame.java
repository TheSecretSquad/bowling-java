package com.disalvo.peter;

import java.util.function.Consumer;

public interface Frame {

    void roll(PinCount pinCount);

    void bonusRoll(PinCount pinCount);

    void score(Consumer<FrameScore> frameScoreConsumer);

    void complete();

    void bonusComplete();

    void pendingBonus();
}
