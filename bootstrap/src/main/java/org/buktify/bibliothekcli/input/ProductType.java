package org.buktify.bibliothekcli.input;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.cli.reader.input.Type;
import org.buktify.cli.reader.input.Validatable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * A type for representing the product type
 */
public class ProductType implements Type<FileImage.ImageType>, Validatable {

    /**
     * Checks if a given string is a valid FileImage.ImageType.
     *
     * @param item the string to check
     * @return true if the string is a valid FileImage.ImageType, false otherwise
     */
    @Override
    public boolean isValid(@NotNull String item) {
        return Arrays.stream(FileImage.ImageType.values())
                .map(imageType -> imageType.name().toLowerCase())
                .anyMatch(s -> s.equalsIgnoreCase(item));
    }

    /**
     * Returns a hint for what input is expected
     *
     * @return a string providing a hint for the expected input
     */
    @Override
    public String getHint() {
        return "[paper/velocity]";
    }

    /**
     * Attempts to read a FileImage.ImageType from a BufferedReader.
     *
     * @param reader the BufferedReader to read from
     * @return the FileImage.ImageType, or null if the input was not valid
     */
    @Override
    @SneakyThrows(IOException.class)
    public FileImage.ImageType secureGet(@NotNull BufferedReader reader) {
        String line = reader.readLine();
        return isValid(line) ? FileImage.ImageType.valueOf(line.toUpperCase()) : null;
    }
}
