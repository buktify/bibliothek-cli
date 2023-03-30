package org.buktify.bibliothekcli.command.action.impl.profile.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.impl.SimpleTerminalReader;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.command.action.impl.profile.ProfileAction;
import org.buktify.bibliothekcli.profile.impl.VelocityProfile;
import org.springframework.stereotype.Component;

@Component("initVelocityAction")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VelocityProfileAction extends ProfileAction<VelocityProfile> implements CommandAction {

    VelocityProfile.VelocityProfileBuilder builder = VelocityProfile.builder();
    SimpleTerminalReader terminalReader;

    @Override
    public void execute() {

    }

    @Override
    protected VelocityProfile getProfile() {
        return null;
    }
}
