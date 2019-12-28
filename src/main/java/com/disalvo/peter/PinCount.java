package com.disalvo.peter;

import java.util.function.Consumer;

public abstract class PinCount {

    public FrameScore sumWith(FrameScore frameScore) {
        return frameScore.sumWith(asFrameScore());
    }

    protected abstract FrameScore asFrameScore();

    public void print(Consumer<String> printAction, Runnable printEmptyAction) {
        String printValue = toString();
        if(printValue.isBlank())
            printEmptyAction.run();
        else
            printAction.accept(printValue);
    }
}
