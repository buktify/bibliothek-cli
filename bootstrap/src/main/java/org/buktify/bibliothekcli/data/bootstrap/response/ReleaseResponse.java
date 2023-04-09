package org.buktify.bibliothekcli.data.bootstrap.response;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * This class represents a response object containing lastest version of our application,
 * got from GitHub releases.
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ReleaseResponse {

    @Setter
    String tag_name;

    public String getTagName() {
        return tag_name;
    }
}
