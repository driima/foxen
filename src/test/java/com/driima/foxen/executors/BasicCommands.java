package com.driima.foxen.executors;

import com.driima.foxen.*;

import java.util.List;

public class BasicCommands implements CommandExecutor {

    /**
     * A basic implementation of a /ping command.
     */
    @Command
    public void ping() {
        System.out.println("pong!");
    }

    /**
     * A basic implementation of a command that will output the sum of a list of specified numbers.
     * TODO: varargs
     * TODO: Identify and warn when an @Optional preceeds a non-@Optional, which would render it useless.
     */
    @Command
    public void add(List<Integer> numbers) {
        System.out.println(numbers.stream().mapToInt(Integer::intValue).sum());
    }

    @Command
    public void getUser(User user) {
        System.out.println(user.getName());
    }

    @Command
    public void getUserTime(User user) {
        System.out.println("Time: " + user.getName());
    }

    @Command("toggleTimeLog")
    public void toggleTimeLog(BasicCommandHandler basicCommandHandler, String test) {
        basicCommandHandler.setLogExecutionTime(!basicCommandHandler.isLogExecutionTime());

        System.out.println("Toggled time logging: " + basicCommandHandler.isLogExecutionTime());
    }
}
