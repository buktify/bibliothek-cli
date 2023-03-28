package org.buktify.bibliothekcli.cli.writer.impl;

import lombok.SneakyThrows;

import java.io.IOException;

public class SimpleTerminalWriter extends AbstractTerminalWriter {

    @Override
    @SneakyThrows(IOException.class)
    public void write(String input) {
        writer.flush();
    }
}
