package org.example.randoop.utilsRandoop;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class methodsFinder {

    public static void main(String[] args) throws IOException {
        String classDir = "/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src";
        String outputFile = FileSearcher.findFilePath("methodsVersion01.txt");
        finderMethodsPath(classDir,outputFile);
    }

    public static void finderMethodsPath(String classDir,String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            Files.walk(Paths.get(classDir))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            CompilationUnit compilationUnit = StaticJavaParser.parse(path);
                            List<MethodDeclaration> methods = compilationUnit.findAll(MethodDeclaration.class);
                            for (MethodDeclaration method : methods) {
                                // Pega o caminho completo
                                // writer.write((path.toString().replace(".java", "") + "." + method.getSignature()).replace(" ", "").replace("\\", ".").substring(3));
                                // Pega o caminho sem o diretório raiz
                                // methodsFiltrados.add(((path.toString().substring(classDir.length() + 1)) + "." + method.getSignature()).replace(" ", "").replace("\\", ".").replace(".java", ""));
                                writer.write(((path.toString().substring(classDir.length() + 1)) + "." + method.getName()).replace(" ", "").replace("/", ".").replace(".java", ""));
                                writer.newLine();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
