package com.disalvo.peter;

import java.util.function.Consumer;

public class CapturingConsole implements Console {

    private final StringBuilder contents;

    public CapturingConsole() {
        this.contents = new StringBuilder();
    }

    public String contents() {
        return contents.toString();
    }

    public void printLine(String line) {
        contents.append(line);
        contents.append(System.lineSeparator());
    }

    @Override
    public void readRoll(Consumer<Integer> rollConsumer) {
        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);

        rollConsumer.accept(1);
        rollConsumer.accept(1);
    }
}
