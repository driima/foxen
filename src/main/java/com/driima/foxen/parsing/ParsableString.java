package com.driima.foxen.parsing;

import java.util.Collections;
import java.util.Set;

public interface ParsableString<T> extends Parsable<T, String> {

    default T getOptionalDefault() {
        return null;
    }

    default Set<String> getPredictions() {
        return Collections.emptySet();
    }
}