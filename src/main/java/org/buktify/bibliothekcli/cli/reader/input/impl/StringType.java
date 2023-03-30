package org.buktify.bibliothekcli.cli.reader.input.impl;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.cli.reader.input.Type;

import java.io.BufferedReader;

public class StringType implements Type<String> {

    @Override
    @SneakyThrows
    public String secureGet(BufferedReader reader) {
        return reader.readLine();
    }
}
