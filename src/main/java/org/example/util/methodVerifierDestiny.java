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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class methodVerifierDestiny {
        private static ArrayList<String> callers = new ArrayList<>();
        private static ArrayList<callerWaited> callersMethod = new ArrayList<>();

        private static String pathRoot;

    public static void main(String[] args) {
        //String javaFilePath = "/Users/gabriellacerda/GitHubGabrielLacerda/backup_Projects_test/versao02/RenameMethodExample2.0/calculadora.java";
        //String methodName = "sum";
//        handlerVerifier("/Users/gabriellacerda/GitHubGabrielLacerda/backup_Projects_test/versao02/RenameMethodExample2.0","calculadora.java",
//                "soma");

    }


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

                org.example.util.methodVerifierDestiny.MethodVisitor methodVisitor = new org.example.util.methodVerifierDestiny.MethodVisitor(methodName);
                methodVisitor.visit(cu, null);

                return methodVisitor.getFoundInClass();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        private static void findLine(CompilationUnit cu, String methodName){

            org.example.util.methodVerifierDestiny.MethodVisitor methodVisitor = new org.example.util.methodVerifierDestiny.MethodVisitor(methodName);
            methodVisitor.visit(cu, null);
            cu.findAll(MethodCallExpr.class, expr -> expr.getName().getIdentifier().equals(methodName))
                    .forEach(expr -> callers.add((methodVisitor.getFoundInClass()  + "." + methodName + "#" + expr.getRange().get().begin.line)));
        }

        private static String findSuperclassFilePath(String currentClassName, String superclass) {
            String currentFilePath = methodVerifierDestiny.class.getProtectionDomain().getCodeSource().getLocation().getPath();
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



        public static  ArrayList<callerWaited> handlerVerifier(String path,String fileName, String methodName) {
            pathRoot = path+ "/";
            String fullPath = path + "/" + fileName;
            verifyMethod(fullPath, methodName);
            for (String c : callers){
                String[] splitResult = c.split("#");
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
    }


