package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.Random;

public class FloatArgument implements ParsableString<Float> {

    private static final Random random = new Random();

    @Override
    public Float parse(String input) {
        return Float.valueOf(input);
    }

    @Override
    public String getExample() {
        return random.nextFloat() + "";
    }

    @Override
    public Float getOptionalDefault() {
        return 0f;
    }

    @Override
    public String getFailure(String input) {
        return "Argument not a floating point number.";
    }
}
