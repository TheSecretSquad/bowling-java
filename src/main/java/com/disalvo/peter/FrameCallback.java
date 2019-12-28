package com.disalvo.peter;

public interface FrameCallback {

    void completeFrame();

    void requestBonusRolls(Frame frame, int numberOfRolls);
}
