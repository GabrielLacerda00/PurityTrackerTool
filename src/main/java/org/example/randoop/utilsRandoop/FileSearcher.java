package org.example.randoop.utilsRandoop;

import java.io.File;

public class FileSearcher {

    public static String findFilePath(String fileName) {
        String projectDirectory = System.getProperty("user.dir"); // Diretório do projeto

        return searchFile(new File(projectDirectory), fileName);
    }

    private static String searchFile(File directory, String fileName) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    String filePath = searchFile(file, fileName);
                    if (filePath != null) {
                        return filePath;
                    }
                } else if (file.getName().equals(fileName)) {
                    return file.getAbsolutePath();
                }
            }
        }

        return null;
    }

    public static void main(String[] args) {
        String fileName = "randoop-all-4.3.2.jar";
        String filePath = findFilePath(fileName);

        if (filePath != null) {
            System.out.println("O caminho absoluto do arquivo " + fileName + " é: " + filePath);
        } else {
            System.out.println("Arquivo não encontrado no projeto.");
        }
    }
}
