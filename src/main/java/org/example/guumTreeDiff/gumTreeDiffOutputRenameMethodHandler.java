package org.example.guumTreeDiff;


import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;
import gumtree.spoon.diff.operations.Operation;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.guumTreeDiff.gumTreeUtils.RenameMethodsObj;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.TempObject;
import org.example.util.filePathFinderVersao01;
import org.example.util.filePathFinderVersao02;

public class gumTreeDiffOutputRenameMethodHandler {
    static ArrayList<renameMethodObject> listaConvertida = new ArrayList();
    static Diff result;
    private ArrayList updateMethods = new ArrayList();
    private ArrayList<TempObject> updateInvocation = new ArrayList();

    public static void main(String[] args) throws Exception {
        String pathDir01 = "/Users/gabriellacrd/Documents/SuitTestsRenameMethod/OneRenameForThreObjCallersInOtherClasses/CodeOrigin";
        String pathDir02 = "/Users/gabriellacrd/Documents/SuitTestsRenameMethod/OneRenameForThreObjCallersInOtherClasses/CodeDestiny";
        gum04 gum04 = new gum04(pathDir01, pathDir02);
        gum04.getListaConvertida().forEach(System.out::println);
    }

    public gumTreeDiffOutputRenameMethodHandler(String path01, String path02) throws Exception {
        renameMethodHandler(path01, path02);
    }

    public static void renameMethodHandler(String path01, String path02) throws Exception {
        ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(path01);
        ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(path02);
        RenameMethodsObj renameMethodsObj = new RenameMethodsObj();
        int tamanho = Math.min(filePaths1.size(), filePaths2.size());
        ArrayList<TempObject> invocationsArray = new ArrayList();
        ArrayList<TempObject> updateMethodArrays = new ArrayList();
        ArrayList<Diff> listDiffs = new ArrayList();

        for(int i = 0; i < tamanho; ++i) {
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            result = (new AstComparator()).compare(file1, file2);
            listDiffs.add(result);
        }


        for (Diff diffObj: listDiffs) {
            for(int i = 0; i < diffObj.getRootOperations().size(); ++i) {
                if (getTypeRename(diffObj, i).equals("update Method")) {
                    updateMethodArrays.add(extractDetailsUpdateMethod(diffObj, i));
                } else if (getTypeRename(diffObj, i).equals("update Invocation")) {
                    invocationsArray.add(extractDetailsInvocationMethod(diffObj, i));
                }
            }
        }

        creatingMap(pairingLists(updateMethodArrays, invocationsArray), renameMethodsObj);
        converteMap(renameMethodsObj.getRenameMethodObjects());
    }

    private static void creatingMap(ArrayList<TempObject> listResult, RenameMethodsObj renameMethodsObj) {
        for(int j = 0; j < listResult.size(); j += 2) {
            if (!renameMethodsObj.getRenameMethodObjects().containsKey(listResult.get(j))) {
                renameMethodsObj.getRenameMethodObjects().put(listResult.get(j), new renameMethodObject(listResult.get(j)));
                (renameMethodsObj.getRenameMethodObjects().get(listResult.get(j))).setUpdateInvocationList(listResult.get(j + 1));
            } else {
                (renameMethodsObj.getRenameMethodObjects().get(listResult.get(j))).setUpdateInvocationList(listResult.get(j + 1));
            }
        }

    }

    private static void converteMap(HashMap<TempObject, renameMethodObject> renames) {
        listaConvertida.addAll(renames.values());
    }

    public  ArrayList<renameMethodObject> getListaConvertida() {
        return listaConvertida;
    }

    private static ArrayList pairingLists(ArrayList<TempObject> updateMethodArrays, ArrayList<TempObject> invocationsArray) {
        ArrayList<TempObject> listResult = new ArrayList();

        for(int i = 0; i < invocationsArray.size(); ++i) {
            listResult.add(updateMethodArrays.get(0));
            listResult.add(invocationsArray.get(i));
        }

        return listResult;
    }

    private static String getTypeRename(Diff result, int var) {
        String[] types = (result.getRootOperations().get(var)).getAction().getName().split("-");
        String type = types[0];
        return type + " " + (result.getRootOperations().get(var)).getAction().getNode().getType().toString();
    }

    private static TempObject extractDetailsInvocationMethod(Diff result, int var) {
        String typeeRename = getTypeRename(result,var);
        String lineO = getLineOrigin(result,var);
        String nameMethodO = extractClassName(result,var)+"."+getInvocationSrc(result,var);
        String lineD = getLineDestino(result,var);
        String nameMethodD = extractClassName(result,var)+"."+getInvocationDst(result,var);
        TempObject updateInvocationObject = new TempObject(typeeRename, lineO, nameMethodO, lineD, nameMethodD);
        return updateInvocationObject;
    }

    private static TempObject extractDetailsUpdateMethod(Diff result, int var) {
        String typeRename = getTypeRename(result,var);
        String lineOrigin = getLineOrigin(result,var);
        String nameMethodOrigin = extractClassName(result,var)+"."+getInvocationSrc(result,var);
        String lineDest = getLineDestino(result,var);
        String nameMethodDst = extractClassName(result,var)+"."+getInvocationDst(result,var);
        TempObject updateMethodObject = new TempObject(typeRename, lineOrigin, nameMethodOrigin, lineDest, nameMethodDst);
        return updateMethodObject;
    }

    private static String getLineOrigin(Diff result, int var) {
        String number = "";
        if ((result.getRootOperations().get(var)).getDstNode() == null) {
            number = "null";
        } else {
            number = Integer.toString((result.getRootOperations().get(var)).getSrcNode().getPosition().getLine());
        }

        return number;
    }

    private static String getLineDestino(Diff result, int var) {
        String number = "";
        if ((result.getRootOperations().get(var)).getDstNode() == null) {
            number = "null";
        } else {
            number = Integer.toString((result.getRootOperations().get(var)).getDstNode().getPosition().getLine());
        }

        return number;
    }

    private static String getInvocationSrc(Diff result, int var) {
        String name = "";
        if ((result.getRootOperations().get(var)).getSrcNode() == null) {
            name = "null";
        } else {
            name = extractMethodName((result.getRootOperations().get(var)).getSrcNode().toString());
        }

        return name;
    }

    private static String getInvocationDst(Diff result, int var) {
        String name = "";
        if ((result.getRootOperations().get(var)).getDstNode() == null) {
            name = "null";
        } else {
            name = extractMethodName((result.getRootOperations().get(var)).getDstNode().toString());
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

    public static String extractClassName(Diff result, int var) {
        return extractClassDetailss((result.getRootOperations().get(var)).toString());
    }

    private static String extractClassDetailss(String input) {
        String regex = "\\b(\\w+)\\s*:";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1) : null;
    }
}
