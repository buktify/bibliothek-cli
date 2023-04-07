package org.buktify.bibliothekcli.input;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.buktify.cli.reader.input.Validatable;
import org.buktify.cli.reader.input.impl.StringType;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.util.List;

/**
 * A type for representing the paper version
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PaperVersionType extends StringType implements Validatable {

    DataBootstrap dataBootstrap;

    /**
     * Checks if given string is a valid version
     *
     * @param item string to check
     * @return true if string is valid, otherwise false
     */
    @Override
    public boolean isValid(@NotNull String item) {
        return dataBootstrap.getByTypeAndVersion(FileImage.ImageType.PAPER, item).isPresent();
    }

    /**
     * Attempts to read a version string from a BufferedReader
     *
     * @param reader the BufferedReader to read from
     * @return a version string, or null if the input was not valid
     */
    @Override
    @SneakyThrows
    public String secureGet(@NotNull BufferedReader reader) {
        String input = reader.readLine();
        return isValid(input) ? input : null;
    }

    /**
     * Returns a hint for what input is expected
     *
     * @return a string providing a hint for the expected input
     */
    @Override
    public String getHint() {
        List<DownloadableFileImage> versionData = dataBootstrap.getByType(FileImage.ImageType.PAPER);
        String first = versionData.get(0).getVersion();
        String lastest = versionData.get(versionData.size() - 1).getVersion();
        return "[" + first + "-" + lastest + "]";
    }
}
