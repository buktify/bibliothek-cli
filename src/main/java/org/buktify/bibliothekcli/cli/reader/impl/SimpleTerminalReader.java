package org.buktify.bibliothekcli.cli.reader.impl;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.TerminalReader;
import org.buktify.bibliothekcli.cli.reader.input.InputType;
import org.buktify.bibliothekcli.cli.reader.input.Type;
import org.buktify.bibliothekcli.cli.reader.input.Validatable;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

@Component
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class SimpleTerminalReader implements TerminalReader {

    String PROMPT_PREFIX = "bibliothek-cli> ";
    BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public <T> T read(String request, Type<T> type) {
        System.out.println(request + (type instanceof Validatable validatable ? validatable.getHint() : ""));
        return read(type);
    }

    @SneakyThrows
    @Override
    public <T> T read(Type<T> type) {
        System.out.print(PROMPT_PREFIX);
        return type.secureGet(scanner);
    }

    @Override
    public <T> T forceRead(String request, Type<T> type) {
        System.out.println(request + (type instanceof Validatable validatable ? " " + validatable.getHint() : ""));
        while (true) {
            T result = read(type);
            if (result != null) return result;
        }
    }

    @Override
    public void processOptionalChoice(String request, Runnable optional) {
        processYesNoChoice(request, optional, () -> {});
    }

    @Override
    public void processYesNoChoice(String request, Runnable positive, Runnable negative) {
        String input = forceRead(request, InputType.OPTIONAL).toLowerCase();
        switch (input) {
            case "y" -> positive.run();
            case "n" -> negative.run();
        }
    }
}
