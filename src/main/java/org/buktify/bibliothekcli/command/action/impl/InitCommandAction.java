package org.buktify.bibliothekcli.command.action.impl;

import org.buktify.bibliothekcli.command.action.CommandAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("initAction")
@Scope("prototype")
public class InitCommandAction implements CommandAction {

    @Override
    public void execute(List<String> params) {

    }
}
