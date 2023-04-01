package org.buktify.cli.reader.input.impl.extended;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.buktify.cli.reader.input.Validatable;
import org.buktify.cli.reader.input.impl.StringType;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Component
public class PaperVersionType extends StringType implements Validatable {

    DataBootstrap dataBootstrap;

    @Override
    public boolean isValid(@NotNull String item) {
        return dataBootstrap.getByTypeAndVersion(FileImage.ImageType.PAPER, item).isPresent();
    }

    @Override
    @SneakyThrows
    public String secureGet(@NotNull BufferedReader reader) {
        String input = reader.readLine();
        return isValid(input) ? input : null;
    }

    @Override
    public String getHint() {
        List<DownloadableFileImage> versionData = dataBootstrap.getByType(FileImage.ImageType.PAPER);
        String first = versionData.get(0).getVersion();
        String lastest = versionData.get(versionData.size() - 1).getVersion();
        return "[" + first + "-" + lastest + "]";
    }
}
