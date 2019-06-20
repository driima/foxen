package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

public class DoubleArgument implements ParsableString<Double> {

    @Override
    public Double parse(String input) {
        return Double.valueOf(input);
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
