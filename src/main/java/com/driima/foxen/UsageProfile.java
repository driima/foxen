package com.driima.foxen;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

@Builder
public class UsageProfile {

    private static final Random USAGE_RANDOM = new Random();

    @Singular
    private final List<String> commandAliases;

    @Getter
    @Singular
    private final List<UsageDescriptor> usageDescriptors;

    public String getUsage() {
        return getUsage(UsageFormat.STANDARD_FORMAT);
    }

    public String getUsage(UsageFormat usageFormat) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        stringJoiner.add(usageFormat.getCommandPrefix() + commandAliases.get(0));

        String[] requiredArgsAffixes = usageFormat.getRequiredArgsAffixes();
        String[] optionalArgsAffixes = usageFormat.getOptionalArgsAffixes();

        for (UsageDescriptor usageDescriptor : usageDescriptors) {
            String[] values = usageDescriptor.value;
            boolean showAffixes = usageDescriptor.showAffixes;

            StringBuilder commandUsageBuilder = new StringBuilder();

            String value = "UNSPECIFIED";

            if (values.length == 1) {
                value = values[0];
            } else if (values.length > 1) {
                value = values[USAGE_RANDOM.nextInt(values.length)];
            }

            if (showAffixes) {
                boolean isOptional = usageDescriptor.optional;
                String openingAffixes = isOptional ? optionalArgsAffixes[0] : requiredArgsAffixes[0];
                String closingAffixes = isOptional ? optionalArgsAffixes[1] : requiredArgsAffixes[1];
                commandUsageBuilder.append(openingAffixes).append(value).append(closingAffixes);
            } else {
                commandUsageBuilder.append(value);
            }

            stringJoiner.add(commandUsageBuilder.toString());
        }

        return stringJoiner.toString();
    }

    @Getter
    @Builder
    public static class UsageDescriptor {
        private final String example;
        private final String[] value;
        @Builder.Default
        private final boolean showAffixes = true;
        private final boolean optional;
    }
}
