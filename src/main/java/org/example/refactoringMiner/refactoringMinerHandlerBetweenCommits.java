package org.example.refactoringMiner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.util.*;
import org.refactoringminer.api.Refactoring;
import org.refactoringminer.api.RefactoringHandler;

import org.eclipse.jgit.lib.Repository;
import org.refactoringminer.api.GitHistoryRefactoringMiner;
import org.refactoringminer.api.GitService;
import org.refactoringminer.rm1.GitHistoryRefactoringMinerImpl;
import org.refactoringminer.util.GitServiceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.util.clearTempDirs;
import org.example.util.methodVerifier;

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

    static ArrayList<Object> objectsRefactoringMiner = new ArrayList<>();

    static ArrayList<objVersion01> versao01 = new ArrayList<>();

    static ArrayList<objVersion02> versao02 = new ArrayList<>();

    static ArrayList<callerWaited> rightSideCallers = new ArrayList<>();

    static ArrayList<callerWaited> leftSideCallers = new ArrayList<>();

    private static String path01 = "";

    private static String path02 = "";

    public static void main(String[] args) throws Exception {
        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCaller/CodeOrigin";
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCaller/CodeDestiny";
        handlerPathsDirs(pathDir01,pathDir02);
        //Rename method
        refactoringBetweenCommits("https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git","a5d9055020fe8b5d11cadd1bc99f28358ad2776f",
                "d3787c49b276e7489248f41e5800d9b7b38dcc6e");
        //Renames methods
        /*refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","1aa1171a01937ad6655ceb193260ca4bd4308008",
                "0ba9f3f29f3e5e14836e98cdb13eee3dd8ff7461");*/

        System.out.println("----------- Lista Objetos Rminer ---------- ");
        for (objectOutputRefactMiner obj : objectsRMiners) {
            System.out.println(obj);
        }

        for (Object version: objectsRefactoringMiner) {
            System.out.println(version);
        }

    }

    public static void handlerPathsDirs(String pathh01, String pathh02){
        path01 = pathh01;
        path02 = pathh02;
    }

    public static void refactoringBetweenCommits(String projectURL, String commit1, String commit2) throws Exception {

        int lastSlashIndex = projectURL.lastIndexOf("/");
        String projectNameWithGit = projectURL.substring(lastSlashIndex + 1);
        String projectName = projectNameWithGit.replace(".git", "");

        Repository repo = gitService.cloneIfNotExists(
                "tmp/"+projectName,
                projectURL);

        miner.detectBetweenCommits(repo,
                commit1, commit2,
                new RefactoringHandler() {
                    @Override
                    public void handle(String commitId, List<Refactoring> refactorings) {
                        System.out.println("Refactorings at " + commitId);
                        for (Refactoring ref : refactorings) {
                            System.out.println(ref.toJSON());
                            try {
                                readJSON(ref);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                    }
                });
        clearTempDirs.runClearTempDirs();
    }

    private static void readJSON(Refactoring ref) throws IOException {

        //converto em String -> converto em JsonElement para trasnformar em um object.

        String jsonString = ref.toJSON();
        JsonElement jelement = new JsonParser().parse(jsonString);
        JsonObject ObjetoJson = jelement.getAsJsonObject();

        type = ObjetoJson.get("type").getAsString();
        //System.out.println("Type: " + type);
        //System.out.println("--------------------------------------------");

        if (ObjetoJson.has("leftSideLocations")) {
            //essa função vai pegar tudo de leftSide e salvar em um array usa o forEach para transformar em um Element novamente.

            JsonArray leftSideLocations = ObjetoJson.getAsJsonArray("leftSideLocations");
            for (JsonElement locationElement : leftSideLocations) {
                //System.out.println("leftSideLocation: ");
                pegaElementosOrigin(locationElement);
            }
        }

        if (ObjetoJson.has("rightSideLocations")) {
            JsonArray rightSideLocations = ObjetoJson.getAsJsonArray("rightSideLocations");
            for (JsonElement locationElement : rightSideLocations) {
                //System.out.println("Right Side Location: ");
                pegaElementosDestino(locationElement);
            }
        }
        createAndAddObjectRMiner(versao01,versao02);
    }


    private static void pegaElementosOrigin(JsonElement meuJson) throws IOException {

        JsonObject meuObj = meuJson.getAsJsonObject();

        linhaDeOrigem = meuObj.get("startLine").getAsString();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        metodoOrigem = extractMethodName(meuObj.get("codeElement").getAsString());
        String path = meuObj.get("filePath").getAsString();
        //System.out.println("startLine: " + linhaDeOrigem);
        //System.out.println("codeElementType: " + codeElementType);
        //System.out.println("codeElement: " + metodoOrigem);
        //System.out.println("filePath: "+extractClassName(path));
        //System.out.println("--------------------------------------------");

        //javaParserHandler.mainCallersHandler(path01,metodoOrigem);
        //methodVerifier.handlerVerifier(path,metodoDestino);

        leftSideCallers.addAll(methodVerifier.handlerVerifier(path01,extractFileName(path),metodoOrigem));
        metodoOrigem = createMethodName(extractClassName(path),metodoOrigem);
        objVersion01 version01 = new objVersion01(type,linhaDeOrigem,metodoOrigem,leftSideCallers);
        versao01.add(version01);
        objectsRefactoringMiner.add(version01);

    }
    private static void pegaElementosDestino(JsonElement meuJson) throws IOException {

        JsonObject meuObj = meuJson.getAsJsonObject();

        linhaDeDestino = meuObj.get("startLine").getAsString();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        metodoDestino = extractMethodName(meuObj.get("codeElement").getAsString());
        String path = meuObj.get("filePath").getAsString();
        //System.out.println("startLine: " + linhaDeDestino);
        //System.out.println("codeElementType: " + codeElementType);
        //System.out.println("codeElement: " + metodoDestino);
        //System.out.println("filePath: "+path);
        //System.out.println("--------------------------------------------");

        methodVerifier.getCallersMethod().clear();
        rightSideCallers.addAll(methodVerifier.handlerVerifier(path02,extractFileName(path),metodoDestino));

        metodoDestino = createMethodName(extractClassName(path),metodoDestino);

        objVersion02 version02 = new objVersion02(type,linhaDeDestino,metodoDestino,rightSideCallers);
        versao02.add(version02);
        objectsRefactoringMiner.add(version02);
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

    public static String extractClassName(String className) {
        String regex = "/([^/]+)\\.java";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(className);

        if (matcher.find()) {
            String classNameWithoutExtension = matcher.group(1);
            return classNameWithoutExtension;
        }

        return null; // Retorna null caso nenhum nome seja encontrado
    }



    public static ArrayList<objectOutputRefactMiner> getObjectsRMiners() {
        for (Object obj : objectsRMiners) {
            System.out.println(obj);
        }
        return objectsRMiners;
    }


    public static void createAndAddObjectRMiner(ArrayList<objVersion01> versao01, ArrayList<objVersion02>version02) {
        for (int i = 0; i < versao01.size(); i++) {
            for (int j = 0; j <version02.size() ; j++) {
                objectOutputRefactMiner object = new objectOutputRefactMiner(versao01.get(j),version02.get(j));
                System.out.println(object);
                objectsRMiners.add(object);
            }
        }

    }

    public static String createMethodName(String classeName,String methodName){
        return classeName+"."+methodName;
    }

    private static String extractFileName(String path) {
        Pattern pattern = Pattern.compile("[^/\\\\]+$");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }


}
