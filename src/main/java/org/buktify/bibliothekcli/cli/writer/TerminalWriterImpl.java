package org.buktify.bibliothekcli.cli.writer;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.buktify.bibliothekcli.util.Localization;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TerminalWriterImpl implements TerminalWriter {

    @Value("${application.prompt-prefix}")
    @NonFinal
    private String promptPrefix;

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
