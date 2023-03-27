package org.buktify.bibliothekcli.data.image;

public interface FileImage{

    String getVersion();

    ImageType getImageType();

    enum ImageType {
        PAPER,
        VELOCITY
    }
}
