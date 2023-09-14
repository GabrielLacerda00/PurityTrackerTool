package org.example.util;


import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import org.eclipse.jdt.internal.compiler.lookup.MethodVerifier;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.randoop.utilsRandoop.FileSearcher;
import org.example.refactoringMiner.SourcePathFinder;
import org.example.util.callerWaited;


public class methodVerifierTest {
    private static ArrayList<String> callers = new ArrayList<>();
    private static ArrayList<callerWaited> callersMethod = new ArrayList<>();

    private static String pathRoot;


    private static class MethodVisitor extends VoidVisitorAdapter<Void> {
        private String targetMethodName;
        private String foundInClass;

        public MethodVisitor(String targetMethodName) {
            this.targetMethodName = targetMethodName;
            this.foundInClass = null;
        }

        @Override
        public void visit(ClassOrInterfaceDeclaration cid, Void arg) {
            super.visit(cid, arg);
            for (MethodDeclaration md : cid.getMethods()) {
                if (md.getNameAsString().equals(targetMethodName)) {
                    this.foundInClass = cid.getNameAsString();
                    break;
                }
            }

            // If the method was not found and this class extends another class, check the superclass
            if (this.foundInClass == null && cid.getExtendedTypes().size() > 0) {
                String superclass = cid.getExtendedTypes().get(0).getNameAsString();
                String superclassFilePath = findSuperclassFilePath(cid.getNameAsString(), superclass);
                if (superclassFilePath != null) {
                    // Perform recursive call to search in the superclass
                    this.foundInClass = verifyMethod(superclassFilePath, targetMethodName);
                    //System.out.println(cid.getBegin().get().line);
                }
            }
        }

        public String getFoundInClass() {
            return foundInClass;
        }

    }

    public static String verifyMethod(String javaFilePath, String methodName) {
        ReflectionTypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        JavaSymbolSolver symbolSolver = new JavaSymbolSolver(reflectionTypeSolver);

        ParserConfiguration parserConfiguration = new ParserConfiguration();
        parserConfiguration.setSymbolResolver(symbolSolver);
        JavaParser javaParser = new JavaParser(parserConfiguration);

        try (FileInputStream in = new FileInputStream(javaFilePath)) {
            CompilationUnit cu = javaParser.parse(in).getResult().orElse(null);

            if (cu == null) {
                throw new RuntimeException("Unable to parse file");
            }

            findLine(cu,methodName);

            MethodVisitor methodVisitor = new MethodVisitor(methodName);
            methodVisitor.visit(cu, null);

            return methodVisitor.getFoundInClass();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void findLine(CompilationUnit cu, String methodName){

        MethodVisitor methodVisitor = new MethodVisitor(methodName);
        methodVisitor.visit(cu, null);
        cu.findAll(MethodCallExpr.class, expr -> expr.getName().getIdentifier().equals(methodName))
                .forEach(expr -> callers.add((methodVisitor.getFoundInClass()  + "." + methodName + "#" + expr.getRange().get().begin.line)));
    }

    private static String findSuperclassFilePath(String currentClassName, String superclass) {
        String currentFilePath = MethodVerifier.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        Path currentDirectory = Paths.get(currentFilePath).getParent();

        findFileInAncestors(pathRoot, superclass+ ".java");
        Path superclassPath = Path.of(pathRoot + superclass + ".java");

        //System.out.println(superclass);
        //System.out.println(superclassPath);

        if (Files.exists(superclassPath)) {
            return superclassPath.toString();
        }

        System.out.println("Superclass file not found for class: " + currentClassName);
        return null;
    }

    public static void main(String[] args) {
        String javaFilePath = SourcePathFinder.findClassInProject("MyObj", "/Users/gabriellacerda/GitHubGabrielLacerda/SuiteTestMiniProjects/OneRenameForTwoCallersInOtherClasses/Calculator");
        String methodName = "add";
        //handlerVerifier("/Users/gabriellacerda/GitHubGabrielLacerda/SuiteTestMiniProjects/OneRenameForTwoCallersInOtherClasses/Calculator/src/main/java/org/example/p1","MyObj.java",
        //       "add");
        handlerVerifier(javaFilePath,"",methodName);
    }

    public static  ArrayList<callerWaited> handlerVerifier(String path,String fileName, String methodName) {

        pathRoot = extractDirectoryFromPath(path)+"/";
        //System.out.println(pathRoot);
        //String fullPath = path + "/" + fileName;
        //System.out.println(path);
        verifyMethod(path, methodName);
        for (String c : callers){
            String[] splitResult = c.split("#");
            System.out.println(splitResult[1]);
            System.out.println(splitResult[0]);
            callerWaited callerMethod = new callerWaited(splitResult[1], splitResult[0]);
            callersMethod.add(callerMethod);
        }
        //callersMethod.forEach(System.out::println);
        callers.clear();
        return callersMethod;
    }

    public static Path findFileInAncestors(String rootPath, String fileName) {
        Path root = FileSystems.getDefault().getPath(rootPath);

        while (root != null) {
            final Path[] fileFound = new Path[1];

            try {
                Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        if (file.getFileName().toString().equals(fileName)) {
                            fileFound[0] = file;
                            return FileVisitResult.TERMINATE;
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFileFailed(Path file, IOException e) {
                        System.out.println("Failed to access file: " + file);
                        e.printStackTrace();
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch(IOException e) {
                e.printStackTrace();
            }

            if (fileFound[0] != null) {
                return fileFound[0];
            }

            root = root.getParent();
        }

        return null;
    }

    public static ArrayList<callerWaited> getCallersMethod() {
        return callersMethod;
    }

    public static String removeFileName(String path) {
        Pattern pattern = Pattern.compile("^(.*/)[^/]+$");
        Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return path;
    }
    public static String extractClassName(String path) {
        //captura qualquer coisa que não seja uma barra até encontrar um ponto (.)
        Pattern pattern = Pattern.compile("([^/\\\\]+)\\.java$");
        Matcher matcher = pattern.matcher(path);

        if (matcher.find()) {
            return matcher.group(1);  // Retorna o primeiro grupo de captura, que é o nome do arquivo sem a extensão
        } else {
            return "";
        }
    }

    public static String extractDirectoryFromPath(String path) {
        Path inputPath = Paths.get(path);
        Path parentPath = inputPath.getParent(); // obtém o diretório pai do caminho completo
        return parentPath != null ? parentPath.toString() : "";
    }
}
