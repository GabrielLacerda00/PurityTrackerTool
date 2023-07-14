package org.example;

import com.google.gson.internal.bind.util.ISO8601Utils;
import org.example.refactoringMiner.refactoringMinerHandlerBetweenCommits;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.TempObject;
import org.example.util.callerWaited;
import org.example.util.objectOutputRefactMiner;
import org.example.guumTreeDiff.gumTreeDiffOutputRenameMethodHandler;
import org.example.util.javaParserHandler;

import java.util.*;


public class controllerTool {

    static ArrayList<callerWaited>  callerWaiteds = new ArrayList<>();
    static ArrayList<objectOutputRefactMiner> objectsRMiner = new ArrayList<>();

    static ArrayList<renameMethodObject> listRenamesObjects = new ArrayList<>();

    static ArrayList<renameMethodObject> list = new ArrayList<>();

    private static refactoringMinerHandlerBetweenCommits refactoringMinerHandler;
    public static void main(String[] args) throws Exception {
        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameAndOneCaller/CodigoOrigem";
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameAndOneCaller/CodigoDestino";
        refactoringMinerHandlerBetweenCommits.handlerPathsDirs(pathDir01,pathDir02);
        //Rename method
        refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/SuitTestsRenameMethod.git",
                "45f12929dba78bfd65d396e8ccece2443df42dbc",
                "23e56e6cdf6381ed6b617b9ddaeda4f0e4b9aa37");

        //Renames methods
        /*refactoringMinerHandlerBetweenCommits.refactoringBetweenCommits("https://github.com/GabrielLacerda00/RenameMethodExample.git","1aa1171a01937ad6655ceb193260ca4bd4308008",
                "0ba9f3f29f3e5e14836e98cdb13eee3dd8ff7461");*/

        objectsRMiner = refactoringMinerHandlerBetweenCommits.getObjectsRMiners();
        gumTreeDiffOutputRenameMethodHandler.renameMethodHandler(pathDir01,pathDir02);
        listRenamesObjects = gumTreeDiffOutputRenameMethodHandler.getListaConvertida();
        checkRenameMethod(objectsRMiner,listRenamesObjects);
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

