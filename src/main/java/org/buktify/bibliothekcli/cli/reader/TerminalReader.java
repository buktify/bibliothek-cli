package org.buktify.bibliothekcli.cli.reader;

import org.buktify.bibliothekcli.cli.reader.input.Type;

public interface TerminalReader {

    <T> T read(String request, Type<T> type);

    <T> T read(Type<T> type);

    <T> T forceRead(String request, Type<T> type);

    void processOptionalChoice(String request, Runnable optional);

    void processYesNoChoice(String request, Runnable positive, Runnable negative);
}
