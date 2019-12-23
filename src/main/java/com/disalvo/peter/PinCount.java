package com.disalvo.peter;

import com.disalvo.peter.FrameScore.NumericFrameScore;

public abstract class PinCount {

    public FrameScore sumWith(FrameScore frameScore) {
        return frameScore.sumWith(asFrameScore());
    }

    protected abstract FrameScore asFrameScore();
}
