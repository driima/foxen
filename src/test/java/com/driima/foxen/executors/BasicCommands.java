package com.driima.foxen.executors;

import com.driima.command.*;
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

    /**
     * A basic implementation of a command that can be issued with two different aliases.
     * This will also display the source of the message using the CommandSource specified in BasicCommandHandler's
     * SuppliedArguments.
     *
     * NOTE: Supplied Arguments can be inserted anywhere in the parameter list. This will not break argument order.
     *
     * TODO: ensure unique commands
     */
    @Command({"say", "announce"})
    public void say(@Usage("message") String message, @Optional User user) {
        System.out.println(user.getName() + " said: " + message);
    }
}
