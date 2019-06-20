package com.driima.foxen;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
final class CommandDescriptor {
    CommandProfile profile;
    String[] arguments;
}
