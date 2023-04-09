package org.buktify.cli.reader.input.impl;

import lombok.SneakyThrows;
import org.buktify.cli.reader.input.Validatable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.util.List;
import java.util.StringJoiner;

public class LocaleType extends StringType implements Validatable {

    private final List<String> supportedLocales = List.of("ru", "en");


    @Override
    @SneakyThrows
    public String secureGet(@NotNull BufferedReader reader) {
        String input = reader.readLine().toLowerCase();
        return isValid(input) ? input : null;
    }

    @Override
    public boolean isValid(@NotNull String item) {
        return supportedLocales.contains(item.toLowerCase());
    }

    @Override
    public String getHint() {
        StringJoiner stringJoiner = new StringJoiner("/", "[", "]");
        supportedLocales.forEach(stringJoiner::add);
        return stringJoiner.toString();
    }
}
