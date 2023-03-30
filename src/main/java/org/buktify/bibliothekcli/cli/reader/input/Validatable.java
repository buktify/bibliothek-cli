package org.buktify.bibliothekcli.cli.reader.input;

public interface Validatable {

    boolean isValid(String item);

    String getHint();
}
