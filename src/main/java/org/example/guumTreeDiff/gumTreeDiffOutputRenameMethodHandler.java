package org.example.guumTreeDiff;

import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.refactoringTypes.renameMethodObject;
import org.example.util.TempObject;

import org.example.util.filePathFinderVersao01;
import org.example.util.filePathFinderVersao02;

public class gumTreeDiffOutputRenameMethodHandler {

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
        String pathDir01 = "C:\\Users\\gabri\\versao01\\RenameMethodExample";
        //C:\Users\gabri\versao02\ExtractMethodExample
        //C:\Users\gabri\versao02\MoveMethodExample

        //C:\Users\gabri\versao02\InlineMethodExample
        //C:\Users\gabri\versao02\InlineOneMethodExample\InlineMethodExample

        //C:\Users\gabri\versao02\PullUpMethod

        //C:\Users\gabri\versao02\RenameMethodExample
        //C:\Users\gabri\versao02\RenameMethodsExample\RenameMethodExample
        String pathDir02 = "C:\\Users\\gabri\\versao02\\RenameMethodExample";
        ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(pathDir01);
        ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(pathDir02);

        int tamanho = Math.min(filePaths1.size(), filePaths2.size());

        for (int i = 0; i < tamanho; i++) {
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            result = new AstComparator().compare(file1, file2);
            /*for (int j = 0; j < result.getRootOperations().size(); j++) {
                System.out.println(result.getRootOperations().size());
                System.out.println(result.getRootOperations().get(j).getDstNode());
                System.out.println(result.getRootOperations().get(j).getDstNode().getPosition().getLine());
                System.out.println(result.getRootOperations().get(j).getSrcNode().toString());
            }*/
            extractDetailsUpdateMethod(result);
            extractDetailsInvocationMethod(result);
            System.out.println("-------------------------------------");

            for (int x = 0; x < updateMethodsObjects.size(); x++) {
                TempObject objUpdateMethod = updateMethodsObjects.get(x);
                TempObject objUpdateInvocation = updateInvocationObjects.get(x);
                renameMethodObject renameMethodObj = new renameMethodObject(objUpdateMethod, objUpdateInvocation);
                renameMethodObjects.add(renameMethodObj);

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

    public static void renameMethodHandler(String path01,String path02) throws Exception {
       path01 = "C:\\Users\\gabri\\versao01\\RenameMethodsExample\\RenameMethodExample";
       path02 = "C:\\Users\\gabri\\versao02\\RenameMethodsExample\\RenameMethodExample";
       ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(path01);
       ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(path02);

        int tamanho = Math.min(filePaths1.size(), filePaths2.size());

        for (int i = 0; i < tamanho; i++) {
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            result = new AstComparator().compare(file1, file2);

            //extractDetailsUpdateMethod(result);
            //extractDetailsInvocationMethod(result);

            for (int x = 0; x < updateMethodsObjects.size(); x++) {
                TempObject objUpdateMethod = updateMethodsObjects.get(x);
                TempObject objUpdateInvocation = updateInvocationObjects.get(x);
                renameMethodObject renameMethodObj = new renameMethodObject(objUpdateMethod, objUpdateInvocation);
                renameMethodObjects.add(renameMethodObj);

            }

            for (renameMethodObject ren:
                    renameMethodObjects) {
                System.out.println(ren);
            }
        }
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

        for (int var = 0; var < (result.getRootOperations().size()/2); var++) {
            String typeRename = getTypeRename(result,var);
            String lineOrigin = getLineOrigin(result,var);
            String nameMethodOrigin = getInvocationSrc(result,var);
            String lineDest = getLineDestino(result,var);
            String nameMethodDst = getInvocationDst(result,var);
            TempObject updateMethodObject = new TempObject(typeRename,lineOrigin,nameMethodOrigin,lineDest,nameMethodDst);
            updateMethodsObjects.add(updateMethodObject);
            System.out.println(updateMethodObject.toStringMethod());
        }
        return updateMethodsObjects;
    }

    private static String getTypeRename(Diff result, int var) {
        String[] types =result.getRootOperations().get(var).getAction().getName().split("-");
        String type = types[0];

        return type + " "+result.getRootOperations().get(var).getAction().getNode().getType().toString();
        //return "Update "+result.getAllOperations().get(var).getAction().getNode().getType().toString();
    }

    private static ArrayList<TempObject> extractDetailsInvocationMethod(Diff result) {
        for (int var = (result.getRootOperations().size()/2); var < (result.getRootOperations().size()); var++) {
            String typeeRename = getTypeRename(result,var);
            String lineO = getLineOrigin(result,var);
            String nameMethodO = getInvocationSrc(result,var);
            String lineD = getLineDestino(result,var);
            String nameMethodD = getInvocationDst(result,var);
            TempObject updateInvocationObject = new TempObject(typeeRename,lineO,nameMethodO,lineD,nameMethodD);
            updateInvocationObjects.add(updateInvocationObject);
            System.out.println(updateInvocationObject.toStringInvocation());
        }
        return updateInvocationObjects;
    }

    private static String getLineOrigin(Diff result, int var) {
        return Integer.toString(result.getRootOperations().get(var).getSrcNode().getPosition().getLine());
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
        return extractMethodName(result.getRootOperations().get(var).getSrcNode().toString());
    }

    private static String getInvocationDst(Diff result, int var){
        //return extractMethodName(result.getRootOperations().get(var).getDstNode().toString());
        String name="";
        if(result.getRootOperations().get(var).getDstNode() == null){
            name = "null";
        }
        else{
            name = extractMethodName(result.getRootOperations().get(var).getDstNode().toString());
        }
        return name;
    }



    public static void extractMethodCalls(String code) {
        String regex = "\\b\\w+\\b\\([^\\)]\\)(\\s;|(?=\\s*\\n))";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(code);

        while (matcher.find()) {
            System.out.println(matcher.group().trim());
        }
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

   public static ArrayList<renameMethodObject> getRenameMethodObjects() {
        return renameMethodObjects;
    }
}
