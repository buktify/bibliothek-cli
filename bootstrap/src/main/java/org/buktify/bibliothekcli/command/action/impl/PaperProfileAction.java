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
import org.buktify.bibliothekcli.profile.procsessor.impl.VelocityProfileProcessor;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.writer.TerminalWriter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;

/**
 * This class represents an action for initializing a Paper server profile.
 */
@Component("initPaperAction")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaperProfileAction extends AbstractProfileAction implements CommandAction {

    PaperProfile.PaperProfileBuilder builder = PaperProfile.builder();
    DataBootstrap dataBootstrap;
    PaperProfileProcessor profileProcessor;

    /**
     * Constructs a new instance of the PaperProfileAction.
     *
     * @param reader           the {@link TerminalReader} to use for reading user input
     * @param writer           the {@link TerminalWriter} to use for writing output to the terminal
     * @param dataBootstrap    the {@link DataBootstrap} to use for bootstrapping data
     * @param profileProcessor the {@link VelocityProfileProcessor} to use for processing given profile
     */
    public PaperProfileAction(TerminalReader reader, TerminalWriter writer, DataBootstrap dataBootstrap, PaperProfileProcessor profileProcessor) {
        super(reader, writer);
        this.dataBootstrap = dataBootstrap;
        this.profileProcessor = profileProcessor;
    }

    /**
     * Executes the action for initializing a Paper server profile.
     */
    @Override
    public void execute() {
        String version = reader.localizedForceRead("paper-select-version", new PaperVersionType(dataBootstrap));
        builder.version(version);
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
        reader.processLocalizedOptionalChoice("paper-select-connect-to-velocity-opt", () -> {
            String proxyFolder = reader.localizedRead("paper-select-connect-to-velocity", InputType.STRING);
            builder.proxyConnectionProfile(new PaperProfile.ProxyConnectionProfile(proxyFolder));
        });
        reader.processLocalizedOptionalChoice("paper-select-flags-opt", () -> builder.optimizationShellFlags(InitializationProfile.OptimizationShellFlags.AIKAR));
        PaperProfile paperProfile = builder.build();
        if (!validateProfile(paperProfile)) return;
        writer.localizedWriteln("processor-starting");
        profileProcessor.process(paperProfile);
    }
}
