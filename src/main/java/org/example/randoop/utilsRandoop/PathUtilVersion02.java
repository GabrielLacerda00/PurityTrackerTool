package org.example.randoop.utilsRandoop;


public class PathUtilVersion02 {

    private String  pathV02;

    public PathUtilVersion02(){
        this.pathV02 = "";
    }

    public String getRootFilePath() {
        String os = System.getProperty("os.name").toLowerCase();
        String rootPath;
        String diretorio = System.getProperty("user.dir");
        if (os.contains("win")) { // Verifica se é Windows
            rootPath = diretorio + "\\"+"src\\main\\java\\org\\example\\txtFiles\\";
        } else if (os.contains("mac")) { // Verifica se é macOS
            rootPath = diretorio+"/"+"src/main/java/org/example/txtFiles/";
        } else { // Assume que é Linux ou outro sistema UNIX-like
            rootPath = diretorio+"/"+"src/main/java/org/example/txtFiles/";
        }

        return rootPath;
    }

    public  String getPathFromRoot(String relativePath) {
        String rootPath = getRootFilePath();
        //String separator = System.getProperty("file.separator");
        pathV02 = rootPath+relativePath;
        return rootPath+relativePath;
    }

    public String getPath() {
        return pathV02;
    }

    public static void main(String[] args) {
        // Exemplo de uso
        String relativePath = "methodsVersion02.txt"; // Insira aqui o caminho relativo do arquivo desejado
        PathUtilVersion02 pahtUt = new PathUtilVersion02();
        String fullPath = pahtUt.getPathFromRoot(relativePath);
        System.out.println("Caminho completo: " + fullPath);
    }
}
