package org.example.util;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class filePathFinderVersao02 {
    public static void main(String[] args) throws IOException {
        ArrayList<String> filePaths = getJavaFilePaths("C:\\Users\\gabri\\versao02\\ProjetoMercadinho");
        System.out.println("Arquivos encontrados: " + filePaths.size());

        // Use a lista de filePaths conforme necessário
        for (String filePath : filePaths) {
            System.out.println(filePath);
        }
    }

    public static ArrayList<String> getJavaFilePaths(String classDir) throws IOException {
        ArrayList<String> filePaths = new ArrayList<>();
        Files.walk(Paths.get(classDir))
                .filter(Files::isRegularFile)
                .filter(file -> file.toString().endsWith(".java"))
                .forEach(file -> filePaths.add(file.toString()));
        return filePaths;
    }
}
