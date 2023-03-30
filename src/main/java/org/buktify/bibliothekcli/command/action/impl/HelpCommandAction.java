package org.buktify.bibliothekcli.command.action.impl;

import org.buktify.bibliothekcli.command.action.CommandAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("helpAction")
@Scope("prototype")
public class HelpCommandAction implements CommandAction {

    @Override
    public void execute() {
        System.out.println("");
    }

}
