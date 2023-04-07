package org.buktify.bibliothekcli.data.bootstrap;

import org.buktify.bibliothekcli.data.bootstrap.exception.FileDownloadingException;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * This interface defines the behavior of a data bootstrap component
 * that downloads and manages downloadable files.
 */
public interface DataBootstrap {

    /**
     * Initializes the DataBootstrap instance.
     */
    void init();

    /**
     * Bootstraps with the given {@link DownloadableFileImage} object.
     *
     * @param dataHolder the {@link DownloadableFileImage} object to bootstrap the instance with.
     */
    void bootstrap(@NotNull DownloadableFileImage dataHolder);

    /**
     * Downloads a specified image to the specified file.
     *
     * @param downloadableFile the downloadable image.
     * @param file             the file (destination) to download.
     * @throws FileDownloadingException if an error occurs while downloading the file.
     */
    void download(@NotNull DownloadableFileImage downloadableFile, @NotNull File file) throws FileDownloadingException;

    /**
     * Gets a list of {@link DownloadableFileImage} objects with the specified image type.
     *
     * @param imageType the image type to filter by.
     * @return a list of {@link DownloadableFileImage} objects with the specified image type.
     */
    List<DownloadableFileImage> getByType(@NotNull FileImage.ImageType imageType);

    /**
     * Gets a {@link DownloadableFileImage} object with the specified image type and version.
     *
     * @param imageType the image type to filter by.
     * @param version   the version to filter by.
     * @return an Optional containing the {@link DownloadableFileImage} object with the specified image type and version, or empty if no such object exists.
     */
    Optional<DownloadableFileImage> getByTypeAndVersion(@NotNull FileImage.ImageType imageType, @NotNull String version);

    /**
     * Gets the latest build of a  {@link DownloadableFileImage} object with the specified image type.
     *
     * @param imageType the image type to filter by.
     * @return an Optional containing the latest build with the specified image type, or empty if no such object exists.
     */
    Optional<DownloadableFileImage> getLastestBuild(@NotNull FileImage.ImageType imageType);

}
