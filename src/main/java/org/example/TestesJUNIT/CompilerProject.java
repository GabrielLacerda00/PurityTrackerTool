package org.example.TestesJUNIT;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

public class CompilerProject {

    public static void compileWithMaven(String projectPath) {
        try {
            Process process = Runtime.getRuntime().exec("mvn test-compile", null, new File(projectPath));
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String findTestClassInTarget(String projectPath, String testClassName) {
        String potentialPath = projectPath + "/target/test-classes/" + testClassName.replace('.', '/') + ".class";
        File potentialFile = new File(potentialPath);

        if (potentialFile.exists()) {
            return potentialFile.getAbsolutePath();
        }

        return null;
    }

    public static void runTestClass(String projectPath, String classFilePath) {
        try {
            String className = classFilePath.replace(projectPath + "/target/test-classes/", "")
                    .replace(".class", "")
                    .replace(File.separatorChar, '.');

            File classesDirectory = new File(projectPath + "/target/test-classes/");
            URL[] classpath = {classesDirectory.toURI().toURL()};
            URLClassLoader classLoader = new URLClassLoader(classpath);

            Class<?> testClass = Class.forName(className, true, classLoader);

            Result result = JUnitCore.runClasses(testClass);
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }

            System.out.println(result.wasSuccessful() ? "Todos os testes passaram." : "Alguns testes falharam.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void handler(String projectPath, String searchClassName){
        compileWithMaven(projectPath);
        String foundPath = findTestClassInTarget(projectPath, searchClassName);

        if (foundPath != null) {
            System.out.println("Arquivo .class encontrado em: " + foundPath);
            runTestClass(projectPath, foundPath);
        } else {
            System.out.println("Arquivo .class não encontrado.");
        }
    }

    public static void main(String[] args) {
        String projectPath = "/Users/gabriellacerda/GitHubGabrielLacerda/GCViewerV1/";  // Caminho do diretório do projeto Maven
        //String searchClassName = "com.tagtraum.perf.gcviewer.RegressionTest0";   // Nome completo da classe de teste que você está procurando (com o pacote)

        compileWithMaven(projectPath);
        //String foundPath = findTestClassInTarget(projectPath, searchClassName);

    }
}