package org.buktify.bibliothekcli.data.image;

public interface DownloadableFile {

    String getDownloadUrl();

    void download();

}
