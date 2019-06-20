package com.driima.foxen.parsing;

import com.driima.command.parsing.argument.*;
import com.driima.foxen.parsing.argument.*;

import java.util.*;

public final class Arguments {

    private static final Map<Class<?>, Class<? extends ParsableString<?>>> registeredParsableTypes = new HashMap<>();
    private static final Map<Class<? extends ParsableString<?>>, ParsableString<?>> cachedParsableInstances = new HashMap<>();

    static {
        BooleanArgument booleanArgument = new BooleanArgument();
        ByteArgument byteArgument = new ByteArgument();
        DoubleArgument doubleArgument = new DoubleArgument();
        FloatArgument floatArgument = new FloatArgument();
        IntegerArgument integerArgument = new IntegerArgument();

        registerParsable(Boolean.class, booleanArgument);
        registerParsable(boolean.class, booleanArgument);
        registerParsable(Byte.class, byteArgument);
        registerParsable(byte.class, byteArgument);
        registerParsable(Double.class, doubleArgument);
        registerParsable(double.class, doubleArgument);
        registerParsable(Float.class, floatArgument);
        registerParsable(float.class, floatArgument);
        registerParsable(Integer.class, integerArgument);
        registerParsable(int.class, integerArgument);

        registerParsable(String.class, new StringArgument());
        registerParsable(List.class, new ListArgument());
        registerParsable(Set.class, new SetArgument());
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