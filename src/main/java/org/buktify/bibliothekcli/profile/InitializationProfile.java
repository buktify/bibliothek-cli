package org.buktify.bibliothekcli.profile;

public interface InitializationProfile {

    InitializationType getInitializationType();

    String getServerName();

    int getServerPort();

    enum InitializationType {
        VELOCITY,
        PAPER
    }

}
