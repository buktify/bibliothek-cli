package org.buktify.localization;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@UtilityClass
public class Localization {

    private JsonObject localizationJson;

    @SneakyThrows(IOException.class)
    public boolean init(@NotNull ResourceLoader resourceLoader, @NotNull String locale) {
        Resource localizationSource;
        try {
            localizationSource = resourceLoader.getResource("classpath:locale/" + locale + ".json");
        } catch (Exception e) {
            return false;
        }
        File localizationFile = localizationSource.getFile();
        localizationJson = JsonParser.parseString(Files.readString(localizationFile.toPath())).getAsJsonObject();
        return true;
    }

    @Nullable
    public String localized(@NotNull String key) {
        return localizationJson.get(key).getAsString();
    }

}
