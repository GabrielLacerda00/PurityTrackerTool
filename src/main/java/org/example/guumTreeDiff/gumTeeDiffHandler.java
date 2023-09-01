package org.example.guumTreeDiff;

import gumtree.spoon.AstComparator;
import gumtree.spoon.diff.Diff;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.example.util.filePathFinderVersao01;
import org.example.util.filePathFinderVersao02;
public class gumTeeDiffHandler {
    public static void main(String[] args) throws Exception {

        String pathDir01 = "C:\\Users\\gabri\\versao01\\MoveMethodExample";
        String pathDir02 = "C:\\Users\\gabri\\versao02\\MoveMethodExample";
        ArrayList<String> filePaths1 = filePathFinderVersao01.getJavaFilePaths(pathDir01);
        ArrayList<String> filePaths2 = filePathFinderVersao02.getJavaFilePaths(pathDir02);


        int tamanho = Math.min(filePaths1.size(),filePaths2.size());


        for (int i = 0; i < tamanho; i++){
            File file1 = new File(filePaths1.get(i));
            File file2 = new File(filePaths2.get(i));
            final Diff result = new AstComparator().compare(file1, file2);
            extractMethodDetails(result.toString());
        }

    }
    private static void extractMethodDetails(String toString) {
        Pattern pattern = Pattern.compile("(\\w+)(?: (\\w+))? at .*:(\\d+)");
        Matcher matcher = pattern.matcher(toString);

        ArrayList<String> MyList = new ArrayList<>();

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

        ArrayList<String> methods = checkMethods(MyList);
        for (String method : methods) {
            System.out.println(method);
        }
    }

    private static ArrayList<String> checkMethods(ArrayList<String> states) {
        ArrayList<String> methods = new ArrayList<>();

        if (hasExtractMethod(states)) {
            methods.add("Extract Method");
        }
        if (hasInlineMethod(states)) {
            methods.add("Inline Method");
        }
        if (hasMoveMethod(states)) {
            methods.add("Move Method");
        }
        if (hasRenameMethod(states)) {
            methods.add("Rename Method");
        }
        if (hasPullUpMethod(states)) {
            methods.add("Pull Up Method");
        }
        if (hasPushDownMethod(states)) {
            methods.add("Push Down Method");
        }

        return methods;
    }

    private static boolean hasExtractMethod(ArrayList<String> states) {
        List<String> extractMethodStates = Arrays.asList("Add Functionality", "Update Statement", "Insert Statement",
                "Delete Statement", "Binding Problem");

        return states.containsAll(extractMethodStates);
    }

    private static boolean hasInlineMethod(ArrayList<String> states) {
        List<String> inlineMethodStates = Arrays.asList("Remove Functionality","Update Statement","Insert Statement");

        return states.containsAll(inlineMethodStates);
    }

    private static boolean hasMoveMethod(ArrayList<String> states) {
        List<String> moveMethodStates = Arrays.asList("Add Functionality", "Binding Problem", "Change Visibility");

        return states.containsAll(moveMethodStates);
    }

    private static boolean hasRenameMethod(ArrayList<String> states) {
        List<String> renameMethodStates = Arrays.asList("Rename Method", "Update Statement", "Binding Problem");

        return states.containsAll(renameMethodStates);
    }

    private static boolean hasPullUpMethod(ArrayList<String> states) {
        List<String> pullUpMethodStates = Arrays.asList("Binding Problem", "Remove Functionality", "Add Functionality",
                "Binding Problem");

        return states.containsAll(pullUpMethodStates);
    }

    private static boolean hasPushDownMethod(ArrayList<String> states) {
        List<String> pushDownMethodStates = Arrays.asList("Binding Problem", "Remove Functionality", "Add Functionality",
                "Binding Problem");

        return states.containsAll(pushDownMethodStates);
    }
}
