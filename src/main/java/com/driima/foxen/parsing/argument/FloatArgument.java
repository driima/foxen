package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

public class FloatArgument implements ParsableString<Float> {

    @Override
    public Float parse(String input) {
        return Float.valueOf(input);
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
