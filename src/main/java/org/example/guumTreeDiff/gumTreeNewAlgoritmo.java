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

public class gumTreeNewAlgoritmo {

    static ArrayList<TempObject> listaConvertida = new ArrayList();
    static Diff result;
    private ArrayList updateMethods = new ArrayList();
    private ArrayList<TempObject> updateInvocation = new ArrayList();

    public static void main(String[] args) throws Exception {
        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingObjCallersInOtherClasses/CodeOrigin";
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForMissingObjCallersInOtherClasses/CodeDestiny";
        gumTreeNewAlgoritmo gumTreeNewAlgoritmo = new gumTreeNewAlgoritmo(pathDir01, pathDir02);
        System.out.println(gumTreeNewAlgoritmo.getListaConvertida());;
    }

    public gumTreeNewAlgoritmo(String path01, String path02) throws Exception {
        listaConvertida = renameMethodHandler(path01, path02);
    }

    public static ArrayList<TempObject> renameMethodHandler(String path01, String path02) throws Exception {
        ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(path01);
        ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(path02);
        RenameMethodsObj renameMethodsObj = new RenameMethodsObj();
        ArrayList<TempObject> listOfExpectedRefacts = new ArrayList();
        int tamanho = Math.min(filePaths1.size(), filePaths2.size());

        ArrayList<Diff> listDiffs = new ArrayList();

        for(int i = 0; i < tamanho; ++i) {
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            result = (new AstComparator()).compare(file1, file2);
            listDiffs.add(result);
        }


        for (Diff diffObj: listDiffs) {
            for(int i = 0; i < diffObj.getRootOperations().size(); ++i) {
                listOfExpectedRefacts.add(extractDetails(diffObj, i));
            }
        }

        return listOfExpectedRefacts;

    }

    public  ArrayList<TempObject> getListaConvertida() {
        return listaConvertida;
    }


    private static String getTypeRename(Diff result, int var) {
        String[] types = (result.getRootOperations().get(var)).getAction().getName().split("-");
        String type = types[0];
        return type + " " + (result.getRootOperations().get(var)).getAction().getNode().getType().toString();
    }

    private static TempObject extractDetails(Diff result, int var) {
        String typeeRename = getTypeRename(result,var);
        String lineO = getLineOrigin(result,var);
        String nameMethodO = extractClassName(result,var)+"."+getInvocationSrc(result,var);
        String lineD = getLineDestino(result,var);
        String nameMethodD = extractClassName(result,var)+"."+getInvocationDst(result,var);
        TempObject expectedObject = new TempObject(typeeRename, lineO, nameMethodO, lineD, nameMethodD);
        return expectedObject;
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
