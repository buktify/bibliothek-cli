package org.buktify.bibliothekcli.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.buktify.bibliothekcli.command.processor.CommandProcessor;
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
    @NonFinal
    @Value("${application.version}")
    String version;
    @NonFinal
    ConfigurableApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("""
                    __    _ __    ___       __  __         __              ___\s
                   / /_  (_) /_  / (_)___  / /_/ /_  ___  / /__      _____/ (_)
                  / __ \\/ / __ \\/ / / __ \\/ __/ __ \\/ _ \\/ //_/_____/ ___/ / /\s
                 / /_/ / / /_/ / / / /_/ / /_/ / / /  __/ ,< /_____/ /__/ / / \s
                /_.___/_/_.___/_/_/\\____/\\__/_/ /_/\\___/_/|_|      \\___/_/_/""");
        System.out.println("                              Running version " + version);
        System.out.println("Enter command");
        System.out.print("> ");
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (!(line = reader.readLine()).equals("exit")) {
            String[] arguments = line.split(" ");
            commandProcessor.process(arguments[0], Arrays.stream(Arrays.copyOfRange(arguments, 1, arguments.length)).toList());
            System.out.print("> ");
        }
        applicationContext.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
