package org.buktify.bibliothekcli.profile;

public interface InitializationProfile {

    String getServerName();

    enum InitializationType {
        VELOCITY,
        PAPER
    }

}
