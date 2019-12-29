package com.disalvo.peter;

import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleBowlingGameRunner {

    private final Console console;
    private ConsoleBowlingScoreCardApp bowlingScoreCard;

    public ConsoleBowlingGameRunner() {
        console = new SystemConsole();
        bowlingScoreCard = new ConsoleBowlingScoreCardApp(new BowlingScoreCard(), console);
    }

    public void start() {
        console.printLine("Start bowling!");

        while (true) {
            try {
                console.readRoll(input -> bowlingScoreCard.roll(input));
            } catch (InvalidRollAttemptException e) {
                console.printLine("Oops. That's an invalid roll");
            }
        }
    }

    private static class SystemConsole implements Console {
        private final Scanner input = new Scanner(System.in);

        @Override
        public void printLine(String string) {
            System.out.println(string);
        }

        @Override
        public void readRoll(Consumer<Integer> rollConsumer) {
            System.out.print("Roll: ");
            int number = input.nextInt();
            rollConsumer.accept(number);
        }
    }
}
