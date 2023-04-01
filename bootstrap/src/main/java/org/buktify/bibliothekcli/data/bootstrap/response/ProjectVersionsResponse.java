package org.buktify.bibliothekcli.data.bootstrap.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class ProjectVersionsResponse {

    private List<String> versions;

}
