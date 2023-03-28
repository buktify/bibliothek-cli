package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.impl.AbstractTerminalReader;
import org.buktify.bibliothekcli.cli.reader.impl.SimpleTerminalReader;
import org.buktify.bibliothekcli.cli.writer.impl.AbstractTerminalWriter;
import org.buktify.bibliothekcli.cli.writer.impl.SimpleTerminalWriter;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.profile.impl.PaperInitializationProfile;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("initAction")
@Scope("prototype")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InitCommandAction implements CommandAction {

    SimpleTerminalWriter writer = new SimpleTerminalWriter();
    SimpleTerminalReader reader = new SimpleTerminalReader();


    @Override
    public void execute(List<String> params) {
        PaperInitializationProfile paperInitializationProfile = new PaperInitializationProfile();
        writer.write("Введите название сервера...");

        String serverName = reader.read();
        writer.write(serverName);
    }
}
