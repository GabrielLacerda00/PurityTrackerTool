package org.example;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.example.refactoringMiner.refactoringMinerHandlerBetweenCommits;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.TempObject;
import org.example.util.callerWaited;
import org.example.util.objectOutputRefactMiner;
import org.example.guumTreeDiff.gumTreeDiffOutputRenameMethodHandler;
import org.example.util.javaParserHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {

    static ArrayList<callerWaited>  callerWaiteds = new ArrayList<>();
    static ArrayList<objectOutputRefactMiner> objectsRMiner = new ArrayList<>();

    static ArrayList<renameMethodObject> listRenamesObjects = new ArrayList<>();

    static ArrayList<renameMethodObject> list = new ArrayList<>();

    private static refactoringMinerHandlerBetweenCommits refactoringMinerHandler;
    public static void main(String[] args) throws Exception {
        String pathDir01 = "C:\\Users\\gabri\\versao01\\RenameMethodExample";
        String pathDir02 = "C:\\Users\\gabri\\versao02\\RenameMethodExample";
        refactoringMinerHandlerBetweenCommits.handlerPathsDirs(pathDir01,pathDir02);
        //Rename method
        refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","62a4405fbd48fc9f64b54da58856f3e441b45636",
                "d46cdc1ad2fa4eb91042e318da56ae00ebbf69e5");

        //62a4405fbd48fc9f64b54da58856f3e441b45636 - versao01 testando erro
        //d46cdc1ad2fa4eb91042e318da56ae00ebbf69e5 - versão 02 testando erro
        //56cfa5546ab707eaba0289de2a5f5eda2b2e595d - versao 02 antiga
        //45fa67dcec1f768d69da02374bb3c39fc2dc853b - versao 01 antiga
        //Renames methods
        /*refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","1aa1171a01937ad6655ceb193260ca4bd4308008",
                "0ba9f3f29f3e5e14836e98cdb13eee3dd8ff7461");*/
        objectsRMiner = refactoringMinerHandlerBetweenCommits.getObjectsRMiners();
        System.out.println(objectsRMiner);
        gumTreeDiffOutputRenameMethodHandler.main(args);
        listRenamesObjects = gumTreeDiffOutputRenameMethodHandler.getListaConvertida();
        System.out.println(listRenamesObjects);
        checkRenameMethod(objectsRMiner,listRenamesObjects);
    }
    public static void checkRenameMethod(ArrayList<objectOutputRefactMiner> objectsRMiner, ArrayList<renameMethodObject> renameMethodObjects){
        if(objectsRMiner.isEmpty() || renameMethodObjects.isEmpty()){
            System.out.println("Floss");
        }
        for (int i = 0; i < objectsRMiner.size(); i++) {
            for (int j = i; j < renameMethodObjects.size(); j++) {
                if (renameMethodObjects.get(j).getUpdateMethodObj().getType().toLowerCase().equals("update method") && renameMethodObjects.get(j).getUpdateMethodObj().getNameMethodDst().equals(objectsRMiner.get(j).getVersion02().getNameMethodDst())
                && renameMethodObjects.get(j).getUpdateMethodObj().getLineDest().equals(objectsRMiner.get(j).getVersion02().getLineDst())){
                    System.out.println("Pure"); //True
                }else{
                    System.out.println("Floss"); //False
                }

                if(renameMethodObjects.get(j).getUpdateInvocationInList().get(j).getType().toLowerCase().equals("update invocation")
                        && renameMethodObjects.get(j).getUpdateInvocationInList().get(j).getNameMethodDst().equals(objectsRMiner.get(j).getVersion02().getNameMethodDst())
                        && renameMethodObjects.get(j).getUpdateInvocationInList().get(j).getLineDest().equals(objectsRMiner.get(j).getVersion02().getCallerWaitedsMethod().get(j).getLineCall())){
                    System.out.println("Pure");
                }else{
                    System.out.println("Floss");
                }
            }
        }
    }

}

