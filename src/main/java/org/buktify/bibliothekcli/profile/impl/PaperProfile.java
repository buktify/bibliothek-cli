package org.buktify.bibliothekcli.profile.impl;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.profile.InitializationProfile;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Builder
@RequiredArgsConstructor
public class PaperProfile implements InitializationProfile {

    InitializationType initializationType;
    String serverName;
    @Builder.Default
    int serverPort = 25565;
    @Builder.Default
    ProxyConnectionProfile proxyConnectionProfile = null;

    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    @Getter
    @RequiredArgsConstructor
    public static class ProxyConnectionProfile{

        String proxyFolder;

    }

}
