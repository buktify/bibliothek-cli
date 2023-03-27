package org.buktify.bibliothekcli.data.bootstrap;

import org.buktify.bibliothekcli.data.image.DownloadableFile;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;

import java.util.List;

public interface DataBootstrap {

    void init();

    void bootstrap(DownloadableFileImage dataHolder);

    List<DownloadableFileImage> getByType(FileImage.ImageType imageType);

    List<DownloadableFileImage> getByTypeAndVersion(FileImage.ImageType imageType, String version);

}
