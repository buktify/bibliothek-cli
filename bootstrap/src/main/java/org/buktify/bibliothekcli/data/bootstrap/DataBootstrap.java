package org.buktify.bibliothekcli.data.bootstrap;

import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface DataBootstrap {

    void init();

    void bootstrap(DownloadableFileImage dataHolder);

    void download(DownloadableFileImage downloadableFile, File file) throws FileImageDataBootstrap.FileDownloadingException;

    List<DownloadableFileImage> getByType(FileImage.ImageType imageType);

    Optional<DownloadableFileImage> getByTypeAndVersion(FileImage.ImageType imageType, String version);

}
