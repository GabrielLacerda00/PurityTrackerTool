package org.example;

import org.example.refactoringMiner.RMinerHandlerCommits;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.TempObject;
import org.example.util.callerWaited;
import org.example.util.objectOutputRefactMiner;
import org.example.guumTreeDiff.gumTreeNewAlgoritmo;

import java.util.*;


public class  mainFlux02 {

    public static void main(String[] args) throws Exception {

        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuiteTestePIBICTool/OneRenameForOneCaller/CodeOrigin/src";
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuiteTestePIBICTool/OneRenameForOneCaller/CodeDestiny/src";
        String urlGit = "https://github.com/GabrielLacerda00/SuiteTestePIBICTool.git";
        String commit01 = "2088de9ec680801fdd40174e3263a551c5792aef";
        String commit02 = "c6b8876147f2d13322c502f7ee7ee1b5377206bb";

        RMinerHandlerCommits RMinerHandlerCommits = new RMinerHandlerCommits(pathDir01,pathDir02,urlGit,commit01,commit02);
        gumTreeNewAlgoritmo gumtTree = new gumTreeNewAlgoritmo(pathDir01,pathDir02);

        for (int i = 0; i < RMinerHandlerCommits.getrMinerObjectsArrayList().size(); i++) {
            verifyTypeAndExecute(RMinerHandlerCommits,gumtTree);
        }

    }

    public static void verifyTypeAndExecute(RMinerHandlerCommits RMinerHandlerCommits, gumTreeNewAlgoritmo gumtTree){

        switch (RMinerHandlerCommits.getType()) {
            case "Rename Method":
                checkRenameMethod(RMinerHandlerCommits.getrMinerObjectsArrayList(),gumtTree.getListaConvertida());
                break;
            case "Inline Method":

                break;
            case "Extract Method":

                break;
            default:
                System.out.println("Invalid operation");
        }
    }
    public static List<String> checkRenameMethod(ArrayList<objectOutputRefactMiner> objectsRMiner, ArrayList<TempObject> renameMethodObjects){

        List<String> types = new ArrayList<String>();
        System.out.println(objectsRMiner);
        System.out.println(renameMethodObjects);

        if(objectsRMiner.isEmpty() || renameMethodObjects.isEmpty()){
            types.add("Floss");
            System.out.println("Floss");
            types.add("Floss");
            System.out.println("Floss");
        }


        for (objectOutputRefactMiner objMiner: objectsRMiner) {
            for (TempObject objGum: renameMethodObjects) {
                if (objGum.getType().toLowerCase().equals("update method")
                        && !objGum.getNameMethodOrigin().equals(objMiner.getVersion02().getNameMethodDst())
                        && objGum.getNameMethodDst().equals(objMiner.getVersion02().getNameMethodDst())
                        && objGum.getLineDest().equals(objMiner.getVersion02().getLineDst())){
                    types.add("Pure");
                    System.out.println("Pure");
                }
                else if(objGum.getType().equalsIgnoreCase("update invocation")
                        && !objGum.getNameMethodOrigin().equals(objMiner.getVersion02().getNameMethodDst())
                        && checkCallersLinesAndNames(objMiner,objGum)
                        //&& objGum.getLineOrigin().equals(objMiner.getVersion01().getLineOrigin())
                ){
                    types.add("Pure");
                    System.out.println("Pure");
                }
                else{
                    types.add("Floss");
                    System.out.println("Floss");
                }


            }
        }
        return types;
    }

    public static boolean checkCallersLinesAndNames(objectOutputRefactMiner rminerObj,TempObject objGum){
        for (callerWaited objCall: rminerObj.getVersion02().getCallerWaitedsMethod()) {
            if(objGum.getNameMethodDst().equals(objCall.getCallNameMethod()) && objGum.getLineDest().equals(objCall.getLineCall())){
                return true;
            }
        }
        return false;
    }

    public static boolean checkCallersLinesAndNamesOrigin(objectOutputRefactMiner rminerObj,TempObject objGum){
        for (callerWaited objCall: rminerObj.getVersion01().getCallMethods()) {
            if(objGum.getNameMethodDst().equals(objCall.getCallNameMethod()) && objGum.getLineDest().equals(objCall.getLineCall())){
                return true;
            }
        }
        return false;
    }


}

