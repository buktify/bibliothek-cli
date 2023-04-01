package org.buktify.bibliothekcli.command.action.impl.profile;

import org.buktify.bibliothekcli.profile.InitializationProfile;
import org.jetbrains.annotations.NotNull;


public interface ProfileAction<T extends InitializationProfile> {

    @NotNull T getProfile();
}
