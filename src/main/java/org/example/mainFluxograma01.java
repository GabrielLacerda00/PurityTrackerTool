package org.example;

import org.example.randoop.randoopHandler;
import org.example.randoop.utilsRandoop.filterPaths;
import org.example.randoop.utilsRandoop.methodsFinder;

import java.io.IOException;
import java.util.Scanner;

public class mainFluxograma01 {
    public static void main(String[] args) throws IOException {
        //Projeto URL
        //"C:\\Users\\gabri\\ProjetoMercadinho\\src"
        Scanner pathSRCProject = new Scanner(System.in);
        System.out.print("Informe o caminho do folder src:\n");
        String pathSrcProject = pathSRCProject.nextLine();

        //Projeto URL
        //"C:\\Users\\gabri\\ProjetoMercadinho\\bin"
        Scanner pathBinProject = new Scanner(System.in);
        System.out.print("Informe o caminho do folder bin:\n");
        String pathBinnProject = pathBinProject.nextLine();

        //3.0 - Pegar os caminhos dos métodos
        //methodsFinder.finderMethodsPath(pathSrcProject,"");

        //4.0 - Filtra os métodos
        filterPaths.methodfilterPaths("C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\test\\mercadinho.txt",
                "C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsInJSONFile.txt");

        randoopHandler.createComanderRandoop(pathBinnProject,"C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\test\\arquivo_filtrado.txt",
                "C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\test\\arquivo_filtrado02.txt","");

        randoopHandler.executeComand("C:\\Users\\gabri\\GitHub-Gabriel_lacerda\\PesquisaPibicUFCG\\scriptRandoop.bat");

    }
}