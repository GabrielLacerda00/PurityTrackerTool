package org.example.randoop.utilsRandoop;

public class PathUtilScriptRandoopBat {
    private String  path;

    public PathUtilScriptRandoopBat(){
        this.path = "";
    }

    public String getRootFilePath() {
        String os = System.getProperty("os.name").toLowerCase();
        String rootPath;
        String diretorio = System.getProperty("user.dir");
        if (os.contains("win")) { // Verifica se é Windows
            rootPath = diretorio +"\\"+"src\\main\\java\\org\\example\\randoop\\scriptRandoop\\";
        } else if (os.contains("mac")) { // Verifica se é macOS
            rootPath = diretorio+"/"+"src/main/java/org/example/randoop/scriptRandoop/";
        } else { // Assume que é Linux ou outro sistema UNIX-like
            rootPath = diretorio+"/"+"src/main/java/org/example/randoop/scriptRandoop/";
        }

        return rootPath;
    }

    public  String getPathFromRoot(String relativePath) {
        String rootPath = getRootFilePath();
        System.out.println(rootPath);
        //String separator = System.getProperty("file.separator");
        path = rootPath+relativePath;
        return rootPath+relativePath;
    }

    public String getPath() {
        return path;
    }

    public static void main(String[] args) {
        // Exemplo de uso
        String relativePath = "randoop-all-4.3.2.jar"; // Insira aqui o caminho relativo do arquivo desejado
        PathUtilScriptRandoopBat pahtUt = new PathUtilScriptRandoopBat();
        pahtUt.getPathFromRoot(relativePath);
        String fullPath = pahtUt.getPath() ;
        System.out.println("Caminho completo: " + fullPath);
    }
}
