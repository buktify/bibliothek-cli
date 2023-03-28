package org.buktify.bibliothekcli.cli.reader.impl;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.TerminalReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public abstract class AbstractTerminalReader implements TerminalReader {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    @SneakyThrows(IOException.class)
    public String read() {
        return reader.readLine();
    }
}
