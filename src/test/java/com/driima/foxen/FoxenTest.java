package com.driima.foxen;

import com.driima.foxen.executors.BasicCommands;
import com.driima.foxen.parsing.Arguments;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Scanner;

public class FoxenTest {

    public static void main(String[] args) {
        new FoxenTest();
    }

    private List<User> users;

    private FoxenTest() {

        users = Lists.newArrayList();
        users.add(new User("A"));
        users.add(new User("time"));

        Arguments.registerParsable(User.class, input ->
                users.stream()
                     .filter(user -> user.getName().equalsIgnoreCase(input))
                     .findFirst()
                     .orElse(null)
        );

        BasicCommandHandler basicCommandHandler = new BasicCommandHandler();
        basicCommandHandler.registerCommandExecutor(new BasicCommands());

        Scanner scanner = new Scanner(System.in);
        String readString;

        while (scanner.hasNextLine()) {
            readString = scanner.nextLine();

            if (!readString.isEmpty()) {
                basicCommandHandler.process(CommandSource.SYSTEM.setCommand(readString));
            }
        }
    }
}
