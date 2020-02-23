package com.driima.foxen.executors;

import com.driima.foxen.*;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

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
    public void add(List<Integer> numbers) {
        System.out.println(numbers.stream().mapToInt(Integer::intValue).sum());
    }

    @Command
    public void map(Map<String, Integer> stringToInt) {
        add(Lists.newArrayList(stringToInt.values()));
    }

    @Command("ttl")
    public void toggleTimeLog(BasicCommandHandler basicCommandHandler) {
        basicCommandHandler.setLogExecutionTime(!basicCommandHandler.isLogExecutionTime());

        System.out.println("Toggled time logging: " + basicCommandHandler.isLogExecutionTime());
    }
}
