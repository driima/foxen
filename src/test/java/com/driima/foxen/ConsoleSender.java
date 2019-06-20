package com.driima.foxen;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ConsoleSender implements CommandSender {

    public static final ConsoleSender SYSTEM = new ConsoleSender("SYSTEM");

    private final String name;

    @Override
    public String getName() {
        return name;
    }
}
