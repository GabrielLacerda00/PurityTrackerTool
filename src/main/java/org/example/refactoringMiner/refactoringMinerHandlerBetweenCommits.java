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

import java.util.List;

public class refactoringMinerHandlerBetweenCommits {

    static GitService gitService = new GitServiceImpl();
    static GitHistoryRefactoringMiner miner = new GitHistoryRefactoringMinerImpl();


    public static void main(String[] args) {




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
                           readJSON(ref);
                        }

                    }
                });
    }

    private static void pegaTodosElementos10(Refactoring ref){

        //converto em String -> converto em JsonElement para trasnformar em um object.

        String jsonString = ref.toJSON();
        JsonElement jelement = new JsonParser().parse(jsonString);
        JsonObject ObjetoJson = jelement.getAsJsonObject();

        String type = ObjetoJson.get("type").getAsString();
        System.out.println("Type: " + type);
        System.out.println("--------------------------------------------");

        if (ObjetoJson.has("leftSideLocations")) {
            //essa função vai pegar tudo de leftSide e salvar em um array usa o forEach para transformar em um Element novamente.

            JsonArray leftSideLocations = ObjetoJson.getAsJsonArray("leftSideLocations");
            for (JsonElement locationElement : leftSideLocations) {
                System.out.println("Left Side Location: ");
                pegaElementos(locationElement);
            }
        }

        if (ObjetoJson.has("rightSideLocations")) {
            JsonArray rightSideLocations = ObjetoJson.getAsJsonArray("rightSideLocations");
            for (JsonElement locationElement : rightSideLocations) {
                System.out.println("Right Side Location: ");
                pegaElementos(locationElement);
            }
        }
    }

    private static void readJSON(Refactoring ref) {
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
    }

    private static void pegaElementos(JsonElement meuJson){

        JsonObject meuObj = meuJson.getAsJsonObject();

        int startLine = meuObj.get("startLine").getAsInt();
        String codeElementType = meuObj.get("codeElementType").getAsString();
        String codeElement = meuObj.get("codeElement").getAsString();

        System.out.println("startLine: " + startLine);
        System.out.println("codeElementType: " + codeElementType);
        System.out.println("codeElement: " + codeElement);
        System.out.println("--------------------------------------------");

    }

}
