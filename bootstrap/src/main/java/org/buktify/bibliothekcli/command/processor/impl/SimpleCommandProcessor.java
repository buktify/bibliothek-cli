package org.buktify.bibliothekcli.command.processor.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.command.processor.CommandProcessor;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Simple realization of {@link CommandProcessor}, which uses application context
 * for handling implemented command
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SimpleCommandProcessor implements CommandProcessor, ApplicationContextAware {

    TerminalWriter writer;
    @NonFinal
    ApplicationContext applicationContext;

    /**
     * Processes the specified command using the search application context
     * and perform the appropriate action.
     *
     * @param command string representing the command to process
     */
    @Override
    public void process(@NotNull String command) {
        Object bean;
        try {
            bean = applicationContext.getBean(command + "Action");
        } catch (BeansException beansException) {
            writer.localizedWriteln("invalid-command");
            return;
        }
        if (bean instanceof CommandAction commandAction) {
            commandAction.execute();
            return;
        }
        writer.localizedWriteln("invalid-command");
    }

    /**
     * Sets the application context to use this class.
     * Used for searching for bean that implements given command acton.
     *
     * @param applicationContext the application context in which this class will be used
     * @throws BeansException if an error occurs while getting the application context
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
