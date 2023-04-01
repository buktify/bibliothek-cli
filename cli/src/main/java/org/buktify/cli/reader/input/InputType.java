package org.buktify.cli.reader.input;

import lombok.experimental.UtilityClass;
import org.buktify.cli.reader.input.impl.*;

@UtilityClass
public class InputType {

    public final static Type<Integer> INTEGER = new IntegerType();
    public final static Type<String> STRING = new StringType();
    public final static Type<Integer> PORT = new PortType();
    public final static Type<String> OPTIONAL = new OptionalType();
    public final static Type<String> LOCALE = new LocaleType();

}
