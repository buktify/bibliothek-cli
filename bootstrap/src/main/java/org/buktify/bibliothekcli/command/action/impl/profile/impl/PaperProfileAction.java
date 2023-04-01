package org.buktify.bibliothekcli.command.action.impl.profile.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.reader.input.impl.extended.PaperVersionType;
import org.buktify.bibliothekcli.cli.writer.TerminalWriter;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.command.action.impl.profile.ProfileAction;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.profile.InitializationProfile;
import org.buktify.bibliothekcli.profile.impl.PaperProfile;
import org.buktify.bibliothekcli.profile.procsessor.impl.PaperProfileProcessor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;

@Component("initPaperAction")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaperProfileAction implements ProfileAction<PaperProfile>, CommandAction {

    PaperProfile.PaperProfileBuilder builder = PaperProfile.builder();
    TerminalReader reader;
    TerminalWriter writer;
    DataBootstrap dataBootstrap;
    PaperProfileProcessor profileProcessor;

    @Override
    public void execute() {
        String version = reader.localizedForceRead("paper-select-version", new PaperVersionType(dataBootstrap));
        builder.version(version);
        writer.localizedWriteln("paper-select-name");
        writer.localizedWriteln("paper-select-name-description");
        String serverName = reader.read(InputType.STRING);
        assert serverName != null;
        File serverDirectory = Paths.get(serverName).toFile();
        if (serverDirectory.exists()) {
            if (serverDirectory.listFiles() != null && Objects.requireNonNull(serverDirectory.listFiles()).length != 0) {
                String option = reader.localizedForceRead("directory-already-exists-with-files", InputType.OPTIONAL);
                if (option.equals("n")) return;
            }
            if (serverDirectory.isFile()) {
                writer.localizedWriteln("directory-is-a-file");
                return;
            }
            String option = reader.localizedForceRead("directory-already-exists", InputType.OPTIONAL);
            if (option.equals("n")) return;
        }
        builder.serverName(serverName);
        reader.processLocalizedOptionalChoice("paper-select-port-opt", () -> {
            Integer port = reader.localizedForceRead("paper-select-port", InputType.PORT);
            builder.serverPort(port);
        });
        reader.processLocalizedOptionalChoice("paper-select-connect-to-velocity-opt", () -> {
            String proxyFolder = reader.localizedRead("paper-select-connect-to-velocity", InputType.STRING);
            builder.proxyConnectionProfile(new PaperProfile.ProxyConnectionProfile(proxyFolder));
        });
        reader.processLocalizedOptionalChoice("paper-select-flags-opt", () -> {
            builder.optimizationShellFlags(InitializationProfile.OptimizationShellFlags.AIKAR);
        });
        profileProcessor.process(getProfile());
    }

    @Override
    public @NotNull PaperProfile getProfile() {
        return builder.build();
    }
}
