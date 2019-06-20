package com.driima.foxen.parsing;

import java.util.HashMap;
import java.util.Map;

public final class ParseResult {

    private final Object[] parameters;
    private final Map<String, Exception> exceptions = new HashMap<>();

    public ParseResult(int parameterLength) {
        parameters = new Object[parameterLength];
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameter(int i, Object parameter) {
        parameters[i] = parameter;
    }

    public void addException(String argument, Exception exception) {
        exceptions.put(argument, exception);
    }

    public Map<String, Exception> getExceptions() {
        return exceptions;
    }

    public boolean succeeded() {
        return exceptions.isEmpty();
    }
}
