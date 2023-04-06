package org.buktify.bibliothekcli.command.action;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public abstract class AbstractProfileAction {

    TerminalReader reader;
    TerminalWriter writer;

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean validateServerDirectory(@NotNull File serverDirectory) {
        if (serverDirectory.exists()) {
            if (serverDirectory.listFiles() != null && Objects.requireNonNull(serverDirectory.listFiles()).length != 0) {
                String option = reader.localizedForceRead("directory-already-exists-with-files", InputType.OPTIONAL);
                if (option.equals("n")) return false;
            }
            if (serverDirectory.isFile()) {
                writer.localizedWriteln("directory-is-a-file");
                return false;
            }
            String option = reader.localizedForceRead("directory-already-exists", InputType.OPTIONAL);
            return !option.equals("n");
        }
        return true;
    }

}
