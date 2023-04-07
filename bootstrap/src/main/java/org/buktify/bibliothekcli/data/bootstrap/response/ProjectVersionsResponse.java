package org.buktify.bibliothekcli.data.bootstrap.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

/**
 * This class represents a response object containing a list of project versions.
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ProjectVersionsResponse {

    List<String> versions;

}