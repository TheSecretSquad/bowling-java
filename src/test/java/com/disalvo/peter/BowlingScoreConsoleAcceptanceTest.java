package com.disalvo.peter;

import org.junit.Assert;
import org.junit.Test;

public class BowlingScoreConsoleAcceptanceTest {

    private String linesAsString(String ... lines) {
        return String.join(System.lineSeparator(), lines);
    }

    @Test
    public void test() {

        CapturingConsole capturingConsole = new CapturingConsole();
        ConsoleBowlingScoreCardApp app = new ConsoleBowlingScoreCardApp(new BowlingScoreCard(), capturingConsole);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        app.roll(1);
        app.roll(1);

        Assert.assertEquals(linesAsString(
"┌─────┬─┬─┐┌─────┬─┬─┐┌─────┬─┬─┐┌─────┬─┬─┐┌─────┬─┬─┐┌─────┬─┬─┐┌─────┬─┬─┐┌─────┬─┬─┐┌─────┬─┬─┐┌─────┬─┬─┐",
"│     │1│1││     │1│1││     │1│1││     │1│1││     │1│1││     │1│1││     │1│1││     │1│1││     │1│1││     │1│1│",
"│     └─┴─┤│     └─┴─┤│     └─┴─┤│     └─┴─┤│     └─┴─┤│     └─┴─┤│     └─┴─┤│     └─┴─┤│     └─┴─┤│     └─┴─┤",
"│    2    ││    4    ││    6    ││    8    ││    10   ││    12   ││    14   ││    16   ││    18   ││    20   │",
"└─────────┘└─────────┘└─────────┘└─────────┘└─────────┘└─────────┘└─────────┘└─────────┘└─────────┘└─────────┘"),
        capturingConsole.contents());
    }
}
