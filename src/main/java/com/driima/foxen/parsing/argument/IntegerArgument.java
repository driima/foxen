package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.Random;

public class IntegerArgument implements ParsableString<Integer> {

    private static final Random random = new Random();

    @Override
    public Integer parse(String input) {
        return Integer.valueOf(input);
    }

    @Override
    public String getExample() {
        return random.nextInt() + "";
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
