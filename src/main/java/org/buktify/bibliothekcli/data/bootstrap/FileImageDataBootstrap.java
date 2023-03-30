package org.buktify.bibliothekcli.data.bootstrap;

import com.google.common.hash.Hashing;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.bootstrap.exception.FIleDownloadingException;
import org.buktify.bibliothekcli.data.bootstrap.response.BuildsResponse;
import org.buktify.bibliothekcli.data.bootstrap.response.ProjectVersionsResponse;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FileImageDataBootstrap implements DataBootstrap, ApplicationContextAware {

    final List<DownloadableFileImage> bootstrappedImages = new ArrayList<>();
    final RestTemplate restTemplate = new RestTemplate();
    ConfigurableApplicationContext applicationContext;
    @Value("${application.api-url}")
    private String apiUrl;

    @Override
    public void init() {
        for (FileImage.ImageType imageType : FileImage.ImageType.values()) {
            System.out.println("Receiving " + imageType.name().toLowerCase() + " version an build data");
            try {
                List<String> versions = Objects.requireNonNull(restTemplate.getForObject(apiUrl + "projects/" + imageType.name().toLowerCase(), ProjectVersionsResponse.class)).getVersions();
                for (String version : versions) {
                    BuildsResponse buildsResponse = restTemplate.getForObject(apiUrl + "projects/" + imageType.name().toLowerCase() + "/versions/" + version + "/builds", BuildsResponse.class);
                    assert buildsResponse != null;
                    BuildsResponse.Build lastest = buildsResponse.getBuilds().get(0);
                    bootstrap(new DownloadableFileImage(version, imageType, lastest));
                }
            } catch (HttpClientErrorException exception) {
                System.out.println("Error receiving data! Please, check yor internet connection");
                applicationContext.close();
                System.exit(0);
            }
        }
    }

    @Override
    @SneakyThrows(IOException.class)
    public void download(DownloadableFileImage downloadableFile, File file) throws FIleDownloadingException {
        /*
         * Creating parent directories and file if not exists
         */
        if (file.getParentFile() != null) file.getParentFile().mkdirs();
        file.createNewFile();
        ResponseEntity<byte[]> response = null;
        try {
            response = restTemplate.getForEntity(apiUrl + "projects/" + downloadableFile.getImageType().name().toLowerCase() + "/versions/" + downloadableFile.getVersion() + "/builds/" + downloadableFile.getLastestBuild().getBuild() + "/downloads/" + downloadableFile.getLastestBuild().getDownloads().getApplication().getName(), byte[].class);
        } catch (HttpClientErrorException exception) {
            throw new FIleDownloadingException("Error downloading " + downloadableFile.getLastestBuild().getDownloads().getApplication().getName() + ". Could not connect to PaperMC servers");
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(Objects.requireNonNull(response.getBody()));
        fileOutputStream.close();
        /*
         * Comparing checksum
         */
        String expectedChecksum = downloadableFile.getLastestBuild().getDownloads().getApplication().getSha256();
        ByteSource byteSource = Files.asByteSource(file);
        String checksum = byteSource.hash(Hashing.sha256()).toString();
        if (!checksum.equals(expectedChecksum)) {
            file.delete();
            throw new FIleDownloadingException("Error downloading " + downloadableFile.getLastestBuild().getDownloads().getApplication().getName() + ". Got " + checksum + "SHA256 but expected " + expectedChecksum);
        }
    }

    @Override
    public void bootstrap(DownloadableFileImage dataHolder) {
        bootstrappedImages.add(dataHolder);
    }

    @Override
    public List<DownloadableFileImage> getByType(FileImage.ImageType imageType) {
        return bootstrappedImages.stream().filter(image -> image.getImageType().equals(imageType)).collect(Collectors.toList());
    }

    @Override
    public Optional<DownloadableFileImage> getByTypeAndVersion(FileImage.ImageType imageType, String version) {
        return bootstrappedImages.stream().filter(image -> image.getImageType().equals(imageType)).filter(image -> image.getVersion().equals(version)).findFirst();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
