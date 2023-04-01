package org.buktify.cli.reader.input.impl.extended;

import lombok.SneakyThrows;
import org.buktify.cli.reader.input.Validatable;
import org.buktify.cli.reader.input.impl.StringType;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

public class OptionalType extends StringType implements Validatable {

    @Override
    public boolean isValid(@NotNull String item) {
        return item.equalsIgnoreCase("y") || item.equalsIgnoreCase("n");
    }

    @Override
    @SneakyThrows
    public String secureGet(@NotNull BufferedReader reader) {
        String line = reader.readLine();
        return isValid(line) ? line : null;
    }

    @Override
    public String getHint() {
        return "[y/n]";
    }
}
