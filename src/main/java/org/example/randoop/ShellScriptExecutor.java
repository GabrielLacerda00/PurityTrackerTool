package org.example.randoop;

import java.io.File;
import java.io.IOException;

public class ShellScriptExecutor {
    public static void main(String[] args) {
        try {
            // Caminho para o arquivo shell
            String shellScriptPath = "/Users/gabriellacerda/GitHubGabrielLacerda/script.sh";

            // Criando o processo para executar o shell
            ProcessBuilder processBuilder = new ProcessBuilder("bash", shellScriptPath);

            // Definindo o diretório de trabalho (opcional, mas pode ser útil)
            //processBuilder.directory(new File("/Users/gabriellacerda/GitHubGabrielLacerda/script.sh"));

            // Redirecionando o output e error stream para o console (opcional)
            processBuilder.inheritIO();

            // Iniciando o processo
            Process process = processBuilder.start();

            // Esperando o processo terminar e obtendo o código de saída
            int exitCode = process.waitFor();

            // Verificando o código de saída para determinar o sucesso ou falha
            if (exitCode == 0) {
                System.out.println("Script executado com sucesso!");
            } else {
                System.err.println("Ocorreu um erro ao executar o script. Código de saída: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
