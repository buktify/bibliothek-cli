package org.buktify.bibliothekcli.input;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.cli.reader.input.Type;
import org.buktify.cli.reader.input.Validatable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;

public class ProductType implements Type<FileImage.ImageType>, Validatable {

    @Override
    public boolean isValid(@NotNull String item) {
        return item.equalsIgnoreCase("paper") || item.equalsIgnoreCase("velocity");
    }

    @Override
    public String getHint() {
        return "[paper/velocity]";
    }

    @Override
    @SneakyThrows
    public FileImage.ImageType secureGet(@NotNull BufferedReader reader) {
        String line = reader.readLine();
        return isValid(line) ? FileImage.ImageType.valueOf(line.toUpperCase()) : null;
    }
}
