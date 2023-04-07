package org.buktify.bibliothekcli.profile.procsessor;

import org.buktify.bibliothekcli.profile.InitializationProfile;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for processing {@link InitializationProfile}.
 *
 * @param <T> the type of {@link InitializationProfile} to process.
 */
public interface ProfileProcessor<T extends InitializationProfile> {

    /**
     * Process the given profile.
     *
     * @param profile the {@link InitializationProfile} to process.
     * @return true if the processing was successful, false otherwise.
     */
    boolean process(@NotNull T profile);
}