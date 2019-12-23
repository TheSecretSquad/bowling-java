package com.disalvo.peter;

import com.disalvo.peter.FrameScore.EmptyFrameScore;

public final class EmptyPinCount extends PinCount {
    @Override
    protected FrameScore asFrameScore() {
        return new EmptyFrameScore();
    }
}
