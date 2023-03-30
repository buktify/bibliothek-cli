package org.buktify.bibliothekcli.cli.reader.input.impl;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.cli.reader.input.Type;
import org.buktify.bibliothekcli.cli.reader.input.Validatable;

import java.io.BufferedReader;

public class IntegerType implements Type<Integer>, Validatable {

    @Override
    @SneakyThrows
    public Integer secureGet(BufferedReader reader) {
        String input = reader.readLine();
        return isValid(input) ? Integer.parseInt(input) : null;
    }

    @Override
    public boolean isValid(String input) {
        return input.matches("\\d+");
    }

    @Override
    public String getHint() {
        return "";
    }
}
