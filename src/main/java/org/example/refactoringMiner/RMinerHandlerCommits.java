package org.example.refactoringMiner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.example.util.*;
import org.example.refactoringMiner.MinerUtils.*;
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
import org.example.util.methodVerifierDestiny;

public class RMinerHandlerCommits {

    static GitService gitService = new GitServiceImpl();
    static GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();
    static String linhaDeOrigem;
    static String linhaDeDestino;
    static String metodoOrigem;
    static String metodoDestino;
    static String type;
    private static String path01 = "";
    private  static String path02 = "";
    private RMinerObjects minerObjects;


    private static ArrayList<objectOutputRefactMiner> rMinerObjectsArrayList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingCaller/CodeOrigin";
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingCaller/CodeDestiny";
        String urlGit = "https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git";
        String commit01 = "9efa63633fbff505b84d8fba97d7a4292e7e5561";
        String commit02 = "91e95a85850a61e81623386eeca2f495f9aec062";

        RMinerHandlerCommits RMinerHandlerCommits = new RMinerHandlerCommits(pathDir01,pathDir02,urlGit,commit01,commit02);

       System.out.println("----------- Lista Objetos Rminer ---------- ");
      RMinerHandlerCommits.getrMinerObjectsArrayList().forEach(System.out::println);
    }

    public RMinerHandlerCommits(String pathh01, String pathh02, String projectURL, String commit1, String commit2) throws Exception {
        this.minerObjects = new RMinerObjects();
        handlerPathsDirs(pathh01,pathh02);
        refactoringBetweenCommits(projectURL,commit1,commit2);
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
                        //System.out.println("Refactorings at " + commitId);
                        for (Refactoring ref : refactorings) {
                            //System.out.println(ref.toJSON());
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


        versionOriginCode versionOriginCode = new versionOriginCode();
        if (ObjetoJson.has("leftSideLocations")) {
            //essa função vai pegar tudo de leftSide e salvar em um array usa o forEach para transformar em um Element novamente.
            JsonArray leftSideLocations = ObjetoJson.getAsJsonArray("leftSideLocations");
            for (JsonElement locationElement : leftSideLocations) {
                //System.out.println("leftSideLocation: ");
                pegaElementosOrigin(locationElement,versionOriginCode);
            }
        }

        versionDestinyCode versionDestinyCode = new versionDestinyCode();
        //System.out.println(versionOriginCode.getVersao01());
        if (ObjetoJson.has("rightSideLocations")) {
            JsonArray rightSideLocations = ObjetoJson.getAsJsonArray("rightSideLocations");
            
            for (JsonElement locationElement : rightSideLocations) {
                //System.out.println("Right Side Location: ");
                pegaElementosDestino(locationElement,versionDestinyCode, versionOriginCode);
            }
        }

        createAndAddObjectRMiner(versionOriginCode.getVersao01(),versionDestinyCode.getVersao02());
    }


    private static void pegaElementosOrigin(JsonElement meuJson,versionOriginCode versionOriginCode) throws IOException {

        JsonObject meuObj = meuJson.getAsJsonObject();

        linhaDeOrigem = meuObj.get("startLine").getAsString();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        metodoOrigem = extractMethodName(meuObj.get("codeElement").getAsString());
        String file = meuObj.get("filePath").getAsString();

        methodVerifier.getCallersMethod().clear();
        leftSideCallers leftSideCallerss = new leftSideCallers(methodVerifier.handlerVerifier(path01,extractFileName(file),metodoOrigem));

        metodoOrigem = createMethodName(extractClassName(file),metodoOrigem);

        objVersion01 version01 = new objVersion01(type,linhaDeOrigem,metodoOrigem,leftSideCallerss.getLeftSideCallers());
        //System.out.println(leftSideCallerss.getLeftSideCallers())
        //System.out.println(version01);
        versionOriginCode.addInArray(version01);
    }

    private static void pegaElementosDestino(JsonElement meuJson,versionDestinyCode versionDestinyCode, versionOriginCode versionOriginCode) throws IOException {
        //System.out.println(versionOriginCode.getVersao01());

        JsonObject meuObj = meuJson.getAsJsonObject();

        linhaDeDestino = meuObj.get("startLine").getAsString();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        metodoDestino = extractMethodName(meuObj.get("codeElement").getAsString());
        String file = meuObj.get("filePath").getAsString();

        methodVerifierDestiny.getCallersMethod().clear();

        rightSideCallers rightSideCallerss = new rightSideCallers(methodVerifierDestiny.handlerVerifier(path02,extractFileName(file),metodoDestino));
        //System.out.println(rightSideCallerss.getRightSideCallers());
        metodoDestino = createMethodName(extractClassName(file),metodoDestino);

        objVersion02 version02 = new objVersion02(type,linhaDeDestino,metodoDestino,rightSideCallerss.getRightSideCallers());
        versionDestinyCode.addInArray(version02);

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

    public  ArrayList<objectOutputRefactMiner> getrMinerObjectsArrayList() {
        return rMinerObjectsArrayList;
    }

    public static void createAndAddObjectRMiner(ArrayList<objVersion01> versao01, ArrayList<objVersion02>version02) {
        RMinerObjects rMinerObjects = new RMinerObjects();
        for (int i = 0; i < versao01.size(); i++) {
            for (int j = 0; j <version02.size() ; j++) {
                objectOutputRefactMiner object = new objectOutputRefactMiner(versao01.get(i),version02.get(j));
                rMinerObjects.addInArray(object);
            }
        }
        rMinerObjectsArrayList.addAll(rMinerObjects.getObjectsRefactoringMiner());

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

    public RMinerObjects getMinerObjects() {
        return minerObjects;
    }

    public void setMinerObjects(RMinerObjects minerObjects) {
        this.minerObjects = minerObjects;
    }

    public String getType(){
        return type;
    }
}
