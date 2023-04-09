package org.buktify.cli.localization;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Objects;

@UtilityClass
public class Localization {

    private JsonObject localizationJson;

    public boolean init(@NotNull String locale) {
        try {
            InputStream inputStream = Localization.class.getClassLoader().getResourceAsStream("locale/" + locale + ".json");
            Objects.requireNonNull(inputStream);
            String content = IOUtils.toString(inputStream, Charset.defaultCharset());
            localizationJson = JsonParser.parseString(content).getAsJsonObject();
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Nullable
    public String localized(@NotNull String key) {
        return localizationJson.get(key).getAsString();
    }

}
