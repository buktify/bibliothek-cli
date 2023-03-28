package org.buktify.bibliothekcli.data.bootstrap;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.bootstrap.response.BuildsResponse;
import org.buktify.bibliothekcli.data.bootstrap.response.ProjectVersionsResponse;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.buktify.bibliothekcli.util.RenderUtility;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FileImageDataBootstrap implements DataBootstrap, ApplicationContextAware {

    ConfigurableApplicationContext applicationContext;
    @Value("${application.api-url}")
    private String apiUrl;

    @Override
    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        for (FileImage.ImageType imageType : FileImage.ImageType.values()) {
            RenderUtility.printMessage("Receiving build data for " + imageType.name().toLowerCase());
            try {
                List<String> versions = Objects.requireNonNull(restTemplate.getForObject(apiUrl + "projects/" + imageType.name().toLowerCase(), ProjectVersionsResponse.class)).getVersions();
                for (String version : versions) {
                    BuildsResponse buildsResponse = restTemplate.getForObject(apiUrl + "projects/" + imageType.name().toLowerCase() + "/versions/" + version + "/builds", BuildsResponse.class);
                    assert buildsResponse != null;
                    BuildsResponse.Build lastest = buildsResponse.getBuilds().get(0);
                    bootstrap(new DownloadableFileImage(version, imageType, lastest));
                }
            } catch (HttpClientErrorException exception) {
                RenderUtility.printMessage("Error receiving data! Please, check yor internet connection");
                applicationContext.close();
                System.exit(0);
            }

        }
    }

    @Override
    public void bootstrap(DownloadableFileImage dataHolder) {

    }

    @Override
    public List<DownloadableFileImage> getByType(FileImage.ImageType imageType) {
        return null;
    }

    @Override
    public List<DownloadableFileImage> getByTypeAndVersion(FileImage.ImageType imageType, String version) {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (ConfigurableApplicationContext) applicationContext;
    }
}
