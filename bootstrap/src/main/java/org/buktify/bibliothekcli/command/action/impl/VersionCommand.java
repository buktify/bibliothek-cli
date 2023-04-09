package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.configuration.ApplicationProperties;
import org.buktify.cli.localization.Localization;
import org.buktify.cli.writer.TerminalWriter;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * This class represents an action for version command
 */
@Component("versionAction")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VersionCommand implements CommandAction {

    TerminalWriter terminalWriter;
    ApplicationProperties applicationProperties;

    /**
     * This method displays current application's version and
     * checks if an update available in GitHub releases.
     */
    @Override
    public void execute() {
        terminalWriter.writeln(Objects.requireNonNull(Localization.localized("current-version")).replaceAll("%version%", applicationProperties.getCurrentVersion()));
        terminalWriter.writeln(Objects.requireNonNull(Localization.localized("lastest-version")).replaceAll("%version%", (applicationProperties.getLastestVersion()) != null ? applicationProperties.getCurrentVersion() : Objects.requireNonNull(Localization.localized("release-not-found"))));
    }
}
