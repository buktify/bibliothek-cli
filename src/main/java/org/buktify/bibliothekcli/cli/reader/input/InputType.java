package org.buktify.bibliothekcli.cli.reader.input;

import lombok.experimental.UtilityClass;
import org.buktify.bibliothekcli.cli.reader.input.impl.IntegerType;
import org.buktify.bibliothekcli.cli.reader.input.impl.StringType;
import org.buktify.bibliothekcli.cli.reader.input.impl.extended.LocaleType;
import org.buktify.bibliothekcli.cli.reader.input.impl.extended.OptionalType;
import org.buktify.bibliothekcli.cli.reader.input.impl.extended.PortType;
import org.buktify.bibliothekcli.cli.reader.input.impl.extended.ProductType;
import org.buktify.bibliothekcli.data.image.FileImage;

@UtilityClass
public class InputType {

    public final static Type<Integer> INTEGER = new IntegerType();
    public final static Type<String> STRING = new StringType();
    public final static Type<Integer> PORT = new PortType();
    public final static Type<String> OPTIONAL = new OptionalType();
    public final static Type<FileImage.ImageType> IMAGE_TYPE = new ProductType();
    public final static Type<String> LOCALE = new LocaleType();

}
