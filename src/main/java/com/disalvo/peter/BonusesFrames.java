package com.disalvo.peter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class BonusesFrames {

    private final Collection<Frame> bonusCollection;

    BonusesFrames() {
        this.bonusCollection = new ArrayList<>();
    }


    void roll(PinCount pinCount) {
        safelyModifiableIteration(frame -> frame.bonusRoll(pinCount));
    }

    private void safelyModifiableIteration(Consumer<Frame> bonusConsumer) {
        Collection<Frame> bonusCollection = new ArrayList<>(this.bonusCollection);
        for(Frame frame : bonusCollection) {
            bonusConsumer.accept(frame);
        }
    }

    void unregister(Frame frame) {
        bonusCollection.remove(frame);
    }

    public void register(Frame frame) {
        bonusCollection.add(frame);
    }
}
