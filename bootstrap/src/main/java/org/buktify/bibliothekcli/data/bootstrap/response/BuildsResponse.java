package org.buktify.bibliothekcli.data.bootstrap.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * This class represents a response object containing a list of builds,
 * each of which contains a time, build number, and download data.
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class BuildsResponse {

    /**
     * The list of builds.
     */
    List<Build> builds;

    /**
     * This inner class represents a build object containing a time, build number, and downloads.
     */
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Build {

        /**
         * The time the build was created.
         */
        String time;

        /**
         * The build number.
         */
        Integer build;

        /**
         * The downloads associated with the build.
         */
        Download downloads;

    }

    /**
     * This inner class represents a download object
     */
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    @NoArgsConstructor
    public static class Download {

        Application application;
    }

    /**
     * This inner class represents a file object containing a name and SHA256 hash.
     */
    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Application {

        /**
         * The name of the downloadable file.
         */
        String name;

        /**
         * The SHA256 hash of the application.
         */
        String sha256;
    }

}