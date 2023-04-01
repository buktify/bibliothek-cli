package org.buktify.cli.reader;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.reader.input.Type;
import org.buktify.cli.reader.input.Validatable;
import org.buktify.cli.writer.TerminalWriter;
import org.buktify.localization.Localization;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
@RequiredArgsConstructor
public class TerminalReaderImpl implements TerminalReader {

    TerminalWriter terminalWriter;
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public <T> T read(@NotNull String request, @NotNull Type<T> type) {
        terminalWriter.writeln(request + (type instanceof Validatable validatable ? " " + validatable.getHint() : ""));
        return read(type);
    }

    @Override
    public <T> T localizedRead(@NotNull String request, @NotNull Type<T> type) {
        return read(Objects.requireNonNull(Localization.localized(request)), type);
    }

    @SneakyThrows
    @Override
    public <T> T read(@NotNull Type<T> type) {
        terminalWriter.preparePromptInput();
        return type.secureGet(bufferedReader);
    }

    @Override
    public <T> @NotNull T forceRead(@NotNull String request, @NotNull Type<T> type) {
        terminalWriter.writeln(request + (type instanceof Validatable validatable ? " " + validatable.getHint() : ""));
        while (true) {
            T result = read(type);
            if (result != null) return result;
        }
    }

    @Override
    public <T> @NotNull T localizedForceRead(@NotNull String request, @NotNull Type<T> type) {
        return forceRead(Objects.requireNonNull(Localization.localized(request)), type);
    }

    @Override
    public void processOptionalChoice(@NotNull String request, @NotNull Runnable optional) {
        processYesNoChoice(request, optional, () -> {
        });
    }

    @Override
    public void processLocalizedOptionalChoice(@NotNull String request, @NotNull Runnable optional) {
         processLocalizedYesNoChoice(request, optional, () -> {});
    }

    @Override
    public void processYesNoChoice(@NotNull String request, @NotNull Runnable positive, @NotNull Runnable negative) {
        String input = forceRead(request, InputType.OPTIONAL).toLowerCase();
        switch (input) {
            case "y" -> positive.run();
            case "n" -> negative.run();
        }
    }

    @Override
    public void processLocalizedYesNoChoice(@NotNull String request, @NotNull Runnable positive, @NotNull Runnable negative) {
        processYesNoChoice(Objects.requireNonNull(Localization.localized(request)), positive, negative);
    }
}
