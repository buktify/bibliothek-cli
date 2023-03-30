package org.buktify.bibliothekcli.cli.reader.input.impl.extended;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.cli.reader.input.Validatable;
import org.buktify.bibliothekcli.cli.reader.input.impl.StringType;

import java.io.BufferedReader;

public class OptionalType extends StringType implements Validatable {

    @Override
    public boolean isValid(String item) {
        return item.equalsIgnoreCase("y") || item.equalsIgnoreCase("n");
    }

    @Override
    @SneakyThrows
    public String secureGet(BufferedReader reader) {
         String line = reader.readLine();
         return isValid(line) ? line : null;
    }

    @Override
    public String getHint() {
        return "[y/n]";
    }
}
