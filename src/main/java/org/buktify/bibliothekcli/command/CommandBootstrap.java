package org.buktify.bibliothekcli.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.buktify.bibliothekcli.command.processor.CommandProcessor;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.util.RenderUtility;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${application.version}")
    @NonFinal
    String version;

    @Override
    public void run(String... args) throws Exception {
        RenderUtility.renderHeader();
        RenderUtility.printMessage("Booting... Receiving version data...");
        dataBootstrap.init();
        RenderUtility.printMessage("Done! Running version " + version);
        String line;
        RenderUtility.printEmpty();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!(line = reader.readLine()).equals("exit")) {
            String[] arguments = line.split(" ");
            commandProcessor.process(arguments[0], Arrays.stream(Arrays.copyOfRange(arguments, 1, arguments.length)).toList());
            RenderUtility.printEmpty();
        }
        RenderUtility.printMessage("Shutting down...");
        applicationContext.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
