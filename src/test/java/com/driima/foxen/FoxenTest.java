package com.driima.foxen;

import com.driima.foxen.executors.BasicCommands;
import com.driima.foxen.parsing.Arguments;
import com.driima.foxen.parsing.ParsableString;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Scanner;

public class FoxenTest {

    public static void main(String[] args) {
        new FoxenTest();
    }

    private FoxenTest() {
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
