package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.command.action.AbstractProfileAction;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.input.PaperVersionType;
import org.buktify.bibliothekcli.profile.InitializationProfile;
import org.buktify.bibliothekcli.profile.impl.PaperProfile;
import org.buktify.bibliothekcli.profile.procsessor.impl.PaperProfileProcessor;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.writer.TerminalWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

@Component("initPaperAction")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaperProfileAction extends AbstractProfileAction implements CommandAction {

    PaperProfile.PaperProfileBuilder builder = PaperProfile.builder();
    DataBootstrap dataBootstrap;
    PaperProfileProcessor profileProcessor;

    public PaperProfileAction(TerminalReader reader, TerminalWriter writer, DataBootstrap dataBootstrap, PaperProfileProcessor profileProcessor) {
        super(reader, writer);
        this.dataBootstrap = dataBootstrap;
        this.profileProcessor = profileProcessor;
    }


    @Override
    public void execute() {
        String version = reader.localizedForceRead("paper-select-version", new PaperVersionType(dataBootstrap));
        builder.version(version);
        writer.localizedWriteln("paper-select-name");
        writer.localizedWriteln("paper-select-name-description");
        String serverName = reader.forceRead(InputType.STRING);
        File serverDirectory = Paths.get(serverName).toFile();
        if (!validateServerDirectory(serverDirectory)) return;
        builder.serverName(serverName);
        builder.onlineMode(reader.localizedForceRead("server-select-online-mode", InputType.OPTIONAL).equals("y"));
        reader.processLocalizedOptionalChoice("server-select-port-opt", () -> {
            Integer port = reader.localizedForceRead("server-select-port", InputType.PORT);
            builder.serverPort(port);
        });
        reader.processLocalizedOptionalChoice("paper-select-connect-to-velocity-opt", () -> {
            String proxyFolder = reader.localizedRead("paper-select-connect-to-velocity", InputType.STRING);
            builder.proxyConnectionProfile(new PaperProfile.ProxyConnectionProfile(proxyFolder));
        });
        reader.processLocalizedOptionalChoice("paper-select-flags-opt", () -> builder.optimizationShellFlags(InitializationProfile.OptimizationShellFlags.AIKAR));
        profileProcessor.process(builder.build());
    }
}
