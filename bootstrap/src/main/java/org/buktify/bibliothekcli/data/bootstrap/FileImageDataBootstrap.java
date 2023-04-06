package org.buktify.bibliothekcli.data.bootstrap;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.buktify.bibliothekcli.data.bootstrap.exception.FileDownloadingException;
import org.buktify.bibliothekcli.data.bootstrap.response.BuildsResponse;
import org.buktify.bibliothekcli.data.bootstrap.response.ProjectVersionsResponse;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.buktify.cli.localization.Localization;
import org.buktify.cli.writer.TerminalWriter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FileImageDataBootstrap implements DataBootstrap, ApplicationContextAware {

    TerminalWriter terminalWriter;
    List<DownloadableFileImage> bootstrappedImages = new ArrayList<>();
    RestTemplate restTemplate = new RestTemplate();
    @NonFinal
    ConfigurableApplicationContext applicationContext;
    @NonFinal
    @Value("${application.api-url}")
    private String apiUrl;

    @Override
    public void init() {
        for (FileImage.ImageType imageType : FileImage.ImageType.values()) {
            terminalWriter.writeln(Objects.requireNonNull(Localization.localized("version-data-message")).replaceAll("%product%", imageType.name().toLowerCase()));
            try {
                List<String> versions = Objects.requireNonNull(restTemplate.getForObject(apiUrl + "projects/" + imageType.name().toLowerCase(), ProjectVersionsResponse.class)).getVersions();
                for (String version : versions) {
                    BuildsResponse buildsResponse = restTemplate.getForObject(apiUrl + "projects/" + imageType.name().toLowerCase() + "/versions/" + version + "/builds", BuildsResponse.class);
                    assert buildsResponse != null;
                    BuildsResponse.Build lastest = buildsResponse.getBuilds().get(0);
                    bootstrap(new DownloadableFileImage(version, imageType, lastest));
                }
            } catch (HttpClientErrorException exception) {
                terminalWriter.localizedWriteln("version-data-error");
                applicationContext.close();
                System.exit(0);
            }
        }
    }

    @Override
    @SneakyThrows(IOException.class)
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void download(@NotNull DownloadableFileImage downloadableFile, @NotNull File file) throws FileDownloadingException {
        /*
         * Creating parent directories and file if not exists
         */
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        file.createNewFile();
        ResponseEntity<byte[]> response;
        try {
            response = restTemplate.getForEntity(apiUrl + "projects/" + downloadableFile.getImageType().name().toLowerCase() + "/versions/" + downloadableFile.getVersion() + "/builds/" + downloadableFile.getLastestBuild().getBuild() + "/downloads/" + downloadableFile.getLastestBuild().getDownloads().getApplication().getName(), byte[].class);
        } catch (HttpClientErrorException exception) {
            throw new FileDownloadingException("Error downloading " + downloadableFile.getLastestBuild().getDownloads().getApplication().getName() + ". Could not connect to PaperMC servers");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(Objects.requireNonNull(response.getBody()));
        fileOutputStream.close();
        /*
         * Comparing checksum
         */
        String expectedChecksum = downloadableFile.getLastestBuild().getDownloads().getApplication().getSha256();
        String checksum = generateSHA256Checksum(file);
        if (!checksum.equals(expectedChecksum)) {
            file.delete();
            throw new FileDownloadingException("Error downloading " + downloadableFile.getLastestBuild().getDownloads().getApplication().getName() + ". Got " + checksum + "SHA256 but expected " + expectedChecksum);
        }
    }

    @SneakyThrows
    private String generateSHA256Checksum(@NotNull File file) {
        byte[] data = Files.readAllBytes(file.toPath());
        byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
        return new BigInteger(1, hash).toString(16);
    }

    @Override
    public void bootstrap(@NotNull DownloadableFileImage dataHolder) {
        bootstrappedImages.add(dataHolder);
    }

    @Override
    public List<DownloadableFileImage> getByType(FileImage.@NotNull ImageType imageType) {
        return bootstrappedImages.stream().filter(image -> image.getImageType().equals(imageType)).collect(Collectors.toList());
    }

    @Override
    public Optional<DownloadableFileImage> getByTypeAndVersion(FileImage.@NotNull ImageType imageType, @NotNull String version) {
        return bootstrappedImages.stream().filter(image -> image.getImageType().equals(imageType)).filter(image -> image.getVersion().equals(version)).findFirst();
    }

    @Override
    public Optional<DownloadableFileImage> getLastestBuild(FileImage.@NotNull ImageType imageType) {
        List<DownloadableFileImage> versionData = getByType(FileImage.ImageType.PAPER);
        if (versionData.isEmpty()) return Optional.empty();
        return Optional.of(versionData.get(versionData.size() - 1));
    }

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }


}
