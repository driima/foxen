package com.driima.foxen;

import com.driima.foxen.util.Template;

public enum CommandError {
    MISSING_PERMISSIONS("You are not allowed to issue this command."),
    ARGUMENT_MISSING("{type} required, but not supplied."),
    ARGUMENT_ILLEGAL_FORMAT("{type} expected, but found \"{found}\" instead."),
    PARSER_MISSING("{type} has no registered parser."),
    PARSER_FOR_ARGUMENT_MISSING("{type} requires a parser to be registered to parse value \"{found}\"."),
    PARSER_AND_ARGUMENT_MISSING("{type} required, but not supplied and has no registered parser."),
    PARSE_FAILED("Failed to parse \"{found}\" as {type}: {reason}"),
    INCORRECT_USAGE("Incorrect command usage: {command}\n"),
    ERROR("An unexpected error occurred while parsing the command.");

    private String message;

    CommandError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public String toString(Object... templateReplacements) {
        return Template.get().map(this.message, templateReplacements);
    }
}
