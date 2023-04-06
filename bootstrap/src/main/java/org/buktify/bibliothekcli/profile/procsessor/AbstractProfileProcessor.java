package org.buktify.bibliothekcli.profile.procsessor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.data.bootstrap.exception.FileDownloadingException;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public abstract class AbstractProfileProcessor {

    TerminalWriter writer;
    DataBootstrap dataBootstrap;

    @SneakyThrows
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean performBaseFileActions(@NotNull String serverDirectory, @NotNull DownloadableFileImage fileImage) {
        File serverDirectoryFile = Paths.get(serverDirectory).toFile();
        writer.localizedWriteln("processor-creating-folder");
        Files.createDirectories(serverDirectoryFile.toPath());
        writer.localizedWriteln("processor-downloading-server-jar");
        try {
            File serverJarFile = Paths.get(serverDirectory + fileImage.getCanonicalFileName()).toFile();
            dataBootstrap.download(fileImage, serverJarFile);
        } catch (FileDownloadingException exception) {
            writer.writeln(exception.getMessage());
            return false;
        }
        return true;
    }


    @SneakyThrows
    protected void addServer(@NotNull String serverData, @NotNull File velocityConfigFile) {
        ArrayList<String> lines = new ArrayList<>(Arrays.stream(FileUtils.readFileToString(velocityConfigFile, "UTF-8").split("\\r?\\n")).toList());
        boolean serverSection = false;
        for (int i = 0; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            if (line.equals("[servers]")) serverSection = true;
            if (serverSection) {
                if (line.isEmpty()) {
                    lines.add(i, serverData);
                    break;
                }
            }
        }
        FileUtils.writeLines(velocityConfigFile, "UTF-8", lines, "\n");
    }
}
