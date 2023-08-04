package org.example.randoop.utilsRandoop;

public class PathUtilVersion01 {

    private String  path;

    public PathUtilVersion01(){
        this.path = "";
    }

    public String getRootFilePath() {
        String os = System.getProperty("os.name").toLowerCase();
        String rootPath = "";
        String diretorio = System.getProperty("user.dir");
        if (os.contains("win")) { // Verifica se é Windows
            rootPath = diretorio + "\\"+"src\\main\\java\\org\\example\\txtFiles\\";
        } else if (os.contains("mac")) { // Verifica se é macOS
            rootPath = diretorio+"/"+"src/main/java/org/example/txtFiles/";
        } else if (os.contains("linux")) { // Assume que é Linux ou outro sistema UNIX-like
            rootPath = diretorio+"/"+"src/main/java/org/example/txtFiles/";
        }

        return rootPath;
    }

    public  String getPathFromRoot(String relativePath) {
        String rootPath = getRootFilePath();
        //String separator = System.getProperty("file.separator");
        path = rootPath+relativePath;
        return rootPath+relativePath;
    }

    public String getPath() {
        return path;
    }

    public static void main(String[] args) {
        // Exemplo de uso
        String relativePath = "methodsVersion01.txt"; // Insira aqui o caminho relativo do arquivo desejado
        PathUtilVersion01 pahtUt = new PathUtilVersion01();
        String fullPath = pahtUt.getPathFromRoot(relativePath);
        System.out.println("Caminho completo: " + fullPath);
    }
}
