package org.buktify.bibliothekcli.data.bootstrap.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class BuildsResponse {

    List<Build> builds;

    @Data
    @NoArgsConstructor
    public static class Build {

        String time;
        Integer build;
        Download downloads;

    }

    @Data
    @NoArgsConstructor
    public static class Download {

        Application application;
    }

    @Data
    public static class Application {
        String name;
        String sha256;
    }

}
