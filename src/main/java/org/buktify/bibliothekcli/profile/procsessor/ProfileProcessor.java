package org.buktify.bibliothekcli.profile.procsessor;

import org.buktify.bibliothekcli.profile.InitializationProfile;
import org.jetbrains.annotations.NotNull;

public interface ProfileProcessor<T extends InitializationProfile> {

    boolean process(@NotNull T profile);
}
