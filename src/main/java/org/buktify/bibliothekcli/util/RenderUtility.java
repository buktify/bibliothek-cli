package org.buktify.bibliothekcli.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RenderUtility {

    public void renderHeader() {
        System.out.println("""
                    __    _ __    ___       __  __         __              ___\s
                   / /_  (_) /_  / (_)___  / /_/ /_  ___  / /__      _____/ (_)
                  / __ \\/ / __ \\/ / / __ \\/ __/ __ \\/ _ \\/ //_/_____/ ___/ / /\s
                 / /_/ / / /_/ / / / /_/ / /_/ / / /  __/ ,< /_____/ /__/ / / \s
                /_.___/_/_.___/_/_/\\____/\\__/_/ /_/\\___/_/|_|      \\___/_/_/""");
    }

    public void printMessage(String message){
        System.out.println("bibliothek-cli> " + message);
    }

    public void printEmpty(){
        printMessage("");
    }

}
