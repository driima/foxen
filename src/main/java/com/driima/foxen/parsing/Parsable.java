package com.driima.foxen.parsing;

import com.driima.foxen.exception.ArgumentParseException;

public interface Parsable<T, U> {

    T parse(U input) throws ArgumentParseException;

    default T getOptionalDefault() {
        return null;
    }

    String getFailure(U input);
}