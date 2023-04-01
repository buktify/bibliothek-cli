package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.input.ProductType;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.writer.TerminalWriter;
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
        FileImage.ImageType initializationType = reader.localizedForceRead("init-select-product", new ProductType());
        switch (initializationType) {
            case PAPER -> paperProfileAction.execute();
            case VELOCITY -> velocityProfileAction.execute();
        }
    }
}
