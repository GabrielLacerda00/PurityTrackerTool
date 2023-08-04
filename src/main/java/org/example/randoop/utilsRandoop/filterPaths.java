package org.example.randoop.utilsRandoop;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class filterPaths {

    public static void main(String[] args) throws IOException {
        //methodfilterPaths("C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsInJSONFile.txt","C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\test\\mercadinho.txt");
        methodfilterPaths("C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\test\\mercadinho.txt",
                "C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsInJSONFile.txt");
    }
    public static void methodfilterPaths(String OUTPUT_FILE01, String OUTPUT_FILEV02) throws IOException {
        //String path01 = getPathsGenerics(OUTPUT_FILE01);
        //String path02 = getPathsGenerics(OUTPUT_FILEV02);

        // Lendo o conteúdo do segundo arquivo TXT em uma lista
        List<String> filtro = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(OUTPUT_FILEV02))) {
            String line;
            while ((line = br.readLine()) != null) {
                filtro.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Filtrando o arquivo1.txt usando a lista de filtro
        List<String> linhas_filtradas = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(OUTPUT_FILE01))) {
            String line;
            while ((line = br.readLine()) != null) {
                for (String s : filtro) {
                    if (line.contains(s)) {
                        linhas_filtradas.add(line);
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Escrevendo as linhas filtradas em um novo arquivo
        try (FileWriter fw = new FileWriter("C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\test\\arquivo_filtrado02.txt")) {
            for (String line : linhas_filtradas) {
                fw.write(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getPathsGenerics(String classDir) throws IOException {
        List<String> pathGeneric = new ArrayList<>();
        String userDir = System.getProperty("user.dir");
        String fullPath = userDir + File.separator + classDir;
        try (Stream<Path> stream = Files.walk(Paths.get(fullPath))) {
            stream.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .forEach(pathGeneric::add);
        } catch (AccessDeniedException e) {
            System.out.println("Acesso Negado");
        }
        return pathGeneric.get(0);
    }

    public void verificaPaths(String arquivo1,String arquivo2){
        try (BufferedReader reader1 = new BufferedReader(new FileReader(arquivo1));
             BufferedReader reader2 = new BufferedReader(new FileReader(arquivo2))) {

            String linha1 = reader1.readLine();
            String linha2 = reader2.readLine();

            while (linha1 != null) {
                if (linha1.equals(linha2)) {
                    System.out.println("A linha '" + linha1 + "' está presente no arquivo02");
                } else {
                    System.out.println("A linha '" + linha1 + "' não está presente no arquivo02");
                }
                linha1 = reader1.readLine();
                linha2 = reader2.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}