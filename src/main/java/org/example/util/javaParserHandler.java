
package org.example.util;

/*
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        FileInputStream input;
        input = new FileInputStream("C:\\Users\\gabri\\untitled\\src\\main\\java\\org\\example\\TestClass.java");
        CompilationUnit compilation;
        compilation = new JavaParser().parse(input).getResult().orElse(null);

        if (compilation != null) {
            new MethodVisitor().visit(compilation, null);
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);
            System.out.println("Declaração do Método: " + n.getName());
            System.out.println("--------------------");
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
            super.visit(n, arg);
            System.out.println("Chamadas do Método: " + n.getName());
        }

    }
}*//*

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    static ArrayList<String> methodsCallsAndDeclarations = new ArrayList<>();
    static String className = "";
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("(public|protected|private)?\\s*class\\s+(\\w+)");
    public static void main(String[] args) throws IOException {
        callersHandler("C:\\Users\\gabri\\versao02\\RenameMethodExample");
    }
    public static void callersHandler(String pathh) throws IOException {
        String directoryPath = pathh;
        Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(Main::processJavaFile);

        addClassNameToCallersAndDeclarations(methodsCallsAndDeclarations,className);
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
            methodsCallsAndDeclarations.add(n.getNameAsString());
            //findeNameClass(n.getParentNode().toString());
        }
        @Override
        public void visit(MethodCallExpr n, Void arg) {
            super.visit(n, arg);
            //methodsCallsAndDeclarations.add(n.getNameAsString());
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
            list.set(i, additionalText +"."+ list.get(i));
        }
    }
}


*/
/*
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static ArrayList<String> methodsCallsAndDeclarations = new ArrayList<>();
    static String className = "";
    private static final Pattern CLASS_NAME_PATTERN = Pattern.compile("(public|protected|private)?\\s*class\\s+(\\w+)");

    public static void main(String[] args) throws IOException {
        callersHandler("C:\\Users\\gabri\\versao02\\RenameMethodExample");
    }

    public static void callersHandler(String pathh) throws IOException {
        String directoryPath = pathh;
        Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(filePath -> processJavaFile(filePath, "minus"));

    }

    private static void processJavaFile(Path path, String methodName) {
        try {
            CompilationUnit compilationUnit = StaticJavaParser.parse(path);
            new MethodVisitor(methodName).visit(compilationUnit, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        private final String methodName;

        public MethodVisitor(String methodName) {
            this.methodName = methodName;
        }

        @Override
        public void visit(MethodDeclaration n, Void arg) {
            super.visit(n, arg);
            if (n.getNameAsString().equals(methodName)) {
                System.out.println("Method declaration: " + n.getNameAsString());
                // Add your custom logic here for the found method call
            }
        }

        @Override
        public void visit(MethodCallExpr n, Void arg) {
            super.visit(n, arg);
            if (n.getNameAsString().equals(methodName)) {
                System.out.println("Method call found: " + n.getNameAsString());
                // Add your custom logic here for the found method call
            }
        }
    }
}
*/
/*
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.example.callerWaited;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    private static String TARGET_METHOD_NAME = "";

    private static ArrayList<callerWaited> callersMethod = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        mainCallersHandler("C:\\Users\\gabri\\versao02\\RenameMethodExample2.0","soma");
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
                System.out.println(declarationLine);
                System.out.println("Method declaration: " +declaringClassName+"."+ n.getNameAsString());
            }
        }

        @Override
        public void visit(MethodCallExpr n, String filePath) {
            super.visit(n, filePath);
            if (n.getNameAsString().equals(TARGET_METHOD_NAME)) {
                String callingClassName = getClassName(filePath);
                String callLine = Integer.toString(n.getBegin().get().line);
                System.out.println(callLine);
                System.out.println("Method call found: "+callingClassName+"." + n.getNameAsString());
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
}
*/
import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.visitor.*;
import org.example.util.callerWaited;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class javaParserHandler {
    private static String TARGET_METHOD_NAME = "";
    private static String CURRENT_CLASS_NAME = "";

    private static ArrayList<callerWaited> callersMethod = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        mainCallersHandler("/Users/gabriellacerda/GitHubGabrielLacerda/backup_Projects_test/versao02/RenameMethodExample2.0", "soma");
        for (callerWaited callMe : callersMethod) {
            System.out.println(callMe);
        }
    }

    public static void mainCallersHandler(String path, String className) throws IOException {
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
            String currentClassName = getClassName(path.toString());
            CURRENT_CLASS_NAME = currentClassName;
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
                //System.out.println(declarationLine);
                //System.out.println("Method declaration: " + declaringClassName + "." + n.getNameAsString());
            }
        }

        @Override
        public void visit(MethodCallExpr n, String filePath) {
            super.visit(n, filePath);
            if (n.getNameAsString().equals(TARGET_METHOD_NAME)) {
                String callingClassName = CURRENT_CLASS_NAME;
                String callLine = Integer.toString(n.getBegin().get().line);
                //System.out.println(callLine);
                //System.out.println("Method call found: " + callingClassName + "." + n.getNameAsString());
                String fullName = callingClassName + "." + n.getNameAsString();
                callerWaited callerMethod = new callerWaited(callLine, fullName);
                callersMethod.add(callerMethod);
            }
        }
    }

    private static String getClassName(String filePath) {
        int lastSeparatorIndex = filePath.lastIndexOf(System.getProperty("file.separator"));
        int extensionIndex = filePath.lastIndexOf(".java");
        if (lastSeparatorIndex != -1 && extensionIndex != -1) {
            return filePath.substring(lastSeparatorIndex + 1, extensionIndex);
        }
        return "";
    }
    public static ArrayList<callerWaited> getCallersMethod() {
        return callersMethod;
    }
}
