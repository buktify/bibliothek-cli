package org.buktify.bibliothekcli.util;

import com.sun.source.tree.UsesTree;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;

@UtilityClass
public class RenderUtility {

    @Value("${application.version}")
    private String version;

    public void renderHeader(){
        System.out.println("""
                    __    _ __    ___       __  __         __              ___\s
                   / /_  (_) /_  / (_)___  / /_/ /_  ___  / /__      _____/ (_)
                  / __ \\/ / __ \\/ / / __ \\/ __/ __ \\/ _ \\/ //_/_____/ ___/ / /\s
                 / /_/ / / /_/ / / / /_/ / /_/ / / /  __/ ,< /_____/ /__/ / / \s
                /_.___/_/_.___/_/_/\\____/\\__/_/ /_/\\___/_/|_|      \\___/_/_/""");
        System.out.println("                              Running version " + version);
    }

    @SneakyThrows
    public void clearScreen(){
//        switch (OSUtility.getOsType()){
//            case WINDOWS -> Runtime.getRuntime().exec("cls");
//            default -> Runtime.getRuntime().exec("clear");
//        }
    }

}
