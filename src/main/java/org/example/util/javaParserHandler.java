package org.example.util;

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


public class javaParserHandler {
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
                .forEach(javaParserHandler::processJavaFile);

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
            findeNameClass(n.getParentNode().toString());
        }
        @Override
        public void visit(MethodCallExpr n, Void arg) {
            super.visit(n, arg);
            methodsCallsAndDeclarations.add(n.getNameAsString());
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
