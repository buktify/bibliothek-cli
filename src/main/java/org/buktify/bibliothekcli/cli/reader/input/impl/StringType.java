package org.buktify.bibliothekcli.cli.reader.input.impl;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.cli.reader.input.Type;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

public class StringType implements Type<String> {

    @Override
    @SneakyThrows
    public String secureGet(@NotNull BufferedReader reader) {
        return reader.readLine();
    }
}
