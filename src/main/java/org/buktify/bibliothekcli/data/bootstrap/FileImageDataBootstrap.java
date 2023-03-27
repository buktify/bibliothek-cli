package org.buktify.bibliothekcli.data.bootstrap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.buktify.bibliothekcli.data.bootstrap.response.ProjectVersionsResponse;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.data.image.impl.DownloadableFileImage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class FileImageDataBootstrap implements DataBootstrap{

    @Value("${application.api-url}")
    private String apiUrl;

    @Override
    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        for(FileImage.ImageType imageType : FileImage.ImageType.values()){
            ProjectVersionsResponse response = restTemplate.getForObject(apiUrl + "projects/" + imageType.name().toLowerCase(), ProjectVersionsResponse.class);
            System.out.println(response);
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
}
