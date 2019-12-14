package com.disalvo.peter;

public interface ScoreCardFrameCallback {

    void completeAutomatically(Frame frame);
    
    void strike(Frame frame);

    void spare(Frame frame);
}
