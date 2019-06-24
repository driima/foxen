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
     * TODO: Identify and warn when an @Optional preceeds a non-@Optional, which would render it useless.
     */
    @Command
    public void add(int firstNumber, @Optional int secondNumber) {
        System.out.println(firstNumber + secondNumber);
    }

    @Command("toggleTimeLog")
    public void toggleTimeLog(BasicCommandHandler basicCommandHandler) {
        basicCommandHandler.setLogExecutionTime(!basicCommandHandler.isLogExecutionTime());

        System.out.println("Toggled time logging: " + basicCommandHandler.isLogExecutionTime());
    }
}
