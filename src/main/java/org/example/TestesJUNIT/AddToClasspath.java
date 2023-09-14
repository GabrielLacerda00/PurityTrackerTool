package org.example.TestesJUNIT;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.File;
import java.lang.reflect.Method;

public class AddToClasspath {

    public static void main(String[] args) throws Exception {
        // Adicionando um JAR ao classpath
        addJarToClasspath(new File("/Users/gabriellacerda/PurityTrackerTool/src/main/java/org/example/libs/meuProjeto.jar"));

        // Agora você pode tentar carregar classes desse JAR
    }

    public static void addJarToClasspath(File jarFile) throws Exception {
        URL url = jarFile.toURI().toURL();
        URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        method.setAccessible(true);
        method.invoke(classLoader, url);
    }
}
