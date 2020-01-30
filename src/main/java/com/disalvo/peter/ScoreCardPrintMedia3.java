package com.disalvo.peter;

import java.util.function.Consumer;

public interface ScoreCardPrintMedia3 {

    void printFrame(Consumer<FramePrintMedia> framePrintMediaConsumer);

    interface FramePrintMedia {
        void printFrameNumber(Consumer<FrameNumberPrintMedia> frameNumberPrintMediaConsumer);

        void printFrameScore(Consumer<FrameScorePrintMedia> frameScorePrintMediaConsumer);

        void printRoll(Consumer<RollPrintMedia> rollPrintMediaConsumer);
    }

    interface FrameNumberPrintMedia
    {
        void printFrameNumber(int frameNumber);
    }

    interface FrameScorePrintMedia
    {
        void printFrameScore(int frameScore);
        void printEmptyFrameScore();
    }

    interface RollPrintMedia
    {
        void printRollPinCount(Consumer<PinCountPrintMedia> pinCountPrintMediaConsumer);
        void printStrike();
        void printSpare();
    }

    interface PinCountPrintMedia {
        void printPinCount(int pinCount);
        void printEmptyPinCount();
    }
}
