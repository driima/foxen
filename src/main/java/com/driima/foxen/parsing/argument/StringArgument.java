package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

public class StringArgument implements ParsableString<String> {

    @Override
    public String parse(String input) {
        return input;
    }

    @Override
    public String getExample() {
        return "Hello!";
    }

    @Override
    public String getFailure(String input) {
        return "Could not parse string.";
    }
}
