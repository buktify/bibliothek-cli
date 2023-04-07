package org.buktify.bibliothekcli.command.action.impl;

import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.writer.TerminalWriter;
import org.springframework.stereotype.Component;

/**
 * Represents a command action for displaying help information.
 */
@Component("helpAction")
public class HelpCommandAction implements CommandAction {
    TerminalReader reader;
    TerminalWriter writer;

    /**
     * Executes the help command.
     * <p>
     * This method provides a specific help information
     */
    @Override
    public void execute() {

    }

}
