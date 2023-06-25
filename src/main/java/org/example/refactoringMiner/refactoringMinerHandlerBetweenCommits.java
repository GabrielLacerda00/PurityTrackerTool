package org.example.refactoringMiner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.util.objectOutputRefactMiner;

import org.example.util.objVersion01;

public class refactoringMinerHandlerBetweenCommits {

    static GitService gitService = new GitServiceImpl();
    static GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
    static String linhaDeOrigem;
    static String linhaDeDestino;
    static String metodoOrigem;
    static String metodoDestino;

    static String type;


    static objectOutputRefactMiner objectRMiner;

    static ArrayList<objectOutputRefactMiner> objectsRMiners = new ArrayList<>();

    static ArrayList<Object> versao01 = new ArrayList<>();

    static ArrayList<Object> versao02 = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        //Renam method
        refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","45fa67dcec1f768d69da02374bb3c39fc2dc853b",
                "e32e45cc0471ebc94573c9f687257387b74ac947");
        //Renames methods
        /*refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","1aa1171a01937ad6655ceb193260ca4bd4308008",
                "0ba9f3f29f3e5e14836e98cdb13eee3dd8ff7461");*/


        System.out.println("----------- Lista Objetos Rminer ---------- ");
        for (objectOutputRefactMiner obj : objectsRMiners) {
            System.out.println(obj);
        }

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
        createAndAddObjectRMiner();

    }


    private static void pegaElementosOrigin(JsonElement meuJson){

        JsonObject meuObj = meuJson.getAsJsonObject();

        linhaDeOrigem = meuObj.get("startLine").getAsString();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        metodoOrigem = extractMethodName(meuObj.get("codeElement").getAsString());
        String path = meuObj.get("filePath").getAsString();
        System.out.println("startLine: " + linhaDeOrigem);
        System.out.println("codeElementType: " + codeElementType);
        System.out.println("codeElement: " + metodoOrigem);
        System.out.println("filePath: "+extractClassName(path));
        System.out.println("--------------------------------------------");

    }
    private static void pegaElementosDestino(JsonElement meuJson){

        JsonObject meuObj = meuJson.getAsJsonObject();

        linhaDeDestino = meuObj.get("startLine").getAsString();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        metodoDestino = extractMethodName(meuObj.get("codeElement").getAsString());
        String path = meuObj.get("filePath").getAsString();
        System.out.println("startLine: " + linhaDeDestino);
        System.out.println("codeElementType: " + codeElementType);
        System.out.println("codeElement: " + metodoDestino);
        System.out.println("filePath: "+extractClassName(path));
        System.out.println("--------------------------------------------");

    }


    private static String extractMethodName(String methodDefinition) {
        String result = "";

        String regex = "(\\w+)\\s*\\(";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(methodDefinition);

        if (matcher.find()) {
            result = matcher.group(1);
        }

        return result;
    }

    public static String extractClassName(String className){
        String classNamee = className.split("\\.")[0];
        return classNamee;
    }

    public static String createMethodName(String classeName,String methodName){
        return classeName+"."+methodName;
    }

    public static objectOutputRefactMiner getObjectRMiner() {
        return objectRMiner = new objectOutputRefactMiner(type, linhaDeOrigem, metodoOrigem, linhaDeDestino, metodoDestino);
    }

    public static ArrayList<objectOutputRefactMiner> getObjectsRMiners() {
        for (objectOutputRefactMiner obj : objectsRMiners) {
            System.out.println(obj);
        }
        return objectsRMiners;
    }

    public static void createAndAddObjectRMiner() {
        objectOutputRefactMiner object = new objectOutputRefactMiner(type, linhaDeOrigem, metodoOrigem, linhaDeDestino, metodoDestino);
        objectsRMiners.add(object);
    }
}
