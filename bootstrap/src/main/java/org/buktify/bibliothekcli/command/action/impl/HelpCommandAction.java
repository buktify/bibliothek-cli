package org.buktify.bibliothekcli.command.action.impl;

import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.writer.TerminalWriter;
import org.springframework.stereotype.Component;

@Component("helpAction")
public class HelpCommandAction implements CommandAction {
    TerminalReader reader;
    TerminalWriter writer;

    @Override
    public void execute() {

    }

}
