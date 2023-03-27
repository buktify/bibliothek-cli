package org.buktify.bibliothekcli.data.bootstrap.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@RequiredArgsConstructor
@Data
public class ProjectVersionsResponse {

    private List<String> versions;

}
