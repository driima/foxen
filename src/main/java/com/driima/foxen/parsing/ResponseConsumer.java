package com.driima.foxen.parsing;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@NoArgsConstructor
public final class ResponseConsumer {

    private final Map<Class<?>, Consumer<?>> consumers = new HashMap<>();

    public <T> ResponseConsumer consume(Class<T> type, Consumer<T> consumer) {
        consumers.put(type, consumer);

        return this;
    }

    public boolean hasConsumer(Class<?> type) {
        return consumers.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    public <T> Consumer<T> getConsumer(Class<T> type) {
        return (Consumer<T>) consumers.getOrDefault(type, null);
    }
}
