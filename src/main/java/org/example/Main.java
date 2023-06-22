package org.example;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.example.refactoringMiner.refactoringMinerHandlerBetweenCommits;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.TempObject;
import org.example.util.objectOutputRefactMiner;
import org.example.guumTreeDiff.gumTreeDiffOutputRenameMethodHandler;

import java.util.ArrayList;
import java.util.List;


public class Main {

    static ArrayList<objectOutputRefactMiner> objectsRMiner = new ArrayList<>();
    private static refactoringMinerHandlerBetweenCommits refactoringMinerHandler;
    public static void main(String[] args) throws Exception {
        //Rename method
        /*refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","45fa67dcec1f768d69da02374bb3c39fc2dc853b",
                "e32e45cc0471ebc94573c9f687257387b74ac947");*/
        //Renames methods
        refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","1aa1171a01937ad6655ceb193260ca4bd4308008",
                "0ba9f3f29f3e5e14836e98cdb13eee3dd8ff7461");

        objectsRMiner = refactoringMinerHandlerBetweenCommits.getObjectsRMiners();
        gumTreeDiffOutputRenameMethodHandler.main(args);
        //checkRenameMethod(objectsRMiner, gumTreeDiffOutputRenameMethodHandler.getRenameMethodObjects());

    }

   /* public static void checkRenameMethod(ArrayList<objectOutputRefactMiner> objectsRMiner, ArrayList<renameMethodObject> renameMethodObjects){
        for (int i = 0; i < objectsRMiner.size(); i++) {
            for (int j = i; j < renameMethodObjects.size() ; j++) {
                if(renameMethodObjects.get(j).getUpdateMethodObj().getType().toLowerCase().equals("update method") && renameMethodObjects.get(j).getUpdateMethodObj().getNameMethodOrigin().equals(objectsRMiner.get(j).getNameMethodOrigin())
                        && renameMethodObjects.get(j).getUpdateInvocationObj().getNameMethodDst().equals(objectsRMiner.get(j).getNameMethodDst())){
                    System.out.println("Pure");

                }else{
                    System.out.println("Floss!");
                }

                if (renameMethodObjects.get(j).getUpdateInvocationObj().getType().toLowerCase().equals("update invocation") && renameMethodObjects.get(j).getUpdateInvocationObj().getNameMethodDst().equals(objectsRMiner.get(j).getNameMethodDst())&&
                        renameMethodObjects.get(j).getUpdateInvocationObj().getNameMethodOrigin().equals(objectsRMiner.get(j).getNameMethodOrigin())){
                    System.out.println("Pure");
                }else{
                    System.out.println("Floss");
                }
            }
        }
    }*/

    public static void checkInlineMethod(ArrayList<objectOutputRefactMiner> objectsRMiner, ArrayList<renameMethodObject> renameMethodObjects){

    }

    public static boolean verificaMethods(){
        return true;
    }
}