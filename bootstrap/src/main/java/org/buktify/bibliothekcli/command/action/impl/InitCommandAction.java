package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.bibliothekcli.cli.writer.TerminalWriter;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.command.action.impl.profile.impl.PaperProfileAction;
import org.buktify.bibliothekcli.command.action.impl.profile.impl.VelocityProfileAction;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.springframework.stereotype.Component;

@Component("initAction")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InitCommandAction implements CommandAction {

    TerminalReader reader;
    TerminalWriter writer;
    PaperProfileAction paperProfileAction;
    VelocityProfileAction velocityProfileAction;

    @Override
    public void execute() {
        writer.localizedWriteln("init-start");
        FileImage.ImageType initializationType = reader.localizedForceRead("init-select-product", InputType.IMAGE_TYPE);
        switch (initializationType) {
            case PAPER -> paperProfileAction.execute();
            case VELOCITY -> velocityProfileAction.execute();
        }
    }
}
