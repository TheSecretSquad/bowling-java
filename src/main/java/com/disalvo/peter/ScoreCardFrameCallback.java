package com.disalvo.peter;

public interface ScoreCardFrameCallback {

    void frameComplete(Frame frame);

    void requestBonusForFrame(Frame frame);

    void bonusComplete(Frame frame);
}
