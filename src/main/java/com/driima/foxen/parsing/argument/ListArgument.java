package com.driima.foxen.parsing.argument;

import com.driima.foxen.ParameterProfile;
import com.driima.foxen.exception.ArgumentParseException;
import com.driima.foxen.parsing.Arguments;
import com.driima.foxen.parsing.ParsableString;
import com.google.common.collect.Lists;

import java.util.List;

public class ListArgument implements ParsableString<List> {

    @Override
    public List<?> parse(String input, ParameterProfile parameterProfile) throws ArgumentParseException {
        List<String> stringList = parse(input);

        System.out.println(parameterProfile.getParameterGenericTypes());

        if (parameterProfile.getParameterGenericTypes() != null && parameterProfile.getParameterGenericTypes().size() == 1) {
            ParsableString<?> parsable = Arguments.getRegisteredParsable(parameterProfile.getParameterGenericTypes().get(0));
            List<Object> output = Lists.newArrayList();

            stringList.forEach(object -> output.add(parsable.parse(object)));

            return output;
        }

        return stringList;
    }

    @Override
    public List<String> parse(String input) {
        return Lists.newArrayList(input.replaceAll("^[,\\s]+", "").split("[,\\s]+"));
    }

    @Override
    public String getFailure(String input) {
        return "Argument failure.";
    }
}
