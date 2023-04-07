package org.buktify.bibliothekcli.profile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.image.FileImage;

/**
 * An interface representing a profile for initializing a server
 */
public interface InitializationProfile {

    /**
     * Gets the type of image associated with this initialization profile.
     *
     * @return The type of image.
     */
    FileImage.ImageType getImageType();

    /**
     * Determines whether online mode is should be enabled on server.
     *
     * @return true if online mode should be enabled on server.
     */
    boolean isOnlineMode();

    /**
     * Gets the name of the server associated with this initialization profile.
     *
     * @return The server name.
     */
    String getServerName();

    /**
     * Gets the port number associated with the server.
     *
     * @return The server port number.
     */
    int getServerPort();

    /**
     * Gets the optimization shell flags associated with the initialization profile.
     *
     * @return The optimization shell flags.
     */
    OptimizationShellFlags getOptimizationShellFlags();

    /**
     * An enumeration representing the optimization shell flags
     * that can be associated with an initialization profile.
     */
    @RequiredArgsConstructor
    @Getter
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    enum OptimizationShellFlags {

        NONE(""),
        AIKAR("--add-modules=jdk.incubator.vector -XX:+UseG1GC -XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions -XX:+DisableExplicitGC -XX:+AlwaysPreTouch -XX:G1HeapWastePercent=5 -XX:G1MixedGCCountTarget=4 -XX:InitiatingHeapOccupancyPercent=15 -XX:G1MixedGCLiveThresholdPercent=90 -XX:G1RSetUpdatingPauseTimePercent=5 -XX:SurvivorRatio=32 -XX:+PerfDisableSharedMem -XX:MaxTenuringThreshold=1 -Dusing.aikars.flags=https://mcflags.emc.gs -Daikars.new.flags=true -XX:G1NewSizePercent=30 -XX:G1MaxNewSizePercent=40 -XX:G1HeapRegionSize=8M -XX:G1ReservePercent=20"),
        PROXY("-XX:+UseG1GC -XX:G1HeapRegionSize=4M -XX:+UnlockExperimentalVMOptions -XX:+ParallelRefProcEnabled -XX:+AlwaysPreTouch -XX:MaxInlineLevel=15");

        String flags;

    }

}
