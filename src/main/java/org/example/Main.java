package org.example;

import org.example.refactoringTypes.renameMethodObject;
import org.example.util.objectOutputRefactMiner;
import org.example.guumTreeDiff.gumTeeDiffOutputHandler;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
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