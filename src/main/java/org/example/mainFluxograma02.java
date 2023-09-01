package org.example;

import org.example.refactoringMiner.RMinerHandlerCommits;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.objectOutputRefactMiner;
import org.example.guumTreeDiff.gumTreeDiffOutputRenameMethodHandler;

import java.util.*;


public class mainFluxograma02 {

    public static void main(String[] args) throws Exception {
        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingCaller/CodeOrigin";
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingCaller/CodeDestiny";
        String urlGit = "https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git";
        String commit01 = "9efa63633fbff505b84d8fba97d7a4292e7e5561";
        String commit02 = "91e95a85850a61e81623386eeca2f495f9aec062";

        RMinerHandlerCommits RMinerHandlerCommits = new RMinerHandlerCommits(pathDir01,pathDir02,urlGit,commit01,commit02);

        gumTreeDiffOutputRenameMethodHandler gumtTree = new gumTreeDiffOutputRenameMethodHandler(pathDir01,pathDir02);

        checkRenameMethod(RMinerHandlerCommits.getrMinerObjectsArrayList(),gumtTree.getListaConvertida());
    }
    public static List<String> checkRenameMethod(ArrayList<objectOutputRefactMiner> objectsRMiner, ArrayList<renameMethodObject> renameMethodObjects){

        List<String> types = new ArrayList<String>();

        if(objectsRMiner.isEmpty() || renameMethodObjects.isEmpty()){
            types.add("Floss");
            System.out.println("Floss");
            types.add("Floss");
            System.out.println("Floss");
        }

        for (int i = 0; i < objectsRMiner.size(); i++) {
            for (int j = i; j < renameMethodObjects.size(); j++) {
                if (renameMethodObjects.get(j).getUpdateMethodObj().getType().toLowerCase().equals("update method")
                        && !renameMethodObjects.get(j).getUpdateMethodObj().getNameMethodOrigin().equals(objectsRMiner.get(j).getVersion02().getNameMethodDst())
                        && renameMethodObjects.get(j).getUpdateMethodObj().getNameMethodDst().equals(objectsRMiner.get(j).getVersion02().getNameMethodDst())
                        && renameMethodObjects.get(j).getUpdateMethodObj().getLineDest().equals(objectsRMiner.get(j).getVersion02().getLineDst())){
                    types.add("Pure");
                    System.out.println("Pure");
                }
                else{
                    types.add("Floss");
                    System.out.println("Floss");
                }
                if(renameMethodObjects.get(j).getUpdateInvocationInList().get(j).getType().equalsIgnoreCase("update invocation")
                        && !renameMethodObjects.get(j).getUpdateMethodObj().getNameMethodOrigin().equals(objectsRMiner.get(j).getVersion02().getNameMethodDst())
                        && renameMethodObjects.get(j).getUpdateInvocationInList().get(j).getNameMethodDst().equals(objectsRMiner.get(j).getVersion02().getNameMethodDst())
                        && checkCallersLines(objectsRMiner.get(j),renameMethodObjects.get(j),j)){
                    types.add("Pure");
                    System.out.println("Pure");
                }else{
                    types.add("Floss");
                    System.out.println("Floss");
                }
            }
        }
        return types;
    }

    public static boolean checkCallersLines(objectOutputRefactMiner rminerObj,renameMethodObject renameObject,int j){
        boolean var = false;
        if(rminerObj.getVersion01().getCallMethods().size() != rminerObj.getVersion02().getCallerWaitedsMethod().size()
                && rminerObj.getVersion02().getCallerWaitedsMethod().size() == renameObject.getUpdateInvocationInList().size()) {
            if (rminerObj.getVersion01().getLineOrigin().equals(renameObject.getUpdateInvocationInList().get(j).getLineOrigin())
                    && rminerObj.getVersion02().getLineDst().equals(renameObject.getUpdateInvocationInList().get(j).getLineDest())) {
                return var = true;
            }
        }else {
            if (renameObject.getUpdateInvocationInList().get(j).getLineDest().equals(rminerObj.getVersion02().getCallerWaitedsMethod().get(j).getLineCall())) {
                return var = true;
            }
        }

        return var;
    }

}

