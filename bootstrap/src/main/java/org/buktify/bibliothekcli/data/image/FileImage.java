package org.buktify.bibliothekcli.data.image;

/**
 * Interface that represents a (server jar) file image.
 */
public interface FileImage {

    /**
     * @return the version of the file image
     */
    String getVersion();

    /**
     * @return the type of the file image
     */
    ImageType getImageType();

    /**
     * @return the canonical filename of the file image
     */
    String getCanonicalFileName();

    /**
     * Enum that represents the type of file image.
     */
    enum ImageType {
        PAPER, // Represents a Paper file image.
        VELOCITY // Represents a Velocity file image.
    }
}