package org.buktify.bibliothekcli.cli.writer.impl;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.writer.TerminalWriter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public abstract class AbstractTerminalWriter implements TerminalWriter {

    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    @Override
    @SneakyThrows(IOException.class)
    public void write(String input) {
        writer.write(input);
    }
}
