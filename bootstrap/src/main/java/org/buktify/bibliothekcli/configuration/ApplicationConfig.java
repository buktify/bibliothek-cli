package org.buktify.bibliothekcli.configuration;

import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.TerminalReaderImpl;
import org.buktify.cli.writer.TerminalWriter;
import org.buktify.cli.writer.TerminalWriterImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that defines the beans used by the application.
 */
@Configuration
public class ApplicationConfig {

    @Value("${application.prompt-prefix}")
    private String PROMPT_PREFIX;

    /**
     * Creates a new instance of {@link TerminalReader} that uses the configured {@link TerminalWriterImpl}.
     *
     * @return a new instance of {@link TerminalReader}
     */
    @Bean
    public TerminalReader terminalReader() {
        return new TerminalReaderImpl(terminalWriter());
    }

    /**
     * Creates a new instance of {@link TerminalWriter} with the configured prompt prefix.
     *
     * @return a new instance of {@link TerminalWriter}
     */
    @Bean
    public TerminalWriter terminalWriter() {
        return new TerminalWriterImpl(PROMPT_PREFIX);
    }
}