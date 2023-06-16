package org.example.guumTreeDiff;

import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.objectOutputRefactMiner;
import org.example.util.TempObject;

import org.example.util.filePathFinderVersao01;
import org.example.util.filePathFinderVersao02;

public class gumTeeDiffOutputHandler {

    static ArrayList<String> MyList = new ArrayList<>();

    static ArrayList<TempObject> updateMethodsObjects = new ArrayList<TempObject>();

    static ArrayList<TempObject> updateInvocationObjects = new ArrayList<TempObject>();

    static ArrayList<renameMethodObject> renameMethodObjects = new ArrayList<>();
    static ArrayList<renameMethodObject> rMethodObjects = new ArrayList<>();

    static Diff result;

    public static void main(String[] args) throws Exception {

        //C:\Users\gabri\versao01\ExtractMethodExample
        //C:\Users\gabri\versao01\MoveMethodExample

        //C:\Users\gabri\versao01\InlineMethodExample
        //C:\Users\gabri\versao01\InlineOneMethodExample\InlineMethodExample

        //C:\Users\gabri\versao01\PullUpMethod

        //C:\Users\gabri\versao01\RenameMethodExample
        //C:\Users\gabri\versao01\RenameMethodsExample\RenameMethodExample
        String pathDir01 = "C:\\Users\\gabri\\versao01\\InlineMethodExample";
        //C:\Users\gabri\versao02\ExtractMethodExample
        //C:\Users\gabri\versao02\MoveMethodExample

        //C:\Users\gabri\versao02\InlineMethodExample
        //C:\Users\gabri\versao02\InlineOneMethodExample\InlineMethodExample

        //C:\Users\gabri\versao02\PullUpMethod

        //C:\Users\gabri\versao02\RenameMethodExample
        //C:\Users\gabri\versao02\RenameMethodsExample\RenameMethodExample
        String pathDir02 = "C:\\Users\\gabri\\versao02\\InlineMethodExample";
        ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(pathDir01);
        ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(pathDir02);

        int tamanho = Math.min(filePaths1.size(), filePaths2.size());

        for (int i = 0; i < tamanho; i++) {
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            result = new AstComparator().compare(file1, file2);

            extractDetailsUpdateMethod(result);
            extractDetailsInvocationMethod(result);
            for (TempObject objUpdateMethod: updateMethodsObjects) {
                for (TempObject objUpdateInvocation: updateInvocationObjects ) {
                    renameMethodObject renameMethodObj = new renameMethodObject(objUpdateMethod,objUpdateInvocation);
                    renameMethodObjects.add(renameMethodObj);
                }
            }
            for (renameMethodObject ren:
                    renameMethodObjects) {
                System.out.println(ren);
            }
        }
       /*for (String var:
                MyList) {
            System.out.println(var);
        }*/
    }

    private static void extractMethodDetailsss(String toString) {
        Pattern pattern = Pattern.compile("(\\w+)(?: (\\w+))? at .*:(\\d+)");
        Matcher matcher = pattern.matcher(toString);

        while (matcher.find()) {
            String updateType = matcher.group(1);
            String methodName = matcher.group(2);
            String result = updateType + " " + methodName;
            //int number = Integer.parseInt(matcher.group(3));

            //System.out.println(updateType + " " +methodName);

            if (methodName != null) {
                MyList.add(result);
            } else {
                MyList.add(updateType);
            }
        }

    }

    private static ArrayList<TempObject> extractDetailsUpdateMethod(Diff result) {

        for (int var = 0; var < (result.getAllOperations().size()/2); var++) {
            String typeRename = getTypeRename(result,var);
            String lineOrigin = getLineOrigin(result,var);
            String nameMethodOrigin = extractMethodDetails(result.getAllOperations().get(var).getSrcNode().toString());
            String lineDest = getLineDestino(result,var);
            String nameMethodDst = extractMethodDetails(result.getAllOperations().get(var).getDstNode().toString());
            TempObject updateMethodObject = new TempObject(typeRename,lineOrigin,nameMethodOrigin,lineDest,nameMethodDst);
            updateMethodsObjects.add(updateMethodObject);
            System.out.println(updateMethodObject.toStringMethod());
        }
        return updateMethodsObjects;
    }

    private static String getTypeRename(Diff result, int var) {
        return "Update "+result.getAllOperations().get(var).getAction().getNode().getType().toString();
    }

    private static ArrayList<TempObject> extractDetailsInvocationMethod(Diff result) {
        for (int var = (result.getAllOperations().size()/2); var < (result.getAllOperations().size()); var++) {
            String typeRename = getTypeRename(result,var);
            String lineO = getLineOrigin(result,var);
            String nameMethodO = getInvocationSrc(result,var);
            String lineD = getLineDestino(result,var);
            String nameMethodD = getInvocationDst(result,var);
            TempObject updateInvocationObject = new TempObject(typeRename,lineO,nameMethodO,lineD,nameMethodD);
            updateInvocationObjects.add(updateInvocationObject);
            System.out.println(updateInvocationObject.toStringInvocation());;
        }
        return updateInvocationObjects;
    }


    private static String getLineOrigin(Diff result, int var) {
        return Integer.toString(result.getAllOperations().get(var).getSrcNode().getPosition().getLine());
    }
    private static String getLineDestino(Diff result, int var){
        return Integer.toString(result.getAllOperations().get(var).getDstNode().getPosition().getLine());
    }

    private static String getInvocationSrc(Diff result,int var){
        return result.getAllOperations().get(var).getSrcNode().toString();
    }

    private static String getInvocationDst(Diff diff, int var){
        return result.getAllOperations().get(var).getDstNode().toString();
    }

    private static String extractMethodDetails(String param) {

        String methodString = param;

        String result = "";

        String regex = "([a-z]+) ([a-z]+)\\((.*)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(methodString);

        if (matcher.find()) {
            result += matcher.group(2);

            String params = matcher.group(3);
            String[] paramList = params.split(", ");
            String[] paramTypes = new String[paramList.length];

            for (int p = 0; p < paramList.length; p++) {
                paramTypes[p] = paramList[p].split(" ")[0];
            }
            result += Arrays.toString(paramTypes);
        }
        return result;
    }

    public static ArrayList<renameMethodObject> getRenameMethodObjects() {
        return renameMethodObjects;
    }
}
