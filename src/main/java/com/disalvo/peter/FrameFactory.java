package com.disalvo.peter;

public interface FrameFactory {
    Frame nextFrame(FrameNumber frameNumber, Frame previousFrame);

    Frame lastFrame(FrameNumber frameNumber, Frame previousFrame);

    Frame firstFrame(FrameNumber frameNumber);
}
