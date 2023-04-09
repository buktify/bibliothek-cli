package org.buktify.bibliothekcli.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This class contains version information about application
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
public class ApplicationProperties {

    /**
     * Current application version, got from application.yml
     */
    @NotNull
    String currentVersion;

    /**
     * Lastest version, got from GitHub releases
     */
    @Nullable
    String lastestVersion;
}
