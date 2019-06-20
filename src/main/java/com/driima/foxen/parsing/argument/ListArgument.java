package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;
import com.google.common.collect.Lists;

import java.util.List;

public class ListArgument implements ParsableString<List> {

    @Override
    public List<String> parse(String input) {
        return Lists.newArrayList(input.replaceAll("^[,\\s]+", "").split("[,\\s]+"));
    }

    @Override
    public String getFailure(String input) {
        return "Argument failure.";
    }
}
