package com.driima.foxen.parsing;

import com.driima.foxen.exception.ArgumentParseException;

public interface Parsable<T, U> {

    String GENERIC_FAILURE = "Invalid Argument";

    String EXAMPLE_UNIMPLEMENTED = "Example not implemented.";

    T parse(U input) throws ArgumentParseException;

    default String getExample() {
        return EXAMPLE_UNIMPLEMENTED;
    }

    default T getOptionalDefault() {
        return null;
    }

    default String getFailure(U input) { return GENERIC_FAILURE; }
}