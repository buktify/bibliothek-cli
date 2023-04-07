package org.buktify.bibliothekcli.data.image.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.bootstrap.response.BuildsResponse;
import org.buktify.bibliothekcli.data.image.DownloadableFile;
import org.buktify.bibliothekcli.data.image.FileImage;

/**
 * Class that represents a downloadable file image.
 * It implements both {@link DownloadableFile} and {@link FileImage} interfaces.
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor
public class DownloadableFileImage implements DownloadableFile, FileImage {

    String version;
    ImageType imageType;
    BuildsResponse.Build lastestBuild;

    /**
     * Returns the canonical file name of the downloadable file image,
     * which is a string representation of the image type, version and file extension.
     * <p>
     * Example format: paper-1.16.5.jar or velocity-3.2.0-SNAPSHOT.jar
     *
     * @return the canonical file name of the downloadable file image
     */
    @Override
    public String getCanonicalFileName() {
        return imageType.name().toLowerCase() + "-" + version + ".jar";
    }
}
