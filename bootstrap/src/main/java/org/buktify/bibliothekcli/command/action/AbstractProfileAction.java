package org.buktify.bibliothekcli.command.action;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.profile.InitializationProfile;
import org.buktify.bibliothekcli.profile.impl.PaperProfile;
import org.buktify.bibliothekcli.profile.impl.VelocityProfile;
import org.buktify.cli.localization.Localization;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

/**
 * An abstract base class for profile actions.
 * <p>
 * This class provides a protected method for validating the server directory.
 * <p>
 * This class is meant to be extended by concrete profile action classes, which can implement their own behavior.
 */
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public abstract class AbstractProfileAction {

    TerminalReader reader;
    TerminalWriter writer;

    /**
     * Validates whether a server directory can be used for this profile.
     *
     * @param serverDirectory the directory to validate
     * @return true if the directory can be used, false otherwise
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean validateServerDirectory(@NotNull File serverDirectory) {
        if (serverDirectory.exists()) {
            if (serverDirectory.listFiles() != null && Objects.requireNonNull(serverDirectory.listFiles()).length != 0) {
                String option = reader.localizedForceRead("directory-already-exists-with-files", InputType.OPTIONAL);
                return option.equalsIgnoreCase("y");
            }
            if (serverDirectory.isFile()) {
                writer.localizedWriteln("directory-is-a-file");
                return false;
            }
            String option = reader.localizedForceRead("directory-already-exists", InputType.OPTIONAL);
            return option.equalsIgnoreCase("y");
        }
        return true;
    }

    protected boolean validateProfile(@NotNull InitializationProfile profile) {
        if (profile instanceof VelocityProfile velocityProfile) {
            writer.writeln(Objects.requireNonNull(Localization.localized("velocity-verify-profile"))
                    .replaceAll("%server-name%", velocityProfile.getServerName())
                    .replaceAll("%optimization-flags%", velocityProfile.getOptimizationShellFlags() != null ? "applied" : "none")
                    .replaceAll("%server-port%", String.valueOf(velocityProfile.getServerPort()))
                    .replaceAll("%online-mode%", String.valueOf(velocityProfile.isOnlineMode())));
        }
        if (profile instanceof PaperProfile paperProfile) {
            writer.writeln(Objects.requireNonNull(Localization.localized("paper-verify-profile"))
                    .replaceAll("%server-name%", paperProfile.getServerName())
                    .replaceAll("%version%", paperProfile.getVersion())
                    .replaceAll("%proxy-connection%", paperProfile.getProxyConnectionProfile() != null ? paperProfile.getProxyConnectionProfile().getProxyFolder() : "none")
                    .replaceAll("%optimization-flags%", paperProfile.getOptimizationShellFlags() != null ? "applied" : "none")
                    .replaceAll("%server-port%", String.valueOf(paperProfile.getServerPort()))
                    .replaceAll("%online-mode%", String.valueOf(paperProfile.isOnlineMode())));
        }
        return reader.localizedForceRead("processor-continue-opt", InputType.OPTIONAL).equalsIgnoreCase("y");
    }

}
