package org.example.guumTreeDiff;

import com.fasterxml.jackson.databind.node.TextNode;
import gnu.trove.map.hash.THashMap;
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

import org.example.util.javaParserHandler;

public class gumTreeDiffOutputRenameMethodHandler {

    static ArrayList<String> myListCallsSrc = new ArrayList<>();
    static ArrayList<String> myListDeclarationsSrc = new ArrayList<>();

    static ArrayList<String> myListCallsDst = new ArrayList<>();

    static ArrayList<String> myListDeclarationsDst = new ArrayList<>();

    static ArrayList<TempObject> updateMethodsObjects = new ArrayList<TempObject>();

    static ArrayList<TempObject> updateInvocationObjects = new ArrayList<TempObject>();

    static HashMap<TempObject,renameMethodObject> renameMethodObjects = new HashMap<>();
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

        renameMethodHandler(pathDir01,pathDir02);
    }

    public static void renameMethodHandler(String path01,String path02) throws Exception {
       ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(path01);
       ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(path02);

        javaParserHandler p1 = new javaParserHandler(path01);
        myListCallsSrc = p1.getMethodsCalls();
        myListDeclarationsSrc = p1.getMethodsDeclarations();
        javaParserHandler p2 = new javaParserHandler(path02);
        myListCallsDst = p2.getMethodsCalls();
        myListDeclarationsDst = p2.getMethodsDeclarations();

        int tamanho = Math.min(filePaths1.size(), filePaths2.size());

        for (int i = 0; i < tamanho; i++) {
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            result = new AstComparator().compare(file1, file2);

            extractDetailsUpdateMethod(result);
            extractDetailsInvocationMethod(result);

            ArrayList<TempObject> listpares = mergeLists(updateMethodsObjects,updateInvocationObjects);

            int index = -1;
            for (int j = 0; j < listpares.size(); j+=2) {
                if(!renameMethodObjects.containsKey(listpares.get(j))){
                    renameMethodObjects.put(listpares.get(j),new renameMethodObject(listpares.get(j)));
                    index++;
                    renameMethodObjects.get(listpares.get(j)).setUpdateInvocationList(listpares.get(j+1));
                }else{
                    renameMethodObjects.get(listpares.get(j)).setUpdateInvocationList(listpares.get(j+1));
                }
            }

            for (renameMethodObject ren:
                    renameMethodObjects.values()) {
                System.out.println(ren);
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


            String checkedNameO = checkNamesMethodsDeclarationSRC(nameMethodOrigin);
            String checkedNameDst = checkeNameMethodsDeclarationDst(nameMethodDst);

            TempObject updateMethodObject = new TempObject(typeRename,lineOrigin,checkedNameO,lineDest,checkedNameDst);
            updateMethodsObjects.add(updateMethodObject);
            System.out.println(updateMethodObject.toStringMethod());
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
            String nameMethodO = getInvocationSrc(result,var);
            String lineD = getLineDestino(result,var);
            String nameMethodD = getInvocationDst(result,var); 

            String checkedNameMethodSRC = checkNamesMethodsCallsSrc(nameMethodO);
            String checkedNameMethodDST = checkNamesMethodsCallsDST(nameMethodD);

            TempObject updateInvocationObject = new TempObject(typeeRename,lineO,checkedNameMethodSRC,lineD,checkedNameMethodDST);
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

   public static HashMap<TempObject,renameMethodObject> getRenameMethodObjects() {
        return renameMethodObjects;
    }
    private static String checkeNameMethodsDeclarationDst(String nameMethodDst) {
        String strName = "";
        for (String name: myListDeclarationsDst) {
            if(name.contains(nameMethodDst)){
                strName = name;
            }
        }
        return strName;
    }

    private static String checkNamesMethodsDeclarationSRC(String nameMethodO) {
        String strName = "";
        for (String name: myListDeclarationsSrc) {
            if(name.contains(nameMethodO)){
                strName = name;
            }
        }
        return strName;
    }
    private static String checkNamesMethodsCallsDST(String nameMethod){
        String strName = "";
        for (String name: myListCallsDst) {
            if (name.contains(nameMethod)){
                strName = name;
            }
        }
        return strName;
    }
    private static String checkNamesMethodsCallsSrc(String nameMethod){
        String strName = "";
        for (String name: myListCallsSrc) {
            if (name.contains(nameMethod)){
                strName = name;
            }
        }
        return strName;
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
                }
            }
        }
        return listResult;
    }

    private static boolean check(TempObject updateKey, TempObject updateLock){
      return updateKey.getNameMethodOrigin().equals(updateLock.getNameMethodOrigin())
              && updateKey.getNameMethodDst().equals(updateLock.getNameMethodDst());
    }

}
