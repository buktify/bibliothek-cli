package org.buktify.bibliothekcli.data.image.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.bootstrap.response.BuildsResponse;
import org.buktify.bibliothekcli.data.image.DownloadableFile;
import org.buktify.bibliothekcli.data.image.FileImage;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor
public class DownloadableFileImage implements DownloadableFile, FileImage {

    String version;
    ImageType imageType;
    BuildsResponse.Build lastestBuild;

    @Override
    public String getCanonicalFileName() {
        return imageType.name().toLowerCase() + "-" + version + ".jar";
    }
}
