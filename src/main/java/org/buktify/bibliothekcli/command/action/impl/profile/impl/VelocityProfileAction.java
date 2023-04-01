package org.buktify.bibliothekcli.command.action.impl.profile.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.TerminalReader;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.command.action.impl.profile.ProfileAction;
import org.buktify.bibliothekcli.profile.impl.VelocityProfile;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component("initVelocityAction")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class VelocityProfileAction implements ProfileAction<VelocityProfile>, CommandAction {

    VelocityProfile.VelocityProfileBuilder builder = VelocityProfile.builder();
    TerminalReader reader;

    @Override
    public void execute() {

    }

    @Override
    public @NotNull VelocityProfile getProfile() {
        return null;
    }
}
