package com.disalvo.peter;

import com.disalvo.peter.ScoreCardPrintMedia3.PinCountPrintMedia;

import java.util.function.Consumer;

public abstract class PinCount {

    public FrameScore sumWith(FrameScore frameScore) {
        return frameScore.sumWith(asFrameScore());
    }

    public abstract PinCount sumWith(PinCount pinCount);

    protected abstract PinCount sumWith(int count);

    public abstract boolean isValidForMaximum(PinCount maximumPinCount);

    protected abstract FrameScore asFrameScore();

    protected abstract String printString();

    public abstract boolean equals(Object object);

    protected abstract boolean isGreaterThanOrEqualTo(int count);

    public abstract boolean sameAs(PinCount pinCount);

    public void print(Consumer<String> printAction, Runnable printEmptyAction) {
        String printValue = printString();
        if(printValue.isBlank())
            printEmptyAction.run();
        else
            printAction.accept(printValue);
    }

    public abstract void printOn(PinCountPrintMedia pinCountPrintMedia);
}
