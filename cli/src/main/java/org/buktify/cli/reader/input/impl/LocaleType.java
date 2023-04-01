package org.buktify.cli.reader.input.impl;

import lombok.SneakyThrows;
import org.buktify.cli.reader.input.Validatable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.util.List;

public class LocaleType extends StringType implements Validatable {

    private final List<String> supportedLocales = List.of("en", "ru");

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
        return "[ru/en]";
    }
}
