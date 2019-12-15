package com.disalvo.peter;

public interface ScoreCardFrameCallback {

    void complete(Frame frame);
    
    void strike(Frame frame);

    void spare(Frame frame);
}
