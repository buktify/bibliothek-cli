package org.buktify.bibliothekcli.util;

import lombok.experimental.UtilityClass;

import java.util.Locale;

/**
 * The OSUtility class provides utility methods for detecting the operating system type.
 */
@UtilityClass
public class OSUtility {

    /**
     * The detected operating system type.
     */
    private static final OSType detectedOsType = detectOperationSystemType();

    /**
     * @return the detected operating system type
     */
    public OSType getOsType() {
        return detectedOsType;
    }

    /**
     * Detects the operating system type.
     *
     * @return the detected operating system type
     */
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

    public enum OSType {
        WINDOWS,
        MAC,
        LINUX,
        OTHER
    }

}
