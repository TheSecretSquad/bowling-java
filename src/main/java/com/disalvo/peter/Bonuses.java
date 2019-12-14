package com.disalvo.peter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class Bonuses {

    private final Collection<Bonus> bonusCollection;

    Bonuses() {
        this.bonusCollection = new ArrayList<>();
    }

    void roll(PinCount pinCount) {
        safelyModifiableIteration(bonus -> bonus.roll(pinCount));
    }

    private void safelyModifiableIteration(Consumer<Bonus> bonusConsumer) {
        Collection<Bonus> bonusCollection = new ArrayList<>(this.bonusCollection);
        for(Bonus bonus : bonusCollection) {
            bonusConsumer.accept(bonus);
        }
    }

    void unregister(Bonus bonus) {
        bonusCollection.remove(bonus);
    }

    public void register(Bonus bonus) {
        bonusCollection.add(bonus);
    }
}
