package org.buktify.bibliothekcli.util;

import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class OSUtility {

    private static final OSType detectedOsType = detectOperationSystemType();

    public OSType getOsType(){
        return detectedOsType;
    }

    public enum OSType {
        WINDOWS,
        MAC,
        LINUX,
        OTHER
    }

    private OSType detectOperationSystemType() {
        String osString = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if ((osString.contains("mac")) || (osString.contains("darwin"))) {
            return OSType.MAC;
        }
        if (osString.contains("win")) {
            return OSType.WINDOWS;
        }
        if (osString.contains("nux")) {
            return OSType.LINUX;
        }
        return OSType.OTHER;
    }

}
