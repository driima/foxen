package com.driima.foxen;

import com.driima.foxen.parsing.SuppliableArguments;
import lombok.Getter;
import lombok.Setter;

public class BasicCommandHandler extends CommandHandler<CommandSource> {

    @Getter
    @Setter
    private boolean logExecutionTime;

    /**
     * Processes the CommandSource
     *
     * @param commandSource our basic implementation of an object containing a source and the command being sent.
     *                      Other APIs should have their own implementations, and the generic type of the command
     *                      handler should always be sent with the raw command String to the process method.
     */
    public void process(CommandSource commandSource) {
        if (logExecutionTime) {
            long start = System.nanoTime();
            super.process(commandSource, commandSource.getCommand());
            long end = System.nanoTime();

            double executionTime = (end - start);

            System.out.println("Execution time: " + executionTime + "ns");

            return;
        }

        super.process(commandSource, commandSource.getCommand());
    }

    @Override
    public SuppliableArguments getSuppliableArguments(CommandSource source) {
        return new SuppliableArguments()
                .supply(CommandSource.class, () -> source)
                .supply(BasicCommandHandler.class, () -> this);
    }
}
