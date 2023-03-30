package org.buktify.bibliothekcli.cli.reader.input.impl.extended;

import lombok.SneakyThrows;
import org.buktify.bibliothekcli.cli.reader.input.Type;
import org.buktify.bibliothekcli.cli.reader.input.Validatable;
import org.buktify.bibliothekcli.profile.InitializationProfile;

import java.io.BufferedReader;

public class ProductType implements Type<InitializationProfile.InitializationType>, Validatable {

    @Override
    public boolean isValid(String item) {
        return item.equalsIgnoreCase("paper") || item.equalsIgnoreCase("velocity");
    }

    @Override
    public String getHint() {
        return "[paper/velocity]";
    }

    @Override
    @SneakyThrows
    public InitializationProfile.InitializationType secureGet(BufferedReader reader) {
        String line = reader.readLine();
        return isValid(line) ? InitializationProfile.InitializationType.valueOf(line.toUpperCase()) : null;
    }
}
