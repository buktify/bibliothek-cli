package org.buktify.bibliothekcli.cli.reader.input.impl.extended;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.cli.reader.input.Validatable;
import org.buktify.bibliothekcli.cli.reader.input.impl.IntegerType;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PortType extends IntegerType implements Validatable {

    String PORT_REGEX = "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$";

    @Override
    public boolean isValid(String input) {
        return input.matches(PORT_REGEX);
    }

    @Override
    public String getHint() {
        return "[0-65535]";
    }
}
