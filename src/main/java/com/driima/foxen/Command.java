package com.driima.foxen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that a method should be registered as a Command.
 *
 * @see Command#value()
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    /**
     * The aliases used to issue the command. If no alias is specified, the name of the method
     * annotated with this Command is used, which is converted from lowerCamelCase to lowercase
     * separated by spaces.
     *
     * If multiple aliases are declared, any can be used to issue the command, but the first alias
     * will be used when displaying a command's usage, unless specified otherwise in the Usage
     * implementation.
     *
     * @return a collection of aliases used to issue the command.
     */
    String[] value() default {};

    /**
     * Determines whether errors should be shown if the command was issued incorrectly.
     */
    boolean showErrors() default true;

    /**
     * Determines whether the command should be issued asynchronously (through a separate thread).
     */
    boolean async() default false;
}
