package com.driima.foxen;

import com.driima.foxen.util.Template;

public enum CommandError {
    MISSING_PERMISSIONS("You are not allowed to issue this command."),
    ARGUMENT_MISSING("Type [{type}] required, but not supplied."),
    ARGUMENT_ILLEGAL_FORMAT("Type [{type}] expected, but found \"{found}\" instead."),
    PARSER_MISSING("Type [{type}] has no registered parser."),
    PARSER_FOR_ARGUMENT_MISSING("Type [{type}] requires a parser to be registered to parse value \"{found}\"."),
    PARSER_AND_ARGUMENT_MISSING("Type [{type}] required, but not supplied and has no registered parser."),
    PARSE_FAILED("Failed to parse \"{found}\" into type [{type}]: {reason}"),
    INCORRECT_USAGE("Incorrect command usage: {command}\n"),
    ERROR("An unexpected error occurred while parsing the command.");

    private int errorCode;
    private String message;

    CommandError(String message) {
        this.message = message;
        this.errorCode = ordinal()+1;
    }

    @Override
    public String toString() {
        return message;
    }

    public String toString(Object... templateReplacements) {
        return Template.get().map("[ERROR CODE: " + errorCode + "] " + this.message, templateReplacements);
    }
}
