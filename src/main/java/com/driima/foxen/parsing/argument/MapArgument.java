package com.driima.foxen.parsing.argument;

import com.driima.foxen.ParameterProfile;
import com.driima.foxen.exception.ArgumentParseException;
import com.driima.foxen.parsing.Arguments;
import com.driima.foxen.parsing.ParsableString;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MapArgument implements ParsableString<Map> {

    @Override
    public Map<?, ?> parse(String input, ParameterProfile parameterProfile) throws ArgumentParseException {
        Map<String, String> map = parse(input);

        if (parameterProfile.getParameterGenericTypes() != null && parameterProfile.getParameterGenericTypes().size() == 2) {
            ParsableString<?> parsableKey = Arguments.getRegisteredParsable(parameterProfile.getParameterGenericTypes().get(0));
            ParsableString<?> parsableValue = Arguments.getRegisteredParsable(parameterProfile.getParameterGenericTypes().get(1));

            Map output = Maps.newHashMap();

            map.forEach((key, value) -> output.put(parsableKey.parse(key), parsableValue.parse(value)));

            return output;
        }

        return map;
    }

    @Override
    public Map<String, String> parse(String input) {
        List<String> strings = Lists.newArrayList(input.replaceAll("^[,\\s]+", "").split("[,\\s]+"));

        System.out.println(strings);
        return IntStream.iterate(0, i -> i + 2)
                        .limit(strings.size() / 2)
                        .boxed()
                        .collect(Collectors.toMap(strings::get, i -> strings.get(i + 1)));
    }

    @Override
    public String getFailure(String input) {
        return "Argument failure.";
    }
}
