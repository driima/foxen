package com.driima.foxen.parsing;

import com.driima.foxen.parsing.argument.*;

import java.util.*;

public final class Arguments {

    private static final Map<Class<?>, Class<? extends ParsableString<?>>> registeredParsableTypes = new HashMap<>();
    private static final Map<Class<? extends ParsableString<?>>, ParsableString<?>> cachedParsableInstances = new HashMap<>();

    public static final BooleanArgument BOOLEAN = new BooleanArgument();
    public static final ByteArgument BYTE = new ByteArgument();
    public static final DoubleArgument DOUBLE = new DoubleArgument();
    public static final FloatArgument FLOAT = new FloatArgument();
    public static final IntegerArgument INTEGER = new IntegerArgument();
    public static final StringArgument STRING = new StringArgument();
    public static final ListArgument LIST = new ListArgument();
    public static final SetArgument SET = new SetArgument();

    static {
        registerParsable(Boolean.class, BOOLEAN);
        registerParsable(boolean.class, BOOLEAN);
        registerParsable(Byte.class, BYTE);
        registerParsable(byte.class, BYTE);
        registerParsable(Double.class, DOUBLE);
        registerParsable(double.class, DOUBLE);
        registerParsable(Float.class, FLOAT);
        registerParsable(float.class, FLOAT);
        registerParsable(Integer.class, INTEGER);
        registerParsable(int.class, INTEGER);

        registerParsable(String.class, STRING);
        registerParsable(List.class, LIST);
        registerParsable(Set.class, SET);
    }

    public static <T> void registerParsable(Class<T> type, ParsableString<? super T> parsable) {
        Class<? extends ParsableString<T>> parsableType = (Class<? extends ParsableString<T>>) parsable.getClass();

        registeredParsableTypes.put(type, parsableType);
        cachedParsableInstances.put(parsableType, parsable);
    }

    public static <T> Class<? extends ParsableString<T>> getRegisteredParsableType(Class<T> type) {
        return (Class<? extends ParsableString<T>>) registeredParsableTypes.getOrDefault(type, null);
    }

    public static <T> ParsableString<T> getRegisteredParsable(Class<T> type) {
        return (ParsableString<T>) cachedParsableInstances.getOrDefault(registeredParsableTypes.get(type), null);
    }

    public static boolean hasRegisteredParsable(Class<?> type) {
        return registeredParsableTypes.containsKey(type);
    }

}