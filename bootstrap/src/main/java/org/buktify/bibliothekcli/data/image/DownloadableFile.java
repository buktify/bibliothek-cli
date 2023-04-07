package org.buktify.bibliothekcli.data.image;

import org.buktify.bibliothekcli.data.bootstrap.response.BuildsResponse;

/**
 * Interface that represents a downloadable file.
 */
public interface DownloadableFile {

    /**
     * @return the latest build of the downloadable file
     */
    BuildsResponse.Build getLastestBuild();
}