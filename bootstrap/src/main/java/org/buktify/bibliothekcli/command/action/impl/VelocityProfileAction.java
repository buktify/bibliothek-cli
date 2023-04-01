package org.buktify.bibliothekcli.command.action.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.profile.impl.VelocityProfile;
import org.buktify.cli.reader.TerminalReader;
import org.springframework.stereotype.Component;

@Component("initVelocityAction")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VelocityProfileAction implements CommandAction {

    VelocityProfile.VelocityProfileBuilder builder = VelocityProfile.builder();
    TerminalReader reader;

    @Override
    public void execute() {

    }
}
