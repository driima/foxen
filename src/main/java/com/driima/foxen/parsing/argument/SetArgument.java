package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;
import com.google.common.collect.Sets;

import java.util.Set;

public class SetArgument implements ParsableString<Set> {

    @Override
    public Set<String> parse(String input) {
        return Sets.newHashSet(input.replaceAll("^[,\\s]+", "").split("[,\\s]+"));
    }

    @Override
    public String getFailure(String input) {
        return "Argument failure.";
    }
}
