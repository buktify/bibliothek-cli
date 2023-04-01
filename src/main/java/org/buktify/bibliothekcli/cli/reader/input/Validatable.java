package org.buktify.bibliothekcli.cli.reader.input;

import org.jetbrains.annotations.NotNull;

public interface Validatable {

    boolean isValid(@NotNull String item);

    String getHint();
}
