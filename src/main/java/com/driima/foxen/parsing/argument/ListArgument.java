package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListArgument implements ParsableString<List> {

    @Override
    public List<String> parse(String input) {
        return new ArrayList<>(Arrays.asList(input.replaceAll("^[,\\s]+", "").split("[,\\s]+")));
    }

    @Override
    public String getFailure(String input) {
        return "Argument failure.";
    }
}
