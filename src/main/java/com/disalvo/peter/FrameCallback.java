package com.disalvo.peter;

public interface FrameCallback {

    void complete(Frame frame);

    void requestBonusRolls(Frame frame, int numberOfRolls);
}
