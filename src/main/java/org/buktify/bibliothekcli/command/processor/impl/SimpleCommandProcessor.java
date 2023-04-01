package org.buktify.bibliothekcli.command.processor.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.command.action.CommandAction;
import org.buktify.bibliothekcli.command.processor.CommandProcessor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SimpleCommandProcessor implements CommandProcessor, ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public void process(String command) {
        Object bean;
        try {
            bean = applicationContext.getBean(command + "Action");
        } catch (BeansException beansException) {
            System.out.println("Invalid command, try \"help\"");
            return;
        }
        if (bean instanceof CommandAction commandAction) {
            commandAction.execute();
            return;
        }
        System.out.println("Invalid command, try \"help\"");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
