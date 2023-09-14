package org.example.TestesJUNIT;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;

public class DynamicJUnitRunner {

    public static void main(String[] args) {
        try {
            DynamicJUnitRunner runner = new DynamicJUnitRunner();
            runner.compileAndRun("/Users/gabriellacerda/GitHubGabrielLacerda/GCViewer/src/test/java/com/tagtraum/perf/gcviewer/RegressionTest0.java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void compileAndRun(String javaFilePath) throws Exception {
        String fullyQualifiedClassName = deriveFullyQualifiedClassName(javaFilePath);

        // 1. Compilação Dinâmica
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        // Suponha que todos os arquivos .java no mesmo diretório são dependências
        File javaFile = new File(javaFilePath);
        File[] dependencyFiles = javaFile.getParentFile().listFiles((dir, name) -> name.endsWith(".java"));

        String[] compileFiles = new String[dependencyFiles.length];
        for (int i = 0; i < dependencyFiles.length; i++) {
            compileFiles[i] = dependencyFiles[i].getAbsolutePath();
        }

        int compilationResult = compiler.run(null, null, null, compileFiles);
        if (compilationResult != 0) {
            throw new RuntimeException("Compilation failed");
        }


    }

    private String deriveFullyQualifiedClassName(String javaFilePath) {
        File javaFile = new File(javaFilePath);

        // Remove the .java extension
        String className = javaFile.getName().replace(".java", "");

        // Derive the package name from the directory structure
        File currentDirectory = javaFile.getParentFile();
        StringBuilder packageName = new StringBuilder();

        while (currentDirectory != null && !currentDirectory.getName().equals("src")) {
            packageName.insert(0, currentDirectory.getName() + ".");
            currentDirectory = currentDirectory.getParentFile();
        }

        if (packageName.length() > 0) {
            // Remove the trailing dot
            packageName.setLength(packageName.length() - 1);
        }

        return packageName + "." + className;
    }
}
