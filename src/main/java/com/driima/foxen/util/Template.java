package com.driima.foxen.util;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Template {

    private static final Template standardTemplate = new Template();

    private String prefix;
    private String suffix;

    private Template() {
        this("{", "}");
    }

    public String map(String message, Object... templateReplacements) {
        for (int i = 0; i < templateReplacements.length; i ++) {
            String template = String.valueOf(templateReplacements[i]);

            Object replacement = ++i >= templateReplacements.length
                    ? template
                    : templateReplacements[i];

            if (replacement instanceof Function) {
                replacement = ((Function) replacement).apply(template);
            } else if (replacement instanceof Supplier) {
                replacement = ((Supplier) replacement).get();
            }

            if (template.equals(replacement)) {
                continue;
            }

            message = message.replace(this.prefix + template + this.suffix, String.valueOf(replacement));
        }

        return message;
    }

    public String calculate(String message, Object... templateReplacements) {
        String pattern = "(?<=" + escaped(this.prefix) + ")((?:" + escaped(this.prefix) + "??[^" + escaped(this.prefix) + "]*?))(?=" + escaped(this.suffix) + ")";
        Matcher matcher = Pattern.compile(pattern).matcher(message);

        List<String> matches = new LinkedList<>();

        while (matcher.find()) {
            matches.add(matcher.group(0));
        }

        List<Object> templates = new LinkedList<>();

        Iterator<String> iter = matches.iterator();

        for (Object templateReplacement : templateReplacements) {
            if (iter.hasNext()) {
                templates.add(iter.next());
            }

            templates.add(templateReplacement);
        }

        while (iter.hasNext()) {
            templates.add(iter.next());
        }

        return map(message, templates.toArray(new Object[templates.size()]));
    }

    public static Template get() {
        return standardTemplate;
    }

    public static Template get(String prefix, String suffix) {
        return new Template(prefix, suffix);
    }

    private static String escaped(String string) {
        return "\\" + string;
    }
}
