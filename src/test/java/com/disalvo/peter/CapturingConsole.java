package com.disalvo.peter;

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
}
