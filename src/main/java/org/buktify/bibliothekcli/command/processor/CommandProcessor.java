package org.buktify.bibliothekcli.command.processor;

import java.util.List;

public interface CommandProcessor {

    void process(String command, List<String> arguments);

}
