package org.example;

import org.example.randoop.randoopHandler;
import org.example.randoop.utilsRandoop.*;

import java.util.Scanner;

public class mainFluxo01 {
    private  String pathSRCVersao01;
    private  String pathSRCVersao02;
    private String pathBINVersao01;
    private  String pathBINVersao02;

    public mainFluxo01(){
        this.pathBINVersao01 = "";
        this.pathBINVersao02 = "";
        this.pathSRCVersao01 = "";
        this.pathSRCVersao02 = "";
    }

    public String getPathSRCVersao01() {
        return pathSRCVersao01;
    }


    public String getPathSRCVersao02() {
        return pathSRCVersao02;
    }


    public String getPathBINVersao01() {
        return pathBINVersao01;
    }


    public String getPathBINVersao02() {
        return pathBINVersao02;
    }

    public static void main(String[] args) throws Exception {

        //Projeto versão01
        ///Users/gabriellacerda/GitHubGabrielLacerda/Calculator
        ///Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoTest
        //Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src
        ///Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoVersionOrigin/src
        //Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCallersInClassRefact/src/CodeOrigin
        Scanner pathProject01 = new Scanner(System.in);
        System.out.print("Informe o caminho do src do projeto clonado:");
        String pathhProject01 = pathProject01.nextLine();

        //Projeto versão02
        ///Users/gabriellacerda/GitHubGabrielLacerda/CalculV2
        ///Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoTestV2
        ///Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoVersão02/src
        ///Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCallersInClassRefact/src/CodeDestiny
        Scanner pathProject02 = new Scanner(System.in);
        System.out.print("Informe o caminho do src do projeto clonado:");
        String pathhProject02 = pathProject02.nextLine();

        mainFluxo01 main = new mainFluxo01();

        finderPathsBinSrc.finderPathsSrcBin(pathhProject01);
        main.pathSRCVersao01 = finderPathsBinSrc.getPathSRC();

        main.pathBINVersao01 = finderPathsBinSrc.getPathBIN();

        finderPathsBinSrc.finderPathsSrcBin(pathhProject02);
        main.pathSRCVersao02 = finderPathsBinSrc.getPathSRC();
        main.pathBINVersao02 = finderPathsBinSrc.getPathBIN();

        String pathMethodVersion01 = FileSearcher.findFilePath("methodsVersion01.txt");
        String pathMethodVersion02 = FileSearcher.findFilePath("methodsVersion02.txt");
        String pathOmitedmethods = FileSearcher.findFilePath("omited_methods.txt");
        String pathComunClasses = FileSearcher.findFilePath("comuns_classes.txt");
        String pathScriptRandoopBat = FileSearcher.findFilePath("scriptRandoop.bat");

        //1.0Pega as classes
        ClassPathFinder.finderClassesPath(main.getPathBINVersao01(),pathComunClasses);

        //2.0 - Pegar os caminhos dos métodos da versão 01
        methodsFinder.finderMethodsPath(main.getPathSRCVersao01(),pathMethodVersion01);

        //3.0 - Pegar os caminhos dos métodos da versão 02
        methodsFinder.finderMethodsPath(main.getPathSRCVersao02(),pathMethodVersion02);

        //4.0 - Filtra os métodos comuns
        FiltroDeArquivos.filtroNotComunMethods(pathMethodVersion01,
                pathMethodVersion02);

        //Ignora os metodos que não comuns
        randoopHandler.createComanderRandoop(main.getPathBINVersao01(),
                pathComunClasses,pathOmitedmethods,main.getPathSRCVersao02());

    }

}
