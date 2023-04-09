package org.buktify.bibliothekcli.configuration;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.bootstrap.response.ReleaseResponse;
import org.buktify.cli.reader.TerminalReader;
import org.buktify.cli.reader.TerminalReaderImpl;
import org.buktify.cli.writer.TerminalWriter;
import org.buktify.cli.writer.TerminalWriterImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class that defines the beans used by the application.
 */
@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationConfig {

    @Value("${application.prompt-prefix}")
    String PROMPT_PREFIX;
    @Value("${application.version}")
    String version;

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

    /**
     * @return
     */
    @Bean
    public ApplicationProperties ApplicationProperties() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ReleaseResponse response = restTemplate.getForObject("https://api.github.com/repos/Buktify/bibliothek-cli/releases/latest", ReleaseResponse.class);
            assert response != null;
            return new ApplicationProperties(version, response.getTagName());
        } catch (Exception ignored) {
            return new ApplicationProperties(version, null);
        }
    }
}