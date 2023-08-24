package org.example.randoop.utilsRandoop;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassPathFinder {

    public static void main(String[] args) throws IOException {
        String classDir = "/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src";
        String pathClasses = FileSearcher.findFilePath("comuns_classes.txt");
        finderClassesPath(classDir,pathClasses);
    }

    public static void finderClassesPath(String classDir,String pathClasses) throws IOException {
        List<String> classNames = getClassNames(classDir);
        writeClassNamesToFile(classNames, pathClasses);
        //filterMethodsPaths("C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\");
    }

    private static List<String> getClassNames(String classDir) throws IOException {
        List<String> classFiles = getClassFiles(classDir);
        List<String> classNames = classFiles.stream()
                .map(classFile -> classFile)
                .collect(Collectors.toList());
        return classNames;
    }

    private static List<String> getClassFiles(String classDir) throws IOException {
        List<String> classFiles = new ArrayList<>();
        Files.walk(Paths.get(classDir)).forEach(filePath -> {
            if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".class")) {
                //O nome do arquivo de classe sem o caminho completo para o diretório de classes.
                classFiles.add(filePath.toString().substring(classDir.length() + 1).replace(" ", "").replace("/", ".")
                        .replace(".class",""));
                //filePath.toString().replace(".class", "")+ "." + method.getNameAsString()).replace(" ", "").replace("\\", ".").substring(3));
                //filePath.toString().substring(classDir.length() + 1));
            }
        });
        return classFiles;
    }

    private static void writeClassNamesToFile(List<String> classNames, String fileDir) throws IOException {
        Path filePath = Paths.get(fileDir);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (String className : classNames) {
                writer.write(className);
                writer.newLine();
            }
        }
    }
}
