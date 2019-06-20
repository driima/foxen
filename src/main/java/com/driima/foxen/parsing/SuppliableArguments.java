package com.driima.foxen.parsing;

import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@NoArgsConstructor
public final class SuppliableArguments {

    private final Map<Class<?>, Supplier<?>> suppliedArguments = new HashMap<>();

    public <T> SuppliableArguments supply(Class<T> type, Supplier<T> supplier) {
        this.suppliedArguments.put(type, supplier);

        return this;
    }

    public boolean has(Class<?> type) {
        return suppliedArguments.containsKey(type);
    }

    public <T> Supplier<T> get(Class<T> type) {
        return (Supplier<T>) suppliedArguments.getOrDefault(type, null);
    }

    public Map<Class<?>, Supplier<?>> entries() {
        return suppliedArguments;
    }

    public Set<Class<?>> types() {
        return suppliedArguments.keySet();
    }

    public Collection<Supplier<?>> suppliers() {
        return suppliedArguments.values();
    }
}
