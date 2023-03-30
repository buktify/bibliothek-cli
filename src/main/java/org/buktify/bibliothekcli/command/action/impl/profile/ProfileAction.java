package org.buktify.bibliothekcli.command.action.impl.profile;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.profile.InitializationProfile;


@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Getter
@RequiredArgsConstructor
public abstract class ProfileAction<T extends InitializationProfile> {

    protected abstract T getProfile();
}
