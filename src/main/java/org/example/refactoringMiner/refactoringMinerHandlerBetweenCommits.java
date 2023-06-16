package org.example.refactoringMiner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.refactoringTypes.renameMethodObject;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.util.objectOutputRefactMiner;

public class refactoringMinerHandlerBetweenCommits {

    static GitService gitService = new GitServiceImpl();
    static GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
    static String linhaDeOrigem;
    static String linhaDeDestino;
    static String metodoOrigem;
    static String metodoDestino;

    static String type;


    static objectOutputRefactMiner objectRMiner;

    public static void main(String[] args) throws Exception {

        refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","45fa67dcec1f768d69da02374bb3c39fc2dc853b",
                "e32e45cc0471ebc94573c9f687257387b74ac947");

        System.out.println(getObjectRMiner().toString());;


    }

    public static void refactoringBetweenCommits(String projectURL, String commit1, String commit2) throws Exception {

        int lastSlashIndex = projectURL.lastIndexOf("/");
        String projectNameWithGit = projectURL.substring(lastSlashIndex + 1);
        String projectName = projectNameWithGit.replace(".git", "");


        //clearFile(outPutRMinerPath);

        Repository repo = gitService.cloneIfNotExists(
                "tmp/"+projectName,
                projectURL);
        // start commit: 819b202bfb09d4142dece04d4039f1708735019b
        // end commit: d4bce13a443cf12da40a77c16c1e591f4f985b47
        miner.detectBetweenCommits(repo,
                commit1, commit2,
                new RefactoringHandler() {
                    @Override
                    public void handle(String commitId, List<Refactoring> refactorings) {
                        System.out.println("Refactorings at " + commitId);
                        for (Refactoring ref : refactorings) {
                            System.out.println(ref.toJSON());
                            readJSON(ref);
                        }

                    }
                });
    }

    private static void readJSON(Refactoring ref){

        //converto em String -> converto em JsonElement para trasnformar em um object.

        String jsonString = ref.toJSON();
        JsonElement jelement = new JsonParser().parse(jsonString);
        JsonObject ObjetoJson = jelement.getAsJsonObject();

        type = ObjetoJson.get("type").getAsString();
        System.out.println("Type: " + type);
        System.out.println("--------------------------------------------");

        if (ObjetoJson.has("leftSideLocations")) {
            //essa função vai pegar tudo de leftSide e salvar em um array usa o forEach para transformar em um Element novamente.

            JsonArray leftSideLocations = ObjetoJson.getAsJsonArray("leftSideLocations");
            for (JsonElement locationElement : leftSideLocations) {
                System.out.println("leftSideLocation: ");
                pegaElementosOrigin(locationElement);
            }
        }

        if (ObjetoJson.has("rightSideLocations")) {
            JsonArray rightSideLocations = ObjetoJson.getAsJsonArray("rightSideLocations");
            for (JsonElement locationElement : rightSideLocations) {
                System.out.println("Right Side Location: ");
                pegaElementosDestino(locationElement);
            }
        }
    }

    /*private static void readJSON(Refactoring ref) {
        JsonObject objetoJson = new JsonParser().parse(ref.toJSON()).getAsJsonObject();

        String type = objetoJson.get("type").getAsString();
        System.out.println("Type: " + type);
        System.out.println("--------------------------------------------");

        processaLocalizacoes(objetoJson, "leftSideLocations");
        processaLocalizacoes(objetoJson, "rightSideLocations");
    }

    private static void processaLocalizacoes(JsonObject objetoJson, String chave) {
        if (objetoJson.has(chave)) {
            System.out.println(chave + ": ");
            JsonArray arrayJson = objetoJson.getAsJsonArray(chave);
            for (JsonElement elementoJson : arrayJson) {
                pegaElementos(elementoJson);
            }
        }
    }*/

    private static void pegaElementosOrigin(JsonElement meuJson){

        JsonObject meuObj = meuJson.getAsJsonObject();

        linhaDeOrigem = meuObj.get("startLine").getAsString();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        metodoOrigem = extractMethodDetails(meuObj.get("codeElement").getAsString());

        System.out.println("startLine: " + linhaDeOrigem);
        System.out.println("codeElementType: " + codeElementType);
        System.out.println("codeElement: " + metodoOrigem);
        System.out.println("--------------------------------------------");

    }
    private static void pegaElementosDestino(JsonElement meuJson){

        JsonObject meuObj = meuJson.getAsJsonObject();

        linhaDeDestino = meuObj.get("startLine").getAsString();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        metodoDestino = extractMethodDetails(meuObj.get("codeElement").getAsString());

        System.out.println("startLine: " + linhaDeDestino);
        System.out.println("codeElementType: " + codeElementType);
        System.out.println("codeElement: " + metodoDestino);
        System.out.println("--------------------------------------------");

    }

    private static String extractMethodDetails(String param) {

        String methodString = param;

        String result = "";

        String regex = "([a-z]+) ([a-z]+)\\((.*)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(methodString);

        if (matcher.find()) {
            result += matcher.group(2);

            String params = matcher.group(3);
            String[] paramList = params.split(", ");
            String[] paramTypes = new String[paramList.length];

            for (int p = 0; p < paramList.length; p++) {
                paramTypes[p] = paramList[p].split(" ")[1];
            }
            result += Arrays.toString(paramTypes);
        }
        return result;
    }

    public static objectOutputRefactMiner getObjectRMiner() {
        return objectRMiner = new objectOutputRefactMiner(type, linhaDeOrigem, metodoOrigem, linhaDeDestino, metodoDestino);
    }
}
