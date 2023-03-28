package org.buktify.bibliothekcli.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.buktify.bibliothekcli.cli.reader.impl.SimpleTerminalReader;
import org.buktify.bibliothekcli.cli.writer.impl.SimpleTerminalWriter;
import org.buktify.bibliothekcli.command.processor.CommandProcessor;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.util.RenderUtility;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommandBootstrap implements CommandLineRunner, ApplicationContextAware {

    CommandProcessor commandProcessor;
    DataBootstrap dataBootstrap;
    @NonFinal
    ConfigurableApplicationContext applicationContext;

    SimpleTerminalWriter writer = new SimpleTerminalWriter();
    SimpleTerminalReader reader = new SimpleTerminalReader();

    @Override
    public void run(String... args) throws Exception {
        RenderUtility.renderHeader();
        System.out.println("Booting... Receiving version data...");
        RenderUtility.clearScreen();
        dataBootstrap.init();
        RenderUtility.renderHeader();
        String line;
        System.out.print("bibliothek-cli> ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!(line = reader.readLine()).equals("exit")) {
            String[] arguments = line.split(" ");
            commandProcessor.process(arguments[0], Arrays.stream(Arrays.copyOfRange(arguments, 1, arguments.length)).toList());
            System.out.print("bibliothek-cli> ");
        }
        System.out.println("Shutting down...");
        applicationContext.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
