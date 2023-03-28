package org.buktify.bibliothekcli.profile.impl;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.profile.InitializationProfile;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaperInitializationProfile implements InitializationProfile {

    String name;
    String version;
    int port;
    boolean isConnectedToProxy;

    @Override
    public InitializationType getInitializationType() {
        return InitializationType.PAPER;
    }
}
