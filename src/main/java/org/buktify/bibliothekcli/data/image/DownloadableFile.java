package org.buktify.bibliothekcli.data.image;

import org.buktify.bibliothekcli.data.bootstrap.response.BuildsResponse;

public interface DownloadableFile {

    BuildsResponse.Build getLastestBuild();

    void download();

}
