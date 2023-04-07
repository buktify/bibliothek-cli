package org.buktify.bibliothekcli.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This utility class provides a method to save a resource file to a specified output file.
 * Only works with {@link ClassPathResource}
 */
@UtilityClass
public class FileUtility {

    /**
     * Saves a resource file to a specified output file.
     *
     * @param resourcePath the path to the resource file to be saved
     * @param outputFile   the file to which the resource file will be saved
     * @return true if the resource file was successfully saved, false otherwise
     */
    public boolean saveResourceToFile(@NotNull String resourcePath, @NotNull File outputFile) {
        try (InputStream inputStream = new ClassPathResource(resourcePath).getInputStream();
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}
