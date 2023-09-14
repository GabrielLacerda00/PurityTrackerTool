package org.example.refactoringMiner;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;

public class SourcePathFinder {

    public static String findClassInProject(String className, String projectPath) {
        Path startDir = Paths.get(projectPath);
        String fileName = className + ".java";

        Finder finder = new Finder(fileName);
        try {
            Files.walkFileTree(startDir, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, finder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path foundPath = finder.getFoundPath();
        return (foundPath != null) ? foundPath.toString() : null;
    }

    private static class Finder implements FileVisitor<Path> {
        private final String fileName;
        private Path foundPath = null;

        Finder(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            // Se a classe já foi encontrada, não há necessidade de continuar a busca
            return (foundPath != null) ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            if (file.getFileName().toString().equals(fileName)) {
                foundPath = file;
                return FileVisitResult.TERMINATE;
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
            return (foundPath != null) ? FileVisitResult.TERMINATE : FileVisitResult.CONTINUE;
        }

        public Path getFoundPath() {
            return foundPath;
        }
    }

    public static void main(String[] args) {
        String result = findClassInProject("MyObj", "/Users/gabriellacerda/GitHubGabrielLacerda/SuiteTestMiniProjects/OneRenameForOneCallerInOtherClasse/CodeOrigin/Calculator");
        System.out.println(result);
    }
}
