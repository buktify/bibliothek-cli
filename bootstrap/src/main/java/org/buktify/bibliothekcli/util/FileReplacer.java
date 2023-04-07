package org.buktify.bibliothekcli.util;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A utility class for replacing strings within a file.
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileReplacer {

    /**
     * The file to be modified.
     */
    File file;

    /**
     * The contents of the file.
     */
    @Setter
    @NonFinal
    String contents;

    /**
     * Constructs new instance of FileReplacer
     *
     * @param file     The file to be modified.
     * @param contents The contents of the file.
     */
    private FileReplacer(File file, @NotNull String contents) {
        this.file = file;
        this.contents = contents;
    }

    /**
     * Creates a new FileReplacer instance with the specified file.
     *
     * @param file The file to be modified.
     * @return The new FileReplacer instance.
     */
    @SneakyThrows(IOException.class)
    public static FileReplacer open(@NotNull File file) {
        return new FileReplacer(file, Files.readString(file.toPath()));
    }

    /**
     * Replaces all occurrences of a specified pattern with the given replacement string.
     *
     * @param pattern     The pattern to be replaced.
     * @param replacement The string to replace the pattern with.
     * @return The updated FileReplacer instance.
     */
    public FileReplacer replace(@NotNull String pattern, @NotNull String replacement) {
        contents = contents.replaceAll(pattern, replacement);
        return this;
    }

    /**
     * Applies the changes made to the file.
     */
    @SneakyThrows(IOException.class)
    public void apply() {
        Files.writeString(file.toPath(), contents);
    }

}
