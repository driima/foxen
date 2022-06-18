package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.Random;

public class DoubleArgument implements ParsableString<Double> {

    private static final Random random = new Random();

    @Override
    public Double parse(String input) {
        return Double.valueOf(input);
    }

    @Override
    public String getExample() {
        return random.nextDouble() + "";
    }

    @Override
    public Double getOptionalDefault() {
        return 0d;
    }

    @Override
    public String getFailure(String input) {
        return "Argument not a real number.";
    }
}
