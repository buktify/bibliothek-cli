package org.buktify.cli.writer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.cli.localization.Localization;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TerminalWriterImpl implements TerminalWriter {

    String promptPrefix;

    @Override
    public void write(@NotNull String input) {
        System.out.print(input);
    }

    @Override
    public void writeln(@NotNull String string) {
        System.out.println(string);
    }

    @Override
    public void localizedWrite(@NotNull String key) {
        write(Objects.requireNonNull(Localization.localized(key)));
    }

    @Override
    public void localizedWriteln(@NotNull String key) {
        writeln(Objects.requireNonNull(Localization.localized(key)));
    }

    @Override
    public void preparePromptInput() {
        write(promptPrefix);
    }
}
