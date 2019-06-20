package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

public class IntegerArgument implements ParsableString<Integer> {

    @Override
    public Integer parse(String input) {
        return Integer.valueOf(input);
    }

    @Override
    public Integer getOptionalDefault() {
        return 0;
    }

    @Override
    public String getFailure(String input) {
        return "Argument not an integer.";
    }
}
