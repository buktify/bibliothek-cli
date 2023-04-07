package org.buktify.bibliothekcli.profile.impl;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.profile.InitializationProfile;

/**
 * A class representing a Paper initialization profile.
 * Implements the {@link InitializationProfile} interface.
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Builder
@RequiredArgsConstructor
public class PaperProfile implements InitializationProfile {

    /**
     * An ImageType used for the server.
     */
    FileImage.ImageType imageType = FileImage.ImageType.PAPER;

    /**
     * The version of the server to use.
     */
    String version;

    /**
     * The name of the server.
     */
    String serverName;

    /**
     * Whether the server is in online mode.
     */
    @Builder.Default
    boolean onlineMode = true;

    /**
     * The port to use for the server.
     */
    @Builder.Default
    int serverPort = 25565;

    /**
     * The proxy connection profile to use.
     */
    @Builder.Default
    ProxyConnectionProfile proxyConnectionProfile = null;

    /**
     * The optimization shell flags to use.
     */
    @Builder.Default
    OptimizationShellFlags optimizationShellFlags = null;

    /**
     * A profile for the proxy connection.
     */
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    @Getter
    @RequiredArgsConstructor
    public static class ProxyConnectionProfile {

        /**
         * The folder containing the proxy.
         */
        String proxyFolder;

    }

}