package org.buktify.bibliothekcli.util;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileReplacer {

    final File file;
    @Setter
    String contents;

    private FileReplacer(File file, @NotNull String contents) {
        this.file = file;
        this.contents = contents;
    }

    @SneakyThrows
    public static FileReplacer open(@NotNull File file) {
        return new FileReplacer(file, Files.readString(file.toPath()));
    }

    public FileReplacer replace(@NotNull String pattern, @NotNull String replacement) {
        contents = contents.replaceAll(pattern, replacement);
        return this;
    }

    @SneakyThrows
    public void apply() {
        Files.writeString(file.toPath(), contents);
    }

}
