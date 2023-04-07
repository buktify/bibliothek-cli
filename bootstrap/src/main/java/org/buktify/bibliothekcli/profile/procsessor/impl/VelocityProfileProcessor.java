package org.buktify.bibliothekcli.profile.procsessor.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.buktify.bibliothekcli.profile.impl.VelocityProfile;
import org.buktify.bibliothekcli.profile.procsessor.AbstractProfileProcessor;
import org.buktify.bibliothekcli.profile.procsessor.ProfileProcessor;
import org.buktify.bibliothekcli.util.FileReplacer;
import org.buktify.bibliothekcli.util.FileUtility;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
/**
 * Component that processes a {@link VelocityProfile} to create a Velocity server.
 */
@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VelocityProfileProcessor extends AbstractProfileProcessor implements ProfileProcessor<VelocityProfile> {

    /**
     * Constructor for the VelocityProfileProcessor.
     *
     * @param writer        The {@link TerminalWriter} used to write data to the console.
     * @param dataBootstrap The {@link DataBootstrap} used to download server jar.
     */
    public VelocityProfileProcessor(TerminalWriter writer, DataBootstrap dataBootstrap) {
        super(writer, dataBootstrap);
    }

    /**
     * Processes a {@link VelocityProfile} to create a Velocity server.
     *
     * @param profile The {@link VelocityProfile} to be processed.
     * @return true if the process is successful, false otherwise.
     */
    @Override
    public boolean process(@NotNull VelocityProfile profile) {
        DownloadableFileImage fileImage = dataBootstrap.getLastestBuild(FileImage.ImageType.VELOCITY).orElse(null);
        assert fileImage != null;
        String serverDirectory = profile.getServerName() + "/";
        if (!performBaseFileActions(serverDirectory, fileImage)) return false;
        writer.localizedWriteln("processor-velocity-creating-toml");
        File velocityToml = Paths.get(serverDirectory + "/velocity.toml").toFile();
        if (!FileUtility.saveResourceToFile("velocity/velocity.toml", velocityToml)) {
            writer.localizedWriteln("processor-velocity-creating-toml-error");
            return false;
        }
        FileReplacer.open(velocityToml)
                .replace("%server-port%", String.valueOf(profile.getServerPort()))
                .replace("%server-name%", String.valueOf(profile.getServerName()))
                .replace("%online-mode%", String.valueOf(profile.isOnlineMode()))
                .apply();
        writer.localizedWriteln("processor-creating-shell-script");
        File starterFile = Paths.get(serverDirectory + "/start.sh").toFile();
        if (!FileUtility.saveResourceToFile("start.sh", starterFile)) {
            writer.localizedWriteln("processor-creating-shell-script-error");
            return false;
        }
        FileReplacer.open(starterFile)
                .replace("%sever-jar%", fileImage.getCanonicalFileName())
                .replace("%ignore-java-version-flag%", "")
                .replace("%optimization-flags%", profile.getOptimizationShellFlags() != null ? profile.getOptimizationShellFlags().getFlags() : "")
                .apply();
        return true;
    }
}
