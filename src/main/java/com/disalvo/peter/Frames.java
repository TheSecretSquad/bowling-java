package com.disalvo.peter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Frames {

    private final List<Frame> frameList;
    private final FrameNumber lastFrameNumber;
    private final FrameFactory frameFactory;
    private int currentFrameIndex;

    public Frames(FrameNumber lastFrameNumber, FrameFactory frameFactory) {
        this.frameFactory = frameFactory;
        this.frameList = new ArrayList<>();
        this.lastFrameNumber = lastFrameNumber;
        this.currentFrameIndex = 0;
        setupFrames();
    }

    private void setupFrames() {
        Frame first = firstFrame();
        addFrame(first);
        setupFrame(new FrameNumber(2), first);
    }

    private void setupFrame(FrameNumber frameNumber, Frame previousFrame) {
        Frame nextFrame = nextFrame(previousFrame, frameNumber);
        addFrame(nextFrame);
        if(frameNumber.equals(lastFrameNumber))
            return;
        setupFrame(frameNumber.advanced(), nextFrame);
    }

    private Frame firstFrame() {
        return frameFactory.firstFrame();
    }

    public void current(Consumer<Frame> frameConsumer) {
        frameConsumer.accept(currentFrame());
    }

    private Frame currentFrame() {
        return frameList.get(currentFrameIndex);
    }

    public void advance() {
        ++currentFrameIndex;
    }

    private void addFrame(Frame frame) {
        frameList.add(frame);
    }

    private Frame nextFrame(Frame previousFrame, FrameNumber frameNumber) {
        return !isLastFrameNumber(frameNumber) ? normalFrame(previousFrame) : lastFrame(previousFrame);
    }

    private boolean isLastFrameNumber(FrameNumber frameNumber) {
        return frameNumber.equals(lastFrameNumber);
    }

    private Frame normalFrame(Frame previousFrame) {
        return frameFactory.nextFrame(previousFrame);
    }

    private Frame lastFrame(Frame previousFrame) {
        return frameFactory.lastFrame(previousFrame);
    }

    public void each(BiConsumer<FrameNumber, Frame> frameConsumer) {
        FrameNumber frameNumber = new FrameNumber(1);
        for(Frame frame : frameList) {
            frameConsumer.accept(frameNumber, frame);
            frameNumber = frameNumber.advanced();
        }
    }
}
