package org.buktify.bibliothekcli.data.bootstrap;

import org.buktify.bibliothekcli.data.bootstrap.exception.FileDownloadingException;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface DataBootstrap {

    void init();

    void bootstrap(@NotNull DownloadableFileImage dataHolder);

    void download(@NotNull DownloadableFileImage downloadableFile, @NotNull File file) throws FileDownloadingException;

    List<DownloadableFileImage> getByType(@NotNull FileImage.ImageType imageType);

    Optional<DownloadableFileImage> getByTypeAndVersion(@NotNull FileImage.ImageType imageType, @NotNull String version);

    Optional<DownloadableFileImage> getLastestBuild(@NotNull FileImage.ImageType imageType);


}
