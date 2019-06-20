package com.driima.foxen;

import com.driima.foxen.util.Template;

public enum CommandError {
    MISSING_PERMISSIONS("[ERROR CODE: 1] You are not allowed to issue this command."),
    ARGUMENT_MISSING("[ERROR CODE: 2] Type [{type}] required, but not supplied."),
    ARGUMENT_ILLEGAL_FORMAT("[ERROR CODE: 3] Type [{type}] expected, but found \"{found}\" instead."),
    PARSER_MISSING("[ERROR CODE: 4] Type [{type}] has no registered parser."),
    PARSER_FOR_ARGUMENT_MISSING("[ERROR CODE: 5] Type [{type}] requires a parser to be registered to parse value \"{found}\"."),
    PARSER_AND_ARGUMENT_MISSING("[ERROR CODE: 5] Type [{type}] required, but not supplied and has no registered parser."),
    PARSE_FAILED("[ERROR CODE: 6] Failed to parse \"{found}\" into type [{type}]: {reason}"),
    INCORRECT_USAGE("[ERROR CODE: 7] Incorrect command usage: {command}\n"),
    ERROR("[ERROR CODE: 8] An unexpected error occurred while parsing the command.");

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
