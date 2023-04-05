package org.buktify.bibliothekcli.profile.procsessor.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.buktify.bibliothekcli.data.bootstrap.DataBootstrap;
import org.buktify.bibliothekcli.data.bootstrap.FileImageDataBootstrap;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.buktify.bibliothekcli.profile.impl.PaperProfile;
import org.buktify.bibliothekcli.profile.procsessor.ProfileProcessor;
import org.buktify.bibliothekcli.util.FileReplacer;
import org.buktify.bibliothekcli.util.FileUtility;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PaperProfileProcessor implements ProfileProcessor<PaperProfile> {

    TerminalWriter writer;
    DataBootstrap dataBootstrap;

    @Override
    @SneakyThrows
    public boolean process(@NotNull PaperProfile profile) {
        String serverDirectory = profile.getServerName() + "/";
        File serverDirectoryFile = Paths.get(serverDirectory).toFile();
        writer.writeln("Создаём директорию");
        Files.createDirectories(serverDirectoryFile.toPath());
        writer.writeln("Скачиваем ядро сервера");
        DownloadableFileImage fileImage = dataBootstrap.getByTypeAndVersion(profile.getImageType(), profile.getVersion()).get();
        try {
            File serverJarFile = Paths.get(serverDirectory + fileImage.getCanonicalFileName()).toFile();
            serverJarFile.createNewFile();
            dataBootstrap.download(fileImage, serverJarFile);
        } catch (FileImageDataBootstrap.FileDownloadingException exception) {
            writer.writeln(exception.getMessage());
            return false;
        }
        writer.writeln("Создаём и подписываем eula.txt");
        File eulaFile = Paths.get(serverDirectory + "/eula.txt").toFile();
        if (!FileUtility.saveResourceToFile("paper/eula.txt", eulaFile)) {
            writer.writeln("Ошибка при создании eula.txt");
            return false;
        }
        writer.writeln("Создаём server.properties");
        File serverPropertiesFile = Paths.get(serverDirectory + "/server.properties").toFile();
        if (!FileUtility.saveResourceToFile("paper/server.properties", serverPropertiesFile)) {
            writer.writeln("Ошибка при создании server.properties");
            return false;
        }
        if (profile.getServerPort() != 25565) {
            FileReplacer.open(serverPropertiesFile)
                    .replace("%server-port%", String.valueOf(profile.getServerPort()))
                    .replace("%online-mode%", String.valueOf(profile.isOnlineMode()))
                    .apply();
        }
        writer.writeln("Создаём start.sh");
        File starterFile = Paths.get(serverDirectory + "/start.sh").toFile();
        if (!FileUtility.saveResourceToFile("start.sh", starterFile)) {
            writer.writeln("Ошибка при создании start.sh");
            return false;
        }
        FileReplacer.open(starterFile).replace("%sever-jar%", fileImage.getCanonicalFileName()).replace("%ignore-java-version-flag%", profile.getVersion().contains("1.16") ? "-DPaper.IgnoreJavaVersion=true" : "").replace("%optimization-flags%", profile.getOptimizationShellFlags() != null ? profile.getOptimizationShellFlags().getFlags() : "").apply();
        if (profile.getProxyConnectionProfile() != null) {
            writer.writeln("Подключаем сервер к Velocity");
            File velocityFolder = Paths.get(profile.getProxyConnectionProfile().getProxyFolder()).toFile();
            if (!velocityFolder.exists() || velocityFolder.isFile()) {
                writer.writeln("Ошибка при подключении к Velocity: папка прокси не найлена");
                return false;
            }
            File velocityToml = Paths.get(velocityFolder + "/velocity.toml").toFile();
            if (!velocityFolder.exists() || !velocityToml.isFile()) {
                writer.writeln("Ошибка при подключении к Velocity: velocity.toml не найден");
                return false;
            }
            addServer(profile.getServerName() + " = \"127.0.0.1:" + profile.getServerPort() + "\"", velocityToml);
            File velocitySecret = Paths.get(velocityFolder + "/forwarding.secret").toFile();
            if (!velocitySecret.exists() || !velocitySecret.isFile()) {
                writer.writeln("Ошибка при подключении к Velocity: forwarding.secret не найден");
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

    @SneakyThrows
    private void addServer(@NotNull String serverData, @NotNull File velocityConfigFile) {
        ArrayList<String> lines = new ArrayList<>(Arrays.stream(FileUtils.readFileToString(velocityConfigFile, "UTF-8").split("\\r?\\n")).toList());
        boolean serverSection = false;
        for (int i = 0; i < lines.size() - 1; i++) {
            String line = lines.get(i);
            if (line.equals("[servers]")) serverSection = true;
            if (serverSection) {
                if (line.isEmpty()) {
                    lines.add(i, serverData);
                    break;
                }
            }
        }
        FileUtils.writeLines(velocityConfigFile, "UTF-8", lines, "\n");
    }
}
