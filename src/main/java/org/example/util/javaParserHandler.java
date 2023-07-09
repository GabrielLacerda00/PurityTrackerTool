package org.example.util;
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
        /*mainCallersHandler("C:\\Users\\gabri\\versao02\\RenameMethodExample","sum");
        for (callerWaited callMe: callersMethod) {
            System.out.println(callMe);
        }*/
    }

    public  static void mainCallersHandler(String path,String methodName) throws IOException {
        TARGET_METHOD_NAME = methodName;
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
        public void visit(MethodCallExpr n, String filePath) {
            super.visit(n, filePath);

            if (n.getNameAsString().equals(TARGET_METHOD_NAME)) {
                String callingClassName = getClassName(filePath);
                String callLine = Integer.toString(n.getBegin().get().line);
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