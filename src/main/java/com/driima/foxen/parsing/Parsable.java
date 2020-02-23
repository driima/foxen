package com.driima.foxen.parsing;

import com.driima.foxen.ParameterProfile;
import com.driima.foxen.exception.ArgumentParseException;

public interface Parsable<T, U> {

    T parse(U input) throws ArgumentParseException;

    default T parse(U input, ParameterProfile parameterProfile) throws ArgumentParseException {
        return parse(input);
    }

    default T getOptionalDefault() {
        return null;
    }

    String getFailure(U input);
}