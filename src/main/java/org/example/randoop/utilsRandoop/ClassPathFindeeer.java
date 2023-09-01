package org.example.randoop.utilsRandoop;

import org.example.randoop.utilsRandoop.FileSearcher;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassPathFindeeer {

    public static void main(String[] args) throws IOException {
        String classDir = "/Users/gabriellacerda/GitHubGabrielLacerda/Calculator/target/classes"; // changed to bin directory
        String pathClasses = FileSearcher.findFilePath("comuns_classes.txt");
        finderClassesPath(classDir, pathClasses);
    }

    public static void finderClassesPath(String classDir, String outputFile) throws IOException {
        System.out.println(classDir);
        System.out.println(outputFile);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            Files.walk(Paths.get(classDir))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".class")) // filtering for .class files
                    .forEach(path -> {
                        try {
                            String relativePath = (path.toString().substring(classDir.length()+1))
                                    .replace(" ", "")
                                    .replace("/", ".")
                                    .replace(".class", "");
                            writer.write(relativePath);
                            writer.newLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
