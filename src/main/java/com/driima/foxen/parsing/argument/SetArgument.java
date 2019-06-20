package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SetArgument implements ParsableString<Set> {

    @Override
    public Set<String> parse(String input) {
        return new HashSet<>(Arrays.asList(input.replaceAll("^[,\\s]+", "").split("[,\\s]+")));
    }

    @Override
    public String getFailure(String input) {
        return "Argument failure.";
    }
}
