package com.driima.foxen;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class CommandSource<T> {

    public static final CommandSource<CommandSender> SYSTEM = new CommandSource<>(new CommandSender("SYSTEM"));

    private String command;
    private final T source;
}
