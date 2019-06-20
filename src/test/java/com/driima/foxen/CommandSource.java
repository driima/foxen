package com.driima.foxen;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class CommandSource<T extends CommandSender> {

    public static final CommandSource<ConsoleSender> SYSTEM = new CommandSource<>(ConsoleSender.SYSTEM);

    private String command;
    private final T source;
}
