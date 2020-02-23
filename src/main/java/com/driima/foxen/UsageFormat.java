package com.driima.foxen;

import com.google.common.base.CaseFormat;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Function;

@Builder
@Getter
public final class UsageFormat {
    public static final UsageFormat STANDARD = builder()
            .commandPrefix("/")
            .optionalArgsAffixes("[]")
            .requiredArgsAffixes("<>")
            .build();

    public static final UsageFormat STANDARD_CAMEL_TO_SPACED = builder()
            .commandPrefix("/")
            .commandTransformer(input -> CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, input).replaceAll("_", " ").toLowerCase())
            .optionalArgsAffixes("[]")
            .requiredArgsAffixes("<>")
            .build();

    private final String commandPrefix;
    private final Function<String, String> commandTransformer;
    private final String[] requiredArgsAffixes;
    private final String[] optionalArgsAffixes;

    public static class UsageFormatBuilder {
        public UsageFormatBuilder requiredArgsAffixes(String characters) throws IllegalArgumentException {
            validateCharacterLength(characters);

            this.requiredArgsAffixes = getParts(characters);

            return this;
        }

        public UsageFormatBuilder optionalArgsAffixes(String characters) throws IllegalArgumentException {
            validateCharacterLength(characters);

            this.optionalArgsAffixes = getParts(characters);

            return this;
        }

        private void validateCharacterLength(String characters) throws IllegalArgumentException {
            if (characters.length() % 2 == 1) {
                throw new IllegalArgumentException("Opening and closing characters must be equal in length.");
            }
        }

        private String[] getParts(String characters) {
            int halfLength = characters.length() / 2;

            return new String[]{
                    characters.substring(0, halfLength),
                    characters.substring(halfLength)
            };
        }
    }
}
