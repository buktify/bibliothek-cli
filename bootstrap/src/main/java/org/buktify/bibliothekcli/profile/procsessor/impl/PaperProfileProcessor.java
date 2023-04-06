package org.buktify.bibliothekcli.profile.procsessor.impl;

import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.IOUtils;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.buktify.bibliothekcli.profile.impl.PaperProfile;
import org.buktify.bibliothekcli.profile.procsessor.AbstractProfileProcessor;
import org.buktify.bibliothekcli.profile.procsessor.ProfileProcessor;
import org.buktify.bibliothekcli.util.FileReplacer;
import org.buktify.bibliothekcli.util.FileUtility;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PaperProfileProcessor extends AbstractProfileProcessor implements ProfileProcessor<PaperProfile> {

    public PaperProfileProcessor(TerminalWriter writer, DataBootstrap dataBootstrap) {
        super(writer, dataBootstrap);
    }

    @Override
    @SneakyThrows
    public boolean process(@NotNull PaperProfile profile) {
        DownloadableFileImage fileImage = dataBootstrap.getByTypeAndVersion(profile.getImageType(), profile.getVersion()).orElse(null);
        assert fileImage != null;
        String serverDirectory = profile.getServerName() + "/";
        if (!performBaseFileActions(serverDirectory, fileImage)) return false;
        writer.localizedWriteln("processor-paper-creating-eula");
        File eulaFile = Paths.get(serverDirectory + "/eula.txt").toFile();
        if (!FileUtility.saveResourceToFile("paper/eula.txt", eulaFile)) {
            writer.localizedWriteln("processor-paper-creating-eula");
            return false;
        }
        writer.localizedWriteln("processor-paper-creating-properties");
        File serverPropertiesFile = Paths.get(serverDirectory + "/server.properties").toFile();
        if (!FileUtility.saveResourceToFile("paper/server.properties", serverPropertiesFile)) {
            writer.localizedWriteln("processor-paper-creating-properties-error");
            return false;
        }
        if (profile.getServerPort() != 25565) {
            FileReplacer.open(serverPropertiesFile)
                    .replace("%server-port%", String.valueOf(profile.getServerPort()))
                    .replace("%online-mode%", String.valueOf(profile.isOnlineMode()))
                    .apply();
        }
        writer.localizedWriteln("processor-paper-creating-shell-script");
        File starterFile = Paths.get(serverDirectory + "/start.sh").toFile();
        if (!FileUtility.saveResourceToFile("start.sh", starterFile)) {
            writer.localizedWriteln("processor-paper-creating-shell-script-error");
            return false;
        }
        FileReplacer.open(starterFile)
                .replace("%sever-jar%", fileImage.getCanonicalFileName())
                .replace("%ignore-java-version-flag%", profile.getVersion().contains("1.16") ? "-DPaper.IgnoreJavaVersion=true" : "")
                .replace("%optimization-flags%", profile.getOptimizationShellFlags() != null ? profile.getOptimizationShellFlags().getFlags() : "")
                .apply();
        if (profile.getProxyConnectionProfile() != null) {
            writer.localizedWriteln("processor-paper-connecting-to-proxy");
            File velocityFolder = Paths.get(profile.getProxyConnectionProfile().getProxyFolder()).toFile();
            if (!velocityFolder.exists() || velocityFolder.isFile()) {
                writer.localizedWriteln("processor-paper-connecting-to-proxy-folder-error");
                return false;
            }
            File velocityToml = Paths.get(velocityFolder + "/velocity.toml").toFile();
            if (!velocityFolder.exists() || !velocityToml.isFile()) {
                writer.localizedWriteln("processor-paper-connecting-to-proxy-toml-error");
                return false;
            }
            addServer(profile.getServerName() + " = \"127.0.0.1:" + profile.getServerPort() + "\"", velocityToml);
            File velocitySecret = Paths.get(velocityFolder + "/forwarding.secret").toFile();
            if (!velocitySecret.exists() || !velocitySecret.isFile()) {
                writer.writeln("processor-paper-connecting-to-proxy-secret-error");
                return false;
            }
            String secret = IOUtils.toString(velocitySecret.toURI(), Charset.defaultCharset());
            File paperYml = Paths.get(serverDirectory + "/paper.yml").toFile();
            FileUtility.saveResourceToFile("paper/paper.yml", paperYml);
            FileReplacer.open(paperYml)
                    .replace("%online_mode%", String.valueOf(profile.isOnlineMode()))
                    .replace("%forwarding_secret%", secret)
                    .apply();
        }
        return true;
    }

}
