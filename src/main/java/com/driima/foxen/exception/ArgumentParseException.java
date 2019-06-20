package com.driima.foxen.exception;

public class ArgumentParseException extends IllegalArgumentException {
    public ArgumentParseException(String error) {
        super(error);
    }
}
