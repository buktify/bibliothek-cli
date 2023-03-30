package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.impl.SimpleTerminalReader;
import org.buktify.bibliothekcli.cli.reader.input.InputType;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.command.action.impl.profile.impl.PaperProfileAction;
import org.buktify.bibliothekcli.command.action.impl.profile.impl.VelocityProfileAction;
import org.buktify.bibliothekcli.profile.InitializationProfile;
import org.buktify.bibliothekcli.util.RenderUtility;
import org.springframework.stereotype.Component;

@Component("initAction")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InitCommandAction implements CommandAction {

    SimpleTerminalReader terminalReader;
    PaperProfileAction paperProfileAction;
    VelocityProfileAction velocityProfileAction;

    @Override
    public void execute() {
        System.out.println("Хорошо, давайте начнём конфигурировать профиль.");
        InitializationProfile.InitializationType initializationType = terminalReader.forceRead("Выберите, что хотите установить... ", InputType.PRODUCT);
        switch (initializationType) {
            case PAPER -> paperProfileAction.execute();
            case VELOCITY -> velocityProfileAction.execute();
        }
    }
}
