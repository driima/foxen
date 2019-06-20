package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.*;

public class BooleanArgument implements ParsableString<Boolean> {

    private static final Map<Boolean, Set<String>> booleanConversions;
    private static final Set<String> predictions = new HashSet<>(Arrays.asList("true", "false"));

    static {
        booleanConversions = new HashMap<>(2);

        booleanConversions.put(true, new HashSet<>(Arrays.asList(
                "true", "yes", "on", "1"
        )));

        booleanConversions.put(false, new HashSet<>(Arrays.asList(
                "false", "no", "off", "0"
        )));
    }

    @Override
    public Boolean parse(String input) {
        Boolean result = null;

        for (Map.Entry<Boolean, Set<String>> entry : booleanConversions.entrySet()) {
            if (entry.getValue().contains(input.toLowerCase())) {
                result = entry.getKey();
                break;
            }
        }

        return result;
    }

    @Override
    public Boolean getOptionalDefault() {
        return false;
    }

    @Override
    public String getFailure(String input) {
        return "Argument not a valid boolean.";
    }

    @Override
    public Set<String> getPredictions() {
        return predictions;
    }
}
