package org.example.util;
/*
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class javaParserHandler {
    static ArrayList<String> methodsDeclarations = new ArrayList<>();
    static ArrayList<String> methodsCalls = new ArrayList<>();
    static String className = "";

    private String path;
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("(public|protected|private)?\\s*class\\s+(\\w+)");

    public javaParserHandler(String path01) throws IOException {
        this.path = path01;
        run();
    }

    public void run() throws IOException {
        String directoryPath = path;
        Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(javaParserHandler::processJavaFile);

        addClassNameToCallersAndDeclarations(methodsDeclarations,className);
        addClassNameToCallersAndDeclarations(methodsCalls,className);
    }

    private static void processJavaFile(Path path) {
        try {
            CompilationUnit compilationUnit = StaticJavaParser.parse(path);
            new MethodVisitor().visit(compilationUnit, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);
            methodsDeclarations.add(n.getNameAsString());
            findeNameClass(n.getParentNode().toString());
        }
        @Override
        public void visit(MethodCallExpr n, Void arg) {
            super.visit(n, arg);
            methodsCalls.add(n.getNameAsString());
        }

    }

    private static void findeNameClass(String input){
        input = input.replace("Optional[", "").replace("]", "");

        Matcher nameMatcher = CLASS_NAME_PATTERN.matcher(input);
        if (nameMatcher.find()) {
            className = nameMatcher.group(2);
        }
    }

    private static void addClassNameToCallersAndDeclarations(ArrayList<String> list, String additionalText) {
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).split("\\.").length == 1){
                list.set(i, additionalText +"."+ list.get(i));
            }

        }
    }


    public  ArrayList<String> getMethodsCalls() {
        return methodsCalls;
    }

    public  ArrayList<String> getMethodsDeclarations() {
        return methodsDeclarations;
    }
}*/
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.example.util.callerWaited;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class javaParserHandler {
    private static String TARGET_METHOD_NAME = "";

    private static ArrayList<callerWaited> callersMethod = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        mainCallersHandler("C:\\Users\\gabri\\versao02\\RenameMethodExample","sum");
        for (callerWaited callMe: callersMethod) {
            System.out.println(callMe);
        }
    }

    public  static void mainCallersHandler(String path,String className) throws IOException {
        TARGET_METHOD_NAME = className;
        callersHandler(path);
    }

    public static void callersHandler(String path) throws IOException {
        String directoryPath = path;
        Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(filePath -> filePath.toString().endsWith(".java"))
                .forEach(filePath -> processJavaFile(filePath));
    }

    private static void processJavaFile(Path path) {
        try {
            CompilationUnit compilationUnit = StaticJavaParser.parse(path);
            new MethodVisitor().visit(compilationUnit, path.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<String> {
        @Override
        public void visit(MethodDeclaration n, String filePath) {
            super.visit(n, filePath);
            if (n.getNameAsString().equals(TARGET_METHOD_NAME)) {
                String declaringClassName = getClassName(filePath);
                int declarationLine = n.getBegin().get().line;
                //System.out.println("Method declaration: " +declaringClassName+"."+ n.getNameAsString());
            }
        }

        @Override
        public void visit(MethodCallExpr n, String filePath) {
            super.visit(n, filePath);
            if (n.getNameAsString().equals(TARGET_METHOD_NAME)) {
                String callingClassName = getClassName(filePath);
                String callLine = Integer.toString(n.getBegin().get().line);
                //System.out.println("Method call found: "+callingClassName+"." + n.getNameAsString());
                String fullName = callingClassName+"."+n.getNameAsString();
                callerWaited callerMethod = new callerWaited(callLine,fullName);
                callersMethod.add(callerMethod);
            }
        }

        private String getClassName(String filePath) {
            int lastSeparatorIndex = filePath.lastIndexOf(System.getProperty("file.separator"));
            int extensionIndex = filePath.lastIndexOf(".java");
            if (lastSeparatorIndex != -1 && extensionIndex != -1) {
                return filePath.substring(lastSeparatorIndex + 1, extensionIndex);
            }
            return "";
        }
    }

    public static ArrayList<callerWaited> getCallersMethod() {
        return callersMethod;
    }
}