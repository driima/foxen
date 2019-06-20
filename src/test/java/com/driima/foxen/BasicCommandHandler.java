package com.driima.foxen;

import com.driima.foxen.parsing.SuppliableArguments;

public class BasicCommandHandler extends CommandHandler<CommandSource> {

    /**
     * Processes the CommandSource
     *
     * @param commandSource our basic implementation of an object containing a source and the command being sent.
     *                      Other APIs should have their own implementations, and the generic type of the command
     *                      handler should always be sent with the raw command String to the process method.
     */
    public void process(CommandSource commandSource) {
        super.process(commandSource, commandSource.getCommand());
    }

    @Override
    public SuppliableArguments getSuppliableArguments(CommandSource source) {
        return new SuppliableArguments().supply(CommandSource.class, () -> source);
    }
}
