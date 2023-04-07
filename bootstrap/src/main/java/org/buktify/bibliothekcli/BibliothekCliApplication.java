package org.buktify.bibliothekcli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliothekCliApplication {

    public static void main(String[] args) {
        new SpringApplication(BibliothekCliApplication.class).run(args);
    }

}
