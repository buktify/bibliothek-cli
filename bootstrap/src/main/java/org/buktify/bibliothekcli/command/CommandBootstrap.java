package org.buktify.bibliothekcli.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.buktify.bibliothekcli.command.processor.CommandProcessor;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.cli.localization.Localization;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Main class, that handles command line input
 * and gives line to {@link CommandProcessor} for continuous
 * handling
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommandBootstrap implements CommandLineRunner, ApplicationContextAware {

    TerminalReader reader;
    TerminalWriter writer;
    CommandProcessor commandProcessor;
    DataBootstrap dataBootstrap;
    @NonFinal
    ConfigurableApplicationContext applicationContext;


    @Override
    public void run(String... args) {
        renderHeader();
        if (!Localization.init(reader.forceRead("Select locale", InputType.LOCALE))) {
            writer.writeln("Error during initializing localization");
            applicationContext.close();
            return;
        }
        writer.localizedWriteln("booting-message");
        dataBootstrap.init();
        writer.localizedWriteln("booting-ok");
        String line;
        while (!(line = reader.forceRead(InputType.STRING)).equals("exit")) {
            String[] arguments = line.split(" ");
            commandProcessor.process(arguments[0]);
        }
        writer.localizedWriteln("shutting-down");
        applicationContext.close();
    }

    /**
     * Sets the application context to use this class.
     * Mainly used for correct application shutdown.
     *
     * @param applicationContext the application context in which this class will be used
     * @throws BeansException if an error occurs while getting the application context
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }

    private void renderHeader() {
        writer.writeln("""
                    __    _ __    ___       __  __         __              ___\s
                   / /_  (_) /_  / (_)___  / /_/ /_  ___  / /__      _____/ (_)
                  / __ \\/ / __ \\/ / / __ \\/ __/ __ \\/ _ \\/ //_/_____/ ___/ / /\s
                 / /_/ / / /_/ / / / /_/ / /_/ / / /  __/ ,< /_____/ /__/ / / \s
                /_.___/_/_.___/_/_/\\____/\\__/_/ /_/\\___/_/|_|      \\___/_/_/
                """);
    }
}
