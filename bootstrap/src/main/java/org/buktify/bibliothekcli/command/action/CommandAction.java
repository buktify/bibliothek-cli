package org.buktify.bibliothekcli.command.action;

/**
 * A functional interface representing an action to be performed for a command.
 * <p>
 * This interface can be used to define the behavior of a command, allowing it to be easily passed around
 * and reused in different contexts.
 * <p>
 * The {@code execute} method performs the action associated with this command.
 */
@FunctionalInterface
public interface CommandAction {

    /**
     * Performs the action associated with this command.
     */
    void execute();

}