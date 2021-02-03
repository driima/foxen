package com.driima.foxen.parsing;

import com.driima.foxen.exception.ArgumentParseException;

public interface Parsable<T, U> {

    String GENERIC_FAILURE = "Invalid Argument";

    T parse(U input) throws ArgumentParseException;

    default T getOptionalDefault() {
        return null;
    }

    default String getFailure(U input) { return GENERIC_FAILURE; }
}