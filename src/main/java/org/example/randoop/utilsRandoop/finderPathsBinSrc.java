package org.example.randoop.utilsRandoop;

import java.io.File;
import java.io.IOException;

public class finderPathsBinSrc {
    private static String pathBIN;
    private static String pathSRC;

    public static void main(String[] args) throws IOException {
        finderPathsSrcBin("/Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoVersão02/src");
        System.out.println(pathBIN);
        System.out.println(pathSRC);
        //executeComand("");
    }

    public static void finderPathsSrcBin(String path){

        File diretorioProjeto = new File(path);

        // Verifica se o diretório do projeto existe
        if (!diretorioProjeto.exists()) {
            System.err.println("O diretório do projeto não existe");
            return;
        }

        // Verifica se o diretório do projeto é um diretório
        if (!diretorioProjeto.isDirectory()) {
            System.err.println("O caminho especificado não é um diretório");
            return;
        }

        // Itera pelos diretórios e subdiretórios do projeto
        encontrarBinSrc(diretorioProjeto);
    }

    public static void encontrarBinSrc(File diretorio) {
        // Verifica se o diretório atual é o diretório "bin"
        if (diretorio.isDirectory() && (diretorio.getName().equals("bin") || diretorio.getName().equals("target")
        || diretorio.getName().equals("out"))) {
            pathBIN = diretorio.getAbsolutePath();
            //System.out.println("Caminho para a pasta bin: " + diretorio.getAbsolutePath());
        }

        // Verifica se o diretório atual é o diretório "src"
        if (diretorio.isDirectory() && diretorio.getName().equals("src")) {
            pathSRC = diretorio.getAbsolutePath();
            //System.out.println("Caminho para a pasta src: " + diretorio.getAbsolutePath());
        }

        // Itera pelos arquivos e subdiretórios do diretório atual
        if (diretorio.isDirectory()) {
            File[] conteudoDiretorio = diretorio.listFiles();
            if (conteudoDiretorio != null) {
                for (File arquivoOuDiretorio : conteudoDiretorio) {
                    encontrarBinSrc(arquivoOuDiretorio);
                }
            }
        }
    }

    public static String getPathBIN() {
        return pathBIN;
    }

    public static String getPathSRC() {
        return pathSRC;
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
}
