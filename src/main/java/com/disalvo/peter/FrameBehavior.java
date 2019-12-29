package com.disalvo.peter;

public interface FrameBehavior {

    void strike(DefaultFrame frame);
    void spare(DefaultFrame frame);
    void validateRoll(DefaultFrame defaultFrame, NumericPinCount pinCount);
}
