package org.example;

import org.example.refactoringMiner.refactoringMinerHandlerBetweenCommits;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.objectOutputRefactMiner;
import org.example.guumTreeDiff.gumTeeDiffOutputHandler;
import org.example.util.objectOutputRefactMiner;

import java.util.ArrayList;



public class Main {

    static objectOutputRefactMiner objectRMiner;
    private static refactoringMinerHandlerBetweenCommits refactoringMinerHandler;
    public static void main(String[] args) throws Exception {

        refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","45fa67dcec1f768d69da02374bb3c39fc2dc853b",
                "e32e45cc0471ebc94573c9f687257387b74ac947");
        objectRMiner = refactoringMinerHandlerBetweenCommits.getObjectRMiner();

    }


    public static void checkRenameMethod(objectOutputRefactMiner objectMiner, ArrayList<renameMethodObject> renameMethodObjects){
        for (renameMethodObject objectRenameMethodGum: renameMethodObjects
             ) {
            if(objectRenameMethodGum.getUpdateMethodObj().getType().equals("Update method") && objectRenameMethodGum.getUpdateMethodObj().getNameMethodOrigin().equals(objectMiner.getLineOrigin())
            && objectRenameMethodGum.getUpdateInvocationObj().getNameMethodDst().equals(objectMiner.getNameMethodDst())){
                System.out.println("Pure");
            }else{
                System.out.println("Floss!");
            }

            if (objectRenameMethodGum.getUpdateInvocationObj().getType().equals("Update invocation") && objectRenameMethodGum.getUpdateInvocationObj().getLineOrigin().equals(objectMiner.getLineDest())&&
            objectRenameMethodGum.getUpdateInvocationObj().getLineOrigin().equals(objectMiner.getLineDest())){
                System.out.println("Pure");
            }else{
                System.out.println("Floss");
            }
        }
    }
}