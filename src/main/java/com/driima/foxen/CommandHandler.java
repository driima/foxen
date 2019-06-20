package com.driima.foxen;

import com.driima.foxen.exception.ArgumentParseException;
import com.driima.foxen.parsing.*;
import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class CommandHandler<T> {

    private static final Pattern STRING_SPLIT = Pattern.compile("[^\\s\"'\\[\\]]+|\"([^\"]*)\"|'([^']*)'|\\[([^\\[\\]]*)]");

    private final HashMap<String, CommandProfile> commandProfiles = new HashMap<>();
    private final List<CommandProfile> commandProfileList = new ArrayList<>();

    private UsageFormat usageFormat;

    public CommandHandler() {
        this(UsageFormat.STANDARD_FORMAT);
    }

    public CommandHandler(UsageFormat usageFormat) {
        this.usageFormat = usageFormat;
    }

    /**
     * Registers an executor containing commands. A command is a method annotated with the {@link Command} annotation.
     *
     * @param executor The executor to register.
     * @see Command
     */
    public void registerCommandExecutor(CommandExecutor executor) {
        for (Method method : executor.getClass().getMethods()) {
            Command annotation = method.getAnnotation(Command.class);

            if (annotation == null) {
                continue;
            }

            List<String> aliases = new ArrayList<>();

            if (annotation.value().length > 0) {
                for (String alias : annotation.value()) {
                    aliases.add(alias.toLowerCase());
                }
            } else {
                aliases.add(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, method.getName()).replaceAll("_", " "));
            }

            CommandProfile.CommandProfileBuilder commandProfileBuilder = CommandProfile.builder();
            commandProfileBuilder.annotation(annotation);
            commandProfileBuilder.executor(executor);

            MethodProfile.MethodProfileBuilder methodProfileBuilder = MethodProfile.builder();
            methodProfileBuilder.method(method);
            methodProfileBuilder.annotations(Arrays.asList(method.getAnnotations()));

            if (method.isAnnotationPresent(Require.class)) {
                methodProfileBuilder.require(method.getAnnotation(Require.class));
            }

            UsageProfile.UsageProfileBuilder usageProfileBuilder = UsageProfile.builder();
            usageProfileBuilder.commandAliases(aliases);

            for (Parameter parameter : method.getParameters()) {
                ParameterProfile.ParameterProfileBuilder parameterProfileBuilder = ParameterProfile.builder();
                parameterProfileBuilder.type(parameter.getType());
                parameterProfileBuilder.annotations(Arrays.asList(parameter.getAnnotations()));

                boolean isOptional = parameter.isAnnotationPresent(com.driima.foxen.Optional.class);

                if (isOptional) {
                    parameterProfileBuilder.optional(true);
                }

                if (parameter.isAnnotationPresent(Usage.class)) {
                    usageProfileBuilder.usageDescriptor(
                            UsageProfile.UsageDescriptor.builder()
                                    .usage(parameter.getAnnotation(Usage.class))
                                    .optional(isOptional).build()
                    );
                }

                methodProfileBuilder.parameterProfile(parameterProfileBuilder.build());
                methodProfileBuilder.parameterType(parameter.getType());
            }

            commandProfileBuilder.methodProfile(methodProfileBuilder.build());
            commandProfileBuilder.usageProfile(usageProfileBuilder.build());
            CommandProfile commandProfile = commandProfileBuilder.build();

            aliases.forEach(alias -> commandProfiles.put(usageFormat.getCommandPrefix() + alias, commandProfile));

            commandProfileList.add(commandProfile);
        }
    }

    public final boolean process(T commandSource, String commandString) {
        java.util.Optional<CommandDescriptor> optional = getCommandDescriptor(commandString);

        if (optional.isPresent()) {
            CommandDescriptor descriptor = optional.get();
            parse(commandSource, descriptor.getArguments(), descriptor.getProfile());
        } else {
            return false;
        }

        return true;
    }

    private java.util.Optional<CommandDescriptor> getCommandDescriptor(String context) {
        String[] splitMessage = getStringParts(context);
        String commandString = context;

        CommandProfile commandProfile = getCommandProfiles().get(commandString.toLowerCase());

        if (commandProfile == null) {
            int i = splitMessage.length;
            String joinedCommandString = commandString;

            while (commandProfile == null && i > 1) {
                joinedCommandString = String.join(" ", Arrays.copyOfRange(splitMessage, 0, --i));
                commandProfile = getCommandProfiles().get(joinedCommandString.toLowerCase());
            }

            if (commandProfile != null) {
                commandString = joinedCommandString;
                splitMessage = Arrays.copyOfRange(splitMessage, i, splitMessage.length);
            }
        }

        if (commandString.equalsIgnoreCase(context)) {
            splitMessage = new String[0];
        }

        if (commandProfile == null) {
            if (splitMessage.length > 1) {
                commandProfile = getCommandProfiles().get(splitMessage[1].toLowerCase());

                if (commandProfile == null) {
                    return java.util.Optional.empty();
                }
            } else {
                return java.util.Optional.empty();
            }
        }

        return java.util.Optional.of(new CommandDescriptor(commandProfile, splitMessage));
    }

    /**
     * Specifies a list of arguments that are supplied by the implementation. Supplied arguments are passed to
     * command methods where they are specified, and the CommandHandler API will automagically fill in their values
     * using their suppliers.
     *
     * Suppliable arguments can be inserted anywhere in a command method's signature
     *
     * @param supplier
     * @return
     */
    public SuppliableArguments getSuppliableArguments(T supplier) {
        return null;
    }

    /**
     * Specifies a list of consumers that process the return value of a command's method. Useful in the event that
     * the implementation uses its own command response system.
     * @param supplier
     * @return
     */
    public ResponseConsumer getResponseConsumer(T supplier) {
        return null;
    }

    public ImmutableMap<String, CommandProfile> getCommandProfiles() {
        return ImmutableMap.copyOf(commandProfiles);
    }

    public ImmutableList<CommandProfile> getCommandProfileList() {
        return ImmutableList.copyOf(commandProfileList);
    }

    public UsageFormat getUsageFormat() {
        return usageFormat;
    }

    private void parse(T supplier, String[] splitMessage, final CommandProfile commandProfile) {
        // TODO: This can be put into a ProcessResult, with status accessors

        final ParseResult parseResult = getParameters(splitMessage, commandProfile, getSuppliableArguments(supplier));

        if (!parseResult.succeeded()) {
            if (commandProfile.getAnnotation().showErrors()) {
                for (Map.Entry<String, Exception> entry : parseResult.getExceptions().entrySet()) {
                    System.out.println(entry.getKey() + ": " + entry.getValue().getMessage());
                }

                System.out.println("Example Usage: " + commandProfile.getUsageProfile().getUsage(usageFormat));
            }
        } else {
            if (commandProfile.getAnnotation().async()) {
                invokeMethod(commandProfile, parseResult.getParameters(), supplier);
            } else {
                invokeMethod(commandProfile, parseResult.getParameters(), supplier);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void invokeMethod(CommandProfile commandProfile, Object[] parameters) {
        Method method = commandProfile.getMethodProfile().getMethod();

        try {
            method.setAccessible(true);
            method.invoke(commandProfile.getExecutor(), parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void invokeMethod(CommandProfile commandProfile, Object[] parameters, T supplier) {
        Method method = commandProfile.getMethodProfile().getMethod();
        Object reply = null;

        try {
            method.setAccessible(true);
            reply = method.invoke(commandProfile.getExecutor(), parameters);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseConsumer responseConsumer = getResponseConsumer(supplier);

        if (responseConsumer == null) {
            return;
        }

        if (reply != null && responseConsumer.hasConsumer(reply.getClass())) {
            ((Consumer) responseConsumer.getConsumer(reply.getClass())).accept(reply);
        }
    }

    private ParseResult getParameters(String[] args, CommandProfile commandProfile, SuppliableArguments suppliableArguments) {
        List<Class<?>> parameterTypes = commandProfile.getMethodProfile().getParameterTypes();

        int parsableParameterLength = suppliableArguments == null
                ? parameterTypes.size()
                : getParsableParameterLength(suppliableArguments.types(), parameterTypes);

        String[] fixedArgs = args;

        if (args.length > parsableParameterLength && parsableParameterLength > 0 && (parameterTypes.get(parameterTypes.size() - 1) == String.class || Iterable.class.isAssignableFrom(parameterTypes.get(parameterTypes.size() - 1)))) {
            fixedArgs = Arrays.copyOfRange(args, 0, parsableParameterLength);
            String[] lastElements = Arrays.copyOfRange(args, parsableParameterLength - 1, args.length);
            fixedArgs[parsableParameterLength - 1] = String.join(" ", lastElements);
        }

        ParseResult result = new ParseResult(parameterTypes.size());

        int parsableIndex = 0;

        for (int i = 0; i < parameterTypes.size(); i++) {
            ParameterProfile parameterProfile = commandProfile.getMethodProfile().getParameterProfiles().get(i);
            Class<?> type = parameterProfile.getType();

            if (type == String[].class) {
                result.setParameter(i, args);
            } else if (suppliableArguments != null && suppliableArguments.has(type)) {
                result.setParameter(i, suppliableArguments.get(type).get());
            } else {
                boolean optional = parameterProfile.isOptional();

                ParsableString<?> parsable = Arguments.getRegisteredParsable(type);

                if (fixedArgs.length <= parsableIndex) {
                    parsableIndex++;

                    if (parsable != null) {
                        if (optional) {
                            result.setParameter(i, parsable.getOptionalDefault());
                        } else {
                            result.setParameter(i, null);

                            result.addException("Argument " + parsableIndex,
                                    new ArrayIndexOutOfBoundsException(CommandError.ARGUMENT_MISSING.toString(
                                            "type", type.getSimpleName()
                                    ))
                            );
                        }
                    } else {
                        result.setParameter(i, null);

                        if (optional) {
                            result.addException("Argument " + parsableIndex,
                                    new ArgumentParseException(CommandError.PARSER_MISSING.toString(
                                            "type", type.getSimpleName()
                                    ))
                            );
                        } else {
                            result.addException("Argument " + parsableIndex,
                                    new ArgumentParseException(CommandError.PARSER_AND_ARGUMENT_MISSING.toString(
                                            "type", type.getSimpleName()
                                    ))
                            );
                        }
                    }

                    continue;
                }

                String parsableArgument = fixedArgs[parsableIndex++];

                if (parsable != null) {
                    try {
                        Object argument = parsable.parse(parsableArgument);

                        if (argument != null) {
                            result.setParameter(i, argument);
                        } else if (!optional) {
                            result.addException("Argument " + parsableIndex,
                                    new ArgumentParseException(CommandError.PARSE_FAILED.toString(
                                            "found", parsableArgument,
                                            "type", type.getSimpleName(),
                                            "reason", parsable.getFailure(parsableArgument)
                                    ))
                            );
                        }
                    } catch (ArgumentParseException e) {
                        result.setParameter(i, null);

                        if (!optional) {
                            result.addException("Argument " + parsableIndex,
                                    new ArgumentParseException(CommandError.PARSE_FAILED.toString(
                                            "found", parsableArgument,
                                            "type", type.getSimpleName(),
                                            "reason", e.getMessage()
                                    ))
                            );
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        result.setParameter(i, null);

                        if (!optional) {
                            result.addException("Argument " + parsableIndex,
                                    new ArrayIndexOutOfBoundsException(CommandError.ARGUMENT_MISSING.toString(
                                            "type", type.getSimpleName()
                                    ))
                            );
                        }
                    } catch (IllegalArgumentException e) {
                        result.setParameter(i, null);

                        result.addException("Argument " + parsableIndex,
                                new ArgumentParseException(CommandError.ARGUMENT_ILLEGAL_FORMAT.toString(
                                        "type", type.getSimpleName(),
                                        "found", parsableArgument
                                ))
                        );
                    } catch (Exception e) {
                        result.setParameter(i, null);

                        result.addException("Argument " + parsableIndex,
                                new ArgumentParseException(CommandError.ERROR.toString())
                        );
                    }
                } else {
                    result.setParameter(i, null);

                    if (!optional) {
                        result.addException("Argument " + parsableIndex, new ArgumentParseException(CommandError.PARSER_FOR_ARGUMENT_MISSING.toString(
                                "type", type.getSimpleName(),
                                "found", parsableArgument
                        )));
                    }
                }
            }
        }

        return result;
    }

    protected String[] getStringParts(String whole) {
        List<String> matches = new ArrayList<>();

        Matcher matcher = STRING_SPLIT.matcher(whole);

        while (matcher.find()) {
            if (matcher.group(1) != null) {
                matches.add(matcher.group(1));
            } else if (matcher.group(2) != null) {
                matches.add(matcher.group(2));
            } else if (matcher.group(3) != null) {
                matches.add(matcher.group(3));
            } else {
                matches.add(matcher.group());
            }
        }

        return matches.toArray(new String[0]);
    }

    /**
     * Get the length of dynamically parsed parameters whose type is not applied automatically when parsed.
     *
     * @param parameterTypes the parameter types to extract the parsable length from
     * @return the length of parsable parameters
     */
    private int getParsableParameterLength(Set<Class<?>> suppliedArgumentTypes, List<Class<?>> parameterTypes) {
        int length = 0;

        for (Class<?> type : parameterTypes) {
            if (!suppliedArgumentTypes.contains(type)) {
                length++;
            }
        }

        return length;
    }
}
