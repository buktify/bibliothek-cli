package org.buktify.bibliothekcli.command.processor;

import org.jetbrains.annotations.NotNull;

/**
 * Base interface for command handling
 */
public interface CommandProcessor {

    /**
     * Handles given command
     *
     * @param command command string
     */
    void process(@NotNull String command);

}
