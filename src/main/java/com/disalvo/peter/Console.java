package com.disalvo.peter;

import java.util.function.Consumer;

public interface Console {

    void printLine(String string);

    void readRoll(Consumer<Integer> rollConsumer);
}
