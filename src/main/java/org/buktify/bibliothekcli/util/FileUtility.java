package org.buktify.bibliothekcli.util;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@UtilityClass
public class FileUtility {

    public boolean saveResourceToFile(@NotNull ResourceLoader resourceLoader, @NotNull String resourcePath, @NotNull File outputFile) {
        try (InputStream inputStream = resourceLoader.getResource("classpath:" + resourcePath).getInputStream();
             OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            return true;
        } catch (Exception ignored) {
            System.out.println(ignored);
            return false;
        }
    }

}
