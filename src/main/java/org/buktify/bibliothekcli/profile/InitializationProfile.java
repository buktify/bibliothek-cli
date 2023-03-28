package org.buktify.bibliothekcli.profile;

public interface InitializationProfile {

    InitializationType getInitializationType();

    enum InitializationType {
        VELOCITY,
        PAPER
    }

}
