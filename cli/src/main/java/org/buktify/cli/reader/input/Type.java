package org.buktify.cli.reader.input;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

public interface Type<T> {

    T secureGet(@NotNull BufferedReader reader);
}
