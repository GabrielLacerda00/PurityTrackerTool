package org.example.guumTreeDiff;

import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.refactoringTypes.inlineMethodObject;
import org.example.util.TempObject;
import org.example.util.filePathFinderVersao01;
import org.example.util.filePathFinderVersao02;


public class gumTreeDiffOutputInlineMethodHandler {

    static ArrayList<String> MyList = new ArrayList<>();

    static ArrayList<TempObject> deletes = new ArrayList<TempObject>();

    static ArrayList<TempObject> moves = new ArrayList<TempObject>();

    static ArrayList<inlineMethodObject> InlineMethods = new ArrayList<>();

    static Diff result;

    public static void main(String[] args) throws Exception {

        //C:\Users\gabri\versao01\ExtractMethodExample
        //C:\Users\gabri\versao01\MoveMethodExample

        //C:\Users\gabri\versao01\InlineMethodExample
        //C:\Users\gabri\versao01\InlineOneMethodExample\InlineMethodExample

        //C:\Users\gabri\versao01\PullUpMethod

        //C:\Users\gabri\versao01\RenameMethodExample
        //C:\Users\gabri\versao01\RenameMethodsExample\RenameMethodExample
        String pathDir01 = "/Users/gabriellacerda/GitHubGabrielLacerda/backup_Projects_test/versao01/RenameMethodExample";
        //C:\Users\gabri\versao02\ExtractMethodExample
        //C:\Users\gabri\versao02\MoveMethodExample

        //C:\Users\gabri\versao02\InlineMethodExample
        //C:\Users\gabri\versao02\InlineOneMethodExample\InlineMethodExample

        //C:\Users\gabri\versao02\PullUpMethod

        //C:\Users\gabri\versao02\RenameMethodExample
        //C:\Users\gabri\versao02\RenameMethodsExample\RenameMethodExample
        String pathDir02 = "/Users/gabriellacerda/GitHubGabrielLacerda/backup_Projects_test/versao02/RenameMethodExample";
        ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(pathDir01);
        ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(pathDir02);


        int tamanho = Math.min(filePaths1.size(), filePaths2.size());


        for (int i = 0; i < tamanho; i++) {
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            result = new AstComparator().compare(file1, file2);
            //parseString(result.toString());
            System.out.println(result.toString());
            System.out.println("-------------------");

            //extractMethodDetailsss(result.toString());

            extractDetailsDeleteAndAssignmentMethod(result);
            extractDetailsMoves(result);
            for (TempObject objUpdateMethod: deletes) {
                for (TempObject objUpdateInvocation: moves) {
                    inlineMethodObject inlineMethodObj = new inlineMethodObject(objUpdateMethod,objUpdateInvocation);
                    InlineMethods.add(inlineMethodObj);
                }
            }
            for (inlineMethodObject ren:
                    InlineMethods) {
                System.out.println(ren.toString());
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
    private static ArrayList<TempObject> extractDetailsDeleteAndAssignmentMethod(Diff result) {
        for (int var = 0; var < (result.getRootOperations().size()/2); var++) {
            //System.out.println(result.getRootOperations().get(j).getSrcNode().getPosition().getLine());
            String lineOrigin = getLineOrigin(result,var);
            String nameMethodOrigin = getMethodSrc(result,var);
            //System.out.println(Arrays.toString(result.getRootOperations().get(var).getAction().getName().split("-")));
            //System.out.println(result.getRootOperations().get(var).getAction().getNode().getType().toString());
            String type = getType(result,var);
            //System.out.println(result.getRootOperations().get(var).getSrcNode().toString());
            String LineDest = "";
            String nameMethodD = getMethodDst(result,var);
            TempObject deleteAndAssignmentMethodObject = new TempObject(type,lineOrigin,nameMethodOrigin,LineDest,nameMethodD);
            deletes.add(deleteAndAssignmentMethodObject);
            System.out.println(deleteAndAssignmentMethodObject);
        }

        return deletes;
    }


    private static ArrayList<TempObject> extractDetailsMoves(Diff result) {
        for (int var = (result.getRootOperations().size()/2); var < result.getRootOperations().size(); var++) {
            String lineO = getLineOrigin(result,var);
            String nameMethodO = getMethodSrc(result,var);
            String lineD = getLineDestino(result,var);
            String nameMethodD = getMethodDst(result,var);
            String type = getType(result,var);
            TempObject MovesObject = new TempObject(type,lineO,nameMethodO,lineD,nameMethodD);
            moves.add(MovesObject);
            System.out.println(MovesObject.toString());;
        }
        return moves;
    }


    private static String getLineOrigin(Diff result, int var) {

        return Integer.toString(result.getRootOperations().get(var).getSrcNode().getPosition().getLine());
    }
    private static String getLineDestino(Diff result, int var){
        String linha = "";
        if(result.getRootOperations().get(var).getDstNode() == null){
            linha = "null";
        }else{
            linha = Integer.toString(result.getRootOperations().get(var).getDstNode().getPosition().getLine());
        }
        return linha;
    }

    private static String getMethodSrc(Diff result,int var){
        return extractMethodName(result.getRootOperations().get(var).getSrcNode().toString());
    }

    private static String getMethodDst(Diff result, int var){
        String name="";
        if(result.getRootOperations().get(var).getDstNode() == null){
            name = "null";
        }
        else{
            name = extractMethodName(result.getRootOperations().get(var).getDstNode().toString());
        }
        return name;
    }
    private static String getType(Diff result,int var){
        String[] types =result.getRootOperations().get(var).getAction().getName().split("-");
        String type = types[0];

        return type + " "+result.getRootOperations().get(var).getAction().getNode().getType().toString();
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


}
