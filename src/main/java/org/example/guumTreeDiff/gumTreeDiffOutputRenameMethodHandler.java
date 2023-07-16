package org.example.guumTreeDiff;

import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.TempObject;

import org.example.util.filePathFinderVersao01;
import org.example.util.filePathFinderVersao02;



public class gumTreeDiffOutputRenameMethodHandler {

    static ArrayList<TempObject> updateMethodsObjects = new ArrayList<TempObject>();

    static ArrayList<TempObject> updateInvocationObjects = new ArrayList<TempObject>();

    static HashMap<TempObject,renameMethodObject> renameMethodObjects = new HashMap<>();

    static ArrayList<renameMethodObject> listaConvertida = new ArrayList<>();


    static Diff result;


    public static void main(String[] args) throws Exception {

        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForOneCaller/CodeOrigin";
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForOneCaller/CodeDestiny";

        renameMethodHandler(pathDir01,pathDir02);
    }

    public static void renameMethodHandler(String path01,String path02) throws Exception {
        ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(path01);
        ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(path02);

        int tamanho = Math.min(filePaths1.size(), filePaths2.size());

        for (int i = 0; i < tamanho; i++) {
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            result = new AstComparator().compare(file1, file2);
            extractDetailsUpdateMethod(result);
            extractDetailsInvocationMethod(result);

            ArrayList<TempObject> listpares = mergeLists(updateMethodsObjects,updateInvocationObjects);

            for (int j = 0; j < listpares.size(); j+=2) {
                if(!renameMethodObjects.containsKey(listpares.get(j))){
                    renameMethodObjects.put(listpares.get(j),new renameMethodObject(listpares.get(j)));
                    renameMethodObjects.get(listpares.get(j)).setUpdateInvocationList(listpares.get(j+1));
                }else{
                    renameMethodObjects.get(listpares.get(j)).setUpdateInvocationList(listpares.get(j+1));
                }
            }
            converteMap(renameMethodObjects);
        }
    }

    private static ArrayList<TempObject> extractDetailsUpdateMethod(Diff result) {
        for (int var = 0; var < (result.getRootOperations().size()/2); var++) {
            String typeRename = getTypeRename(result,var);
            String lineOrigin = getLineOrigin(result,var);
            String nameMethodOrigin = extractClassName(result,var)+"."+getInvocationSrc(result,var);
            String lineDest = getLineDestino(result,var);
            String nameMethodDst = extractClassName(result,var)+"."+getInvocationDst(result,var);
            TempObject updateMethodObject = new TempObject(typeRename,lineOrigin,nameMethodOrigin,lineDest,nameMethodDst);
            updateMethodsObjects.add(updateMethodObject);
        }
        return updateMethodsObjects;
    }

    private static String getTypeRename(Diff result, int var) {
        String[] types =result.getRootOperations().get(var).getAction().getName().split("-");
        String type = types[0];
        return type + " "+result.getRootOperations().get(var).getAction().getNode().getType().toString();
    }

    private static ArrayList<TempObject> extractDetailsInvocationMethod(Diff result) {
        for (int var = (result.getRootOperations().size()/2); var < (result.getRootOperations().size()); var++) {
            String typeeRename = getTypeRename(result,var);
            String lineO = getLineOrigin(result,var);
            String nameMethodO = extractClassName(result,var)+"."+getInvocationSrc(result,var);
            String lineD = getLineDestino(result,var);
            String nameMethodD = extractClassName(result,var)+"."+getInvocationDst(result,var);
            TempObject updateInvocationObject = new TempObject(typeeRename,lineO,nameMethodO,lineD,nameMethodD);
            updateInvocationObjects.add(updateInvocationObject);
        }
        return updateInvocationObjects;
    }

    private static String getLineOrigin(Diff result, int var) {
        String number="";
        if (result.getRootOperations().get(var).getDstNode() == null) {
            number = "null";
        }else {
            number = Integer.toString(result.getRootOperations().get(var).getSrcNode().getPosition().getLine());
        }
        return number;
    }
    private static String getLineDestino(Diff result, int var){
        String number="";
        if (result.getRootOperations().get(var).getDstNode() == null) {
            number = "null";
        }else {
            number = Integer.toString(result.getRootOperations().get(var).getDstNode().getPosition().getLine());
        }
        return number;
    }

    private static String getInvocationSrc(Diff result,int var){
        String name="";
        if(result.getRootOperations().get(var).getSrcNode() == null){
            name = "null";
        }
        else{
            name = extractMethodName(result.getRootOperations().get(var).getSrcNode().toString());;
        }
        return name;
    }

    private static String getInvocationDst(Diff result, int var){
        String name="";
        if(result.getRootOperations().get(var).getDstNode() == null){
            name = "null";
        }
        else{
            name = extractMethodName(result.getRootOperations().get(var).getDstNode().toString());
        }
        return name;
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
    public static String extractClassName(Diff result, int var){
        return extractClassDetailss(result.getRootOperations().get(var).toString());
    }
    private static String extractClassDetailss(String input) {
        String regex = "\\b(\\w+)\\s*:";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
    private static ArrayList<TempObject> mergeLists(ArrayList<TempObject>list01,ArrayList<TempObject> list02){
        ArrayList<TempObject> listResult = new ArrayList<>();

        for (int i = 0; i < list01.size(); i++) {
            TempObject updateKey = list01.get(i);
            for (int j = 0; j < list02.size(); j++) {
                TempObject updateLock = list02.get(j);

                if (check(updateKey,updateLock)){
                    listResult.add(updateKey);
                    listResult.add(updateLock);
                }else{
                    listResult.add(updateKey);
                    listResult.add(new TempObject(updateLock.getType()));
                }
            }
        }

        return listResult;
    }

    private static boolean check(TempObject updateKey, TempObject updateLock){
        return updateKey.getNameMethodOrigin().equals(updateLock.getNameMethodOrigin())
                && updateKey.getNameMethodDst().equals(updateLock.getNameMethodDst());
    }

    private static void converteMap(HashMap<TempObject,renameMethodObject> renames){
        listaConvertida.addAll(renames.values());
    }

    public static ArrayList<renameMethodObject> getListaConvertida() {
        listaConvertida.forEach(System.out::println);
        return listaConvertida;
    }
}