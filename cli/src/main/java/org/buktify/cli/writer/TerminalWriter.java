package org.buktify.cli.writer;

import org.jetbrains.annotations.NotNull;

public interface TerminalWriter {

    void write(@NotNull String input);

    void writeln(@NotNull String string);

    void localizedWrite(@NotNull String key);

    void localizedWriteln(@NotNull String key);

    void preparePromptInput();
}
