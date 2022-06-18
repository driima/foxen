package com.driima.foxen.parsing.argument;

import com.driima.foxen.parsing.ParsableString;

import java.util.*;

public class BooleanArgument implements ParsableString<Boolean> {

    private static final Random random = new Random();

    private static final Map<Boolean, List<String>> booleanConversions;

    static {
        booleanConversions = new HashMap<>(2);

        booleanConversions.put(true, new ArrayList<>(Arrays.asList(
                "true", "yes", "on", "1"
        )));

        booleanConversions.put(false, new ArrayList<>(Arrays.asList(
                "false", "no", "off", "0"
        )));
    }

    @Override
    public Boolean parse(String input) {
        Boolean result = null;

        for (Map.Entry<Boolean, List<String>> entry : booleanConversions.entrySet()) {
            if (entry.getValue().contains(input.toLowerCase())) {
                result = entry.getKey();
                break;
            }
        }

        return result;
    }

    @Override
    public String getExample() {
        List<String> strings = booleanConversions.get(random.nextBoolean());
        return strings.get(random.nextInt(strings.size()));
    }

    @Override
    public Boolean getOptionalDefault() {
        return false;
    }

    @Override
    public String getFailure(String input) {
        return "Argument not a valid boolean.";
    }
}
