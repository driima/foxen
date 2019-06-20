package com.driima.foxen.executors;

import com.driima.foxen.*;

public class BasicCommands implements CommandExecutor {

    /**
     * A basic implementation of a /ping command.
     */
    @Command
    public void ping() {
        System.out.println("pong!");
    }

    /**
     * A basic implementation of a command that will output the sum of two specified numbers.
     * TODO: varargs
     */
    @Command
    public void add(int firstNumber, int secondNumber) {
        System.out.println(firstNumber + secondNumber);
    }
}
