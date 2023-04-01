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
    public void init(@NotNull ResourceLoader resourceLoader, @NotNull String locale) throws LocalizationException {
        Resource localizationSource;
        try {
            localizationSource = resourceLoader.getResource("classpath:locale/" + locale + ".json");
        }
        catch (Exception ignored){
            throw new LocalizationException("Could not load locale from " + locale + ".json");
        }
        File localizationFile = localizationSource.getFile();
        // localizationJson = (JSONObject) JSONValue.parse(Files.readString(localizationFile.toPath()));
        localizationJson = JsonParser.parseString(Files.readString(localizationFile.toPath())).getAsJsonObject();
    }

    @Nullable
    public String localized(@NotNull String key){
        return localizationJson.get(key).getAsString();
    }

    public class LocalizationException extends Exception{
        public LocalizationException(String message) {
            super(message);
        }
    }
}
