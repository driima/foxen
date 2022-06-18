package com.driima.foxen.parsing;

public interface ParsableString<T> extends Parsable<T, String> {

    default T getOptionalDefault() {
        return null;
    }
}