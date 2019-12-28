package com.disalvo.peter;

public abstract class PinCount {

    public FrameScore sumWith(FrameScore frameScore) {
        return frameScore.sumWith(asFrameScore());
    }

    protected abstract FrameScore asFrameScore();
}
