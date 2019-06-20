package com.driima.foxen;

import com.driima.foxen.executors.BasicCommands;
import com.driima.foxen.parsing.Arguments;
import com.driima.foxen.parsing.ParsableString;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Scanner;

public class CommandTest {

    private static List<User> users = Lists.newArrayList();

    public static void main(String[] args) {
        BasicCommandHandler basicCommandHandler = new BasicCommandHandler();
        basicCommandHandler.registerCommandExecutor(new BasicCommands());
        // A simple command handler wrapper to a String type, registered to the same BasicCommands executor.
        // Note that attempting to issue the /say command will not work as it expects a CommandSource argument.
        VeryBasicCommandHandler veryBasicCommandHandler = new VeryBasicCommandHandler();
        veryBasicCommandHandler.registerCommandExecutor(new BasicCommands());

        users.add(new User("john"));

        // Register User as a parsable argument. This takes a String and returns a User.
        ParsableString<CommandSender> parsable = new ParsableString<CommandSender>() {
            @Override
            public CommandSender parse(String input) {
                for (CommandSender sender : users) {
                    if (sender.getName().equalsIgnoreCase(input)) {
                        return sender;
                    }
                }

                return null;
            }

            @Override
            public CommandSender getOptionalDefault() {
                return CommandSource.SYSTEM.getSource();
            }

            @Override
            public String getFailure(String input) {
                return "User could not be found";
            }
        };

        Arguments.registerParsable(CommandSender.class, parsable);

        // TODO: Subtypes
        Arguments.registerParsable(User.class, parsable);

        Scanner scanner = new Scanner(System.in);
        String readString;

        while (scanner.hasNextLine()) {
            readString = scanner.nextLine();

            if (!readString.isEmpty()) {
                basicCommandHandler.process(CommandSource.SYSTEM.setCommand(readString));
//                veryBasicCommandHandler.process(readString);
            }
        }
    }
}
