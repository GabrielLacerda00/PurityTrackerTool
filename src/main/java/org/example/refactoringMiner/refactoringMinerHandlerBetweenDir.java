package org.example.refactoringMiner;

import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;
import org.refactoringminer.api.RefactoringHandler;
import org.refactoringminer.api.GitService;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map.Entry;


public class refactoringMinerHandlerBetweenDir extends RefactoringHandler {
    private static Set<String> uniqueFilePaths = new HashSet<>();

    private  static List<String> pathClass = new ArrayList<>();

    static String diretorio = System.getProperty("user.dir");

    private static String nameOfProject;
    private static final String OUTPUT_DIR = diretorio+"\\"+"PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles";


    static GitService gitService = new GitServiceImpl();
    static GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
    public static void main(String[] args) throws Exception {


    }
    public static void executeRefactoring(String projectPathV1, String projectPathV2) throws Exception{

        //refactoringBetweenCommits(projectURL,commit1,commit2);
        detectBetweenDir(projectPathV1,projectPathV2);
        modificaPath(uniqueFilePaths);
        writeClassNamesToFile(pathClass);

    }


    public static void detectBetweenDir(String projectV1,String projectV2){
        //GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
        // You must provide absolute paths to the directories. Relative paths will cause exceptions.
        File dir1 = new File(projectV1);
        File dir2 = new File(projectV2);
        //Path dir1 = Paths.get(projectV1);
        //Path dir2 = Paths.get(projectV2);
        miner.detectAtDirectories(dir1, dir2, new RefactoringHandler() {
            @Override
            public void handle(String commitId, List<Refactoring> refactorings) {
                System.out.println("Refactorings at " + commitId);
                for (Refactoring ref : refactorings) {
                    System.out.println(ref.toJSON());
                    JsonElement jsonElement = new JsonParser().parse(ref.toJSON());
                    readJSON(jsonElement,uniqueFilePaths);
                }
            }
        });
    }

    private static void readJSON(JsonElement jsonElement, Set<String> uniqueFilePaths) {
        if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                if (entry.getKey().equals("filePath")) {
                    String filePath = entry.getValue().getAsString();
                    if (!uniqueFilePaths.contains(filePath)) {
                        uniqueFilePaths.add(filePath);
                    }
                }
                readJSON(entry.getValue(), uniqueFilePaths);
            }
        } else if (jsonElement.isJsonArray()) {
            for (JsonElement ma : jsonElement.getAsJsonArray()) {
                readJSON(ma, uniqueFilePaths);
            }
        }
    }

    private static void writeClassNamesToFile(List<String> pathClass) throws IOException {
        Path filePath = Paths.get(diretorio+"\\"+"ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsInJSONFile.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (String className : pathClass) {
                String[] partes = className.split("\\.", 2); // separa a string em duas partes, usando o primeiro ponto como separador
                String segundaParte =partes[1]; // pega a segunda parte e remove tudo que vem após o primeiro ponto
                writer.write(segundaParte);
                writer.newLine();
            }
        }
    }
    /*private static void writeClassNamesToFile(List<String> classNames) throws IOException {
        Path filePath = Paths.get(diretorio+"\\"+"ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsInJSONFile.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            for (String className : classNames) {
                writer.write(className);
                writer.newLine();
            }
        }
    }*/

    public static void modificaPath(Set<String> result) {
        for (String element: result) {
            String a = element.replace('/', '.');
            String d = a.replace(".java", "");
            String w  = d.replaceAll("\"", "");

            pathClass.add(w);
        }
    }

    public static String getNameOfProject(){
        return nameOfProject;
    }
}
