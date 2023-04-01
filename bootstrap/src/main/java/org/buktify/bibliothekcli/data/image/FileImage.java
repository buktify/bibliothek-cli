package org.buktify.bibliothekcli.data.image;

public interface FileImage {

    String getVersion();

    ImageType getImageType();

    String getCanonicalFileName();

    enum ImageType {
        PAPER,
        VELOCITY
    }
}
