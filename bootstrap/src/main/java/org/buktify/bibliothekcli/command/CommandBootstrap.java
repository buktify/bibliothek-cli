package org.buktify.bibliothekcli.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.buktify.bibliothekcli.command.processor.CommandProcessor;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.input.InputType;
import org.buktify.cli.writer.TerminalWriter;
import org.buktify.localization.Localization;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Objects;

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
    @NonFinal
    @Value("${application.version}")
    String version;

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
        writer.write(Objects.requireNonNull(Localization.localized("booting-ok")).replaceAll("%version%", version));
        String line;
        while (!(line = reader.forceRead(InputType.STRING)).equals("exit")) {
            String[] arguments = line.split(" ");
            commandProcessor.process(arguments[0]);
        }
        writer.localizedWrite("shutting-down");
        applicationContext.close();
    }

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
