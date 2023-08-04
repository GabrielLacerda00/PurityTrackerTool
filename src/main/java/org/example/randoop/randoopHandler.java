package org.example.randoop;


import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class randoopHandler {
    public static void main(String[] args) throws IOException {

        /*String pathRandoopJar = "ProjetoPibic\\src\\main\\java\\com\\libs\\Randoop\\randoop-all-4.3.2.jar";
        String jarName02 = "ProjetoPibic\\libs\\RefactoringMiner\\bin\\RefactoringMiner";
        */

        createComanderRandoop("C:\\Users\\gabri\\ProjetoMercadinho\\bin",
                "C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsInJSONFile.txt",
                "C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\comuns_methods.txt",
                "/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/randoop/Output");
        /*createComanderRandoop("C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\ProjetoMercadinho\\bin","C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsInJSONFile.txt"
        ,"C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\comuns_methods.txt");*/
        executeComand("/Users/gabriellacerda/GitHubGabrielLacerda/PurityTrackerFluxograma01/PurityTrackerTool/src/main/java/org/example/libs/Randoop/randoop-all-4.3.2.jar");
    }

    public randoopHandler() {
    }

    public static void createComanderRandoop(String pathProjectBIN, String pathClassTXT, String pathMethodsTXT, String pathResult) throws IOException {
        String userDir = System.getProperty("user.dir");
        String os = System.getProperty("os.name").toLowerCase();
        String pathRandoopJar = "";


        if (os.contains("win")) { // Verifica se é Windows
            pathRandoopJar = userDir +"\\PurityTrackerTool\\src\\main\\java\\org\\example\\libs\\Randoop\\randoop-all-4.3.2.jar";
        } else if (os.contains("mac")) { // Verifica se é macOS
            pathRandoopJar = userDir + "/PurityTrackerTool/src/main/java/org/example/libs/Randoop/randoop-all-4.3.2.jar";
        } else { // Assume que é Linux ou outro sistema UNIX-like
            pathRandoopJar = userDir +"/PurityTrackerTool/src/main/java/org/example/libs/Randoop/randoop-all-4.3.2.jar";
        }


        String inicialComand = "java -classpath ";

        String comand = inicialComand +pathProjectBIN+ ";" + pathRandoopJar + " randoop.main.Main gentests --classlist="+ pathClassTXT+
                " --omit-methods-file="+pathMethodsTXT+ " --output-limit=100" + " --no-error-revealing-tests=true" +
                " --flaky-test-behavior=DISCARD" + " --junit-output-dir=" + pathResult ;

        createFileBATRandoop(comand);
    }

    private static void createFileBATRandoop(String comand) {
        try {
            // create a new file writer and pass the append parameter as true to append data
            FileWriter fileWriter = new FileWriter("scriptRandoop.bat", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            writer.println("cls");
            writer.println("ECHO on");
            writer.println(comand);
            writer.close();
            System.out.println("script created");
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    private static String getPathsGenerics(String classDir) throws IOException {
        List<String> pathGeneric = new ArrayList<>();
        String userDir = System.getProperty("user.dir");
        String fullPath = userDir + File.separator + classDir;
        try (Stream<Path> stream = Files.walk(Paths.get(fullPath))) {
            stream.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .forEach(pathGeneric::add);
        } catch (AccessDeniedException e) {
            System.out.println("Acesso Negado");
        }
        return pathGeneric.get(0);
    }

    public static void executeComand(String batFilePath) throws IOException {
        String command;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            command = "cmd /c start " + batFilePath;
        } else if (osName.startsWith("Linux") || osName.startsWith("Mac")) {
            command = "sh " + batFilePath;
        } else {
            System.out.println("Sistema operacional não suportado.");
            return;
        }

        try {
            // Use a classe Runtime para executar o comando apropriado
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void clearBat(String batFilePath) throws IOException {
        try {
            // create a new file writer and pass the append parameter as true to append data
            FileWriter fileWriter = new FileWriter("scriptRandoop.bat", true);
            PrintWriter writer = new PrintWriter(fileWriter);

            writer.println("pause");
            writer.println("break > "+"%0");
            writer.close();
            System.out.println("script created");
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static List<String> getDirectoryPaths(String directoryName) throws IOException {
        List<String> pathDirectories = new ArrayList<>();
        String userDir = System.getProperty("user.dir");
        try (Stream<Path> stream = Files.walk(Paths.get(userDir))) {
            stream.filter(Files::isDirectory)
                    .map(Path::toString)
                    .filter(path -> path.endsWith(File.separator + directoryName))
                    .forEach(pathDirectories::add);
        } catch (AccessDeniedException e) {
            System.out.println("Access denied: " + e.getMessage());
        }
        return pathDirectories;
    }

    public static String getpathDir(String path) {
        Path caminhoCompleto = Paths.get(path).toAbsolutePath();
        return caminhoCompleto.toString();
    }


}
