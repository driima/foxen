package com.driima.foxen;

import com.driima.foxen.CommandHandler;

public class VeryBasicCommandHandler extends CommandHandler<String> {

    public void process(String command) {
        super.process(command, command);
    }
}
