package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ByteArgument implements ParsableString<Byte> {

    private static final Set<String> predictions = new HashSet<>(
            Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15")
    );

    @Override
    public Byte parse(String input) {
        return Byte.valueOf(input);
    }

    @Override
    public Byte getOptionalDefault() {
        return 0;
    }

    @Override
    public String getFailure(String input) {
        return "Argument not a valid byte.";
    }

    @Override
    public Set<String> getPredictions() {
        return predictions;
    }
}
