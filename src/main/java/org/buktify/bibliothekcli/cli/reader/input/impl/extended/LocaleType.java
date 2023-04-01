package org.buktify.bibliothekcli.cli.reader.input.impl.extended;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.cli.reader.input.Validatable;
import org.buktify.bibliothekcli.cli.reader.input.impl.StringType;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

public class LocaleType extends StringType implements Validatable {

    @Override
    @SneakyThrows
    public String secureGet(@NotNull BufferedReader reader) {
        String input = reader.readLine().toLowerCase();
        return isValid(input) ? input : null;
    }

    @Override
    public boolean isValid(@NotNull String item) {
        return item.equalsIgnoreCase("en") || item.equalsIgnoreCase("ru");
    }

    @Override
    public String getHint() {
        return "[ru/en]";
    }
}
