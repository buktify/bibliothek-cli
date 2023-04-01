package org.buktify.bibliothekcli.cli.reader;

import org.buktify.bibliothekcli.cli.reader.input.Type;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TerminalReader {

    @Nullable <T> T read(@NotNull String request, @NotNull Type<T> type);

    @Nullable <T> T localizedRead(@NotNull String request, @NotNull Type<T> type);

    @Nullable <T> T read(@NotNull Type<T> type);

    @NotNull <T> T forceRead(@NotNull String request, @NotNull Type<T> type);

    @NotNull <T> T localizedForceRead(@NotNull String request, @NotNull Type<T> type);

    void processOptionalChoice(@NotNull String request, @NotNull Runnable optional);

    void processLocalizedOptionalChoice(@NotNull String request, @NotNull Runnable optional);

    void processYesNoChoice(@NotNull String request, @NotNull Runnable positive, @NotNull Runnable negative);

    void processLocalizedYesNoChoice(@NotNull String request, @NotNull Runnable positive, @NotNull Runnable negative);
}
