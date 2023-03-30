package org.buktify.bibliothekcli.cli.reader.input;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.Scanner;

public interface Type<T> {

    T secureGet(BufferedReader reader);
}
