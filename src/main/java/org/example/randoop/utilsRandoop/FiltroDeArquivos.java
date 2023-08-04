package org.example.randoop.utilsRandoop;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class FiltroDeArquivos {
    public static void main(String[] args) {
        String path01 = "src/main/java/org/example/txtFiles/methodsVersion01.txt";
        String path02 = "src/main/java/org/example/txtFiles/methodsVersion02.txt";
        filtroNotComunMethods(path01,path02);
    }

    public static void filtroNotComunMethods(String path01,String path02) {

        // Abre os arquivos de entrada e saída
        String diretorio = System.getProperty("user.dir");
        String os = System.getProperty("os.name").toLowerCase();

        File arquivoEntrada1 = null;
        File arquivoEntrada2 = null;
        File arquivoSaida = null;

        BufferedReader leitor1 = null;
        BufferedReader leitor2 = null;
        BufferedWriter escritor = null;

        if (os.contains("win")) { // Verifica se é Windows
            arquivoEntrada1 = new File(path02);
             arquivoEntrada2 = new File(path01);
             arquivoSaida = new File(diretorio +"\\"+"src\\main\\java\\org\\example\\txtFiles\\omited_methods.txt");
        } else if (os.contains("mac")) { // Verifica se é macOS
            arquivoEntrada1 = new File(path02);
            System.out.println(arquivoEntrada1);
            arquivoEntrada2 = new File(path01);
            System.out.println(arquivoEntrada2);
             arquivoSaida = new File(diretorio +"/"+"src/main/java/org/example/txtFiles/omited_methods.txt");
            System.out.println(arquivoSaida);
            System.out.println("oii");
        } else { // Assume que é Linux ou outro sistema UNIX-like
             arquivoEntrada1 = new File(path02);
             arquivoEntrada2 = new File(path01);
             arquivoSaida = new File(diretorio +"/"+"src/main/java/org/example/txtFiles/omited_methods.txt");
        }


        try {
            // Cria os leitores e escritores para os arquivos de entrada e saída
            leitor1 = new BufferedReader(new FileReader(arquivoEntrada1));
            leitor2 = new BufferedReader(new FileReader(arquivoEntrada2));
            escritor = new BufferedWriter(new FileWriter(arquivoSaida));

            // Lê as linhas do segundo arquivo e armazena em um HashSet para verificação posterior
            HashSet<String> linhasArquivo2 = new HashSet<String>();
            String linhaArquivo2;
            while ((linhaArquivo2 = leitor2.readLine()) != null) {
                linhasArquivo2.add(linhaArquivo2);
            }

            // Lê as linhas do primeiro arquivo, verifica se a linha está presente no segundo arquivo
            // e escreve no arquivo de saída se a linha não estiver presente
            String linhaArquivo1;
            while ((linhaArquivo1 = leitor1.readLine()) != null) {
                if (!linhasArquivo2.contains(linhaArquivo1)) {
                    escritor.write(linhaArquivo1);
                    escritor.newLine();
                }
            }

            //System.out.println("Arquivo de saída gerado com sucesso.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Fecha os leitores e escritores
            try {
                if (leitor1 != null) {
                    leitor1.close();
                }
                if (leitor2 != null) {
                    leitor2.close();
                }
                if (escritor != null) {
                    escritor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

