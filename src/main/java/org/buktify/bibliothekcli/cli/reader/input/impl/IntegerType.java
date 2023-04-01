package org.buktify.bibliothekcli.cli.reader.input.impl;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.cli.reader.input.Type;
import org.buktify.bibliothekcli.cli.reader.input.Validatable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

public class IntegerType implements Type<Integer>, Validatable {

    @Override
    @SneakyThrows
    public Integer secureGet(@NotNull BufferedReader reader) {
        String input = reader.readLine();
        return isValid(input) ? Integer.parseInt(input) : null;
    }

    @Override
    public boolean isValid(@NotNull String input) {
        return input.matches("\\d+");
    }

    @Override
    public String getHint() {
        return "";
    }
}
