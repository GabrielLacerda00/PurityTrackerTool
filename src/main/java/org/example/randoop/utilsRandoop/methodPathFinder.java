package org.example.randoop.utilsRandoop;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class methodPathFinder {

    public static void main(String[] args) throws IOException {
        String classDir = "/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src";
        String outputFilePath = "/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/txtFiles/methodsVersion01.txt";
        finderMethodsPath(classDir, outputFilePath);
    }

    public static void finderMethodsPath(String CLASS_DIR, String OUTPUT_FILE) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            Files.walk(Paths.get(CLASS_DIR))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(path -> {
                        try {
                            CompilationUnit compilationUnit = StaticJavaParser.parse(path);
                            List<MethodDeclaration> methods = compilationUnit.findAll(MethodDeclaration.class);
                            for (MethodDeclaration method : methods) {
                                StringBuilder methodSignature = new StringBuilder();
                                methodSignature.append(path.toString().replace(".java", "")).append(".").append(method.getNameAsString()).append("(");
                                List<Parameter> parameters = method.getParameters();
                                for (int i = 0; i < parameters.size(); i++) {
                                    Type parameterType = parameters.get(i).getType();
                                    String parameterTypePath = parameterType.toString().replace("/", ".");
                                    String parameterTypeQualName = getFullyQualifiedName(path.getParent().resolve(parameterTypePath).toFile());
                                    methodSignature.append(parameterTypeQualName);
                                    if (i < parameters.size() - 1) {
                                        methodSignature.append(",");
                                    }
                                }
                                methodSignature.append(")");
                                writer.write(methodSignature.toString());
                                writer.newLine();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    private static String getFullyQualifiedName(File file) throws IOException {
        CompilationUnit compilationUnit = StaticJavaParser.parse(file);
        Optional<ClassOrInterfaceDeclaration> classOrInterfaceDeclaration = compilationUnit.getClassByName(file.getName().replace(".java", ""));
        if (classOrInterfaceDeclaration.isPresent()) {
            return classOrInterfaceDeclaration.get().getFullyQualifiedName().get();
        }
        return "";
    }
}