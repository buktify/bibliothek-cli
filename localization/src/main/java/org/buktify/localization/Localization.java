package org.buktify.localization;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;

@UtilityClass
public class Localization {

    private JsonObject localizationJson;

    public boolean init(@NotNull String locale) {
        try {
            Resource resource = new ClassPathResource("locale/" + locale + ".json");
            String content = IOUtils.toString(resource.getInputStream(), Charset.defaultCharset());
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
