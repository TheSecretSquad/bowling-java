package com.disalvo.peter;

public interface ScoreCardFrameCallback {

    void complete(Frame frame);

    void requestBonusRolls(Frame frame, int numberOfRolls);
}
