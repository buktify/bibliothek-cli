package org.buktify.bibliothekcli.configuration;

import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.TerminalReaderImpl;
import org.buktify.cli.writer.TerminalWriter;
import org.buktify.cli.writer.TerminalWriterImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Value("${application.prompt-prefix}")
    private String PROMPT_PREFIX;

    @Bean
    public TerminalReader terminalReader() {
        return new TerminalReaderImpl(terminalWriter());
    }

    @Bean
    public TerminalWriter terminalWriter() {
        return new TerminalWriterImpl(PROMPT_PREFIX);
    }
}
