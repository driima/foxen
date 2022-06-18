package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ByteArgument implements ParsableString<Byte> {

    private static final Random random = new Random();

    @Override
    public Byte parse(String input) {
        return Byte.valueOf(input);
    }

    @Override
    public String getExample() {
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        return bytes[0] + "";
    }

    @Override
    public Byte getOptionalDefault() {
        return 0;
    }

    @Override
    public String getFailure(String input) {
        return "Argument not a valid byte.";
    }
}
