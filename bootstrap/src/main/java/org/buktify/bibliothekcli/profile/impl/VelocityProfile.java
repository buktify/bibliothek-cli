package org.buktify.bibliothekcli.profile.impl;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.buktify.bibliothekcli.data.image.FileImage;
import org.buktify.bibliothekcli.profile.InitializationProfile;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@Builder
@RequiredArgsConstructor
public class VelocityProfile implements InitializationProfile {

    FileImage.ImageType imageType = FileImage.ImageType.VELOCITY;
    String serverName;
    @Builder.Default
    boolean onlineMode = true;
    @Builder.Default
    int serverPort = 25565;
    @Builder.Default
    OptimizationShellFlags optimizationShellFlags = null;
}
