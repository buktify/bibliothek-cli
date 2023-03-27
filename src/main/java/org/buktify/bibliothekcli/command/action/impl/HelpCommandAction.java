package org.buktify.bibliothekcli.command.action.impl;

import org.buktify.bibliothekcli.command.action.CommandAction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("helpAction")
@Scope("prototype")
public class HelpCommandAction implements CommandAction {

    @Override
    public void execute(List<String> params) {
        System.out.println("lox");
    }
}
