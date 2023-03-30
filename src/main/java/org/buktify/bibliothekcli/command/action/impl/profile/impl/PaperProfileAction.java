package org.buktify.bibliothekcli.command.action.impl.profile.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.impl.SimpleTerminalReader;
import org.buktify.bibliothekcli.cli.reader.input.InputType;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.command.action.impl.profile.ProfileAction;
import org.buktify.bibliothekcli.profile.impl.PaperProfile;
import org.buktify.bibliothekcli.util.RenderUtility;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("initPaperAction")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaperProfileAction extends ProfileAction<PaperProfile> implements CommandAction {

    PaperProfile.PaperProfileBuilder builder = PaperProfile.builder();
    SimpleTerminalReader terminalReader;

    @Override
    public void execute() {
        System.out.println("Введите название вашего сервера.");
        System.out.println("Название сервера будет совпадать с названием директории, в которой сервер будет создан.");
        builder.serverName(terminalReader.read(InputType.STRING));
        terminalReader.processOptionalChoice("Желаете указать порт или оставить стандартный", () -> {
            Integer port = terminalReader.forceRead("Укажите порт, на котором будет стоять ваш сервер", InputType.PORT);
            builder.serverPort(port);
        });
    }

    @Override
    protected PaperProfile getProfile() {
        return null;
    }
}
