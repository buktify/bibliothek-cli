package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.command.action.AbstractProfileAction;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.profile.InitializationProfile;
import org.buktify.bibliothekcli.profile.impl.VelocityProfile;
import org.buktify.bibliothekcli.profile.procsessor.impl.VelocityProfileProcessor;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.writer.TerminalWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

@Component("initVelocityAction")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VelocityProfileAction extends AbstractProfileAction implements CommandAction {

    VelocityProfile.VelocityProfileBuilder builder = VelocityProfile.builder();
    VelocityProfileProcessor profileProcessor;

    public VelocityProfileAction(TerminalReader reader, TerminalWriter writer, VelocityProfileProcessor profileProcessor) {
        super(reader, writer);
        this.profileProcessor = profileProcessor;
    }

    @Override
    public void execute() {
        writer.localizedWriteln("server-select-name");
        writer.localizedWriteln("server-select-name-description");
        String serverName = reader.forceRead(InputType.STRING);
        File serverDirectory = Paths.get(serverName).toFile();
        if (!validateServerDirectory(serverDirectory)) return;
        builder.serverName(serverName);
        builder.onlineMode(reader.localizedForceRead("server-select-online-mode", InputType.OPTIONAL).equals("y"));
        reader.processLocalizedOptionalChoice("server-select-port-opt", () -> {
            Integer port = reader.localizedForceRead("server-select-port", InputType.PORT);
            builder.serverPort(port);
        });
        reader.processLocalizedOptionalChoice("velocity-select-flags-opt", () -> builder.optimizationShellFlags(InitializationProfile.OptimizationShellFlags.PROXY));
        profileProcessor.process(builder.build());
    }
}
