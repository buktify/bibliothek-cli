package org.buktify.bibliothekcli.command.action;

import java.util.List;

public interface CommandAction {

    void execute(List<String> params);

}
