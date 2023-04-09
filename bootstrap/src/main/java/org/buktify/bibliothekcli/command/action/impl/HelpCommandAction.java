package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.cli.writer.TerminalWriter;
import org.springframework.stereotype.Component;

/**
 * Represents a command action for displaying help information.
 */
@Component("helpAction")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class HelpCommandAction implements CommandAction {

    TerminalWriter writer;

    /**
     * Executes the help command.
     * <p>
     * This method provides a specific help information
     */
    @Override
    public void execute() {
        writer.localizedWriteln("help-message");
    }

}
