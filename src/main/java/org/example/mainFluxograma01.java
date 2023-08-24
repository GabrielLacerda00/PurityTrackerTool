package org.example;


import org.example.randoop.randoopHandler;
import org.example.randoop.utilsRandoop.*;

import java.util.Scanner;

public class mainFluxograma01 {
    private  String pathSRCVersao01;
    private  String pathSRCVersao02;
    private String pathBINVersao01;
    private  String pathBINVersao02;

    public mainFluxograma01(){
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
        //Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinho/src
        ///Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoVersionOrigin/src
        //Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCallersInClassRefact/src/CodeOrigin
        Scanner pathProject01 = new Scanner(System.in);
        System.out.print("Informe o caminho do src do projeto clonado:");
        String pathhProject01 = pathProject01.nextLine();

        //Projeto versão02
        ///Users/gabriellacerda/GitHubGabrielLacerda/ProjetoMercadinhoVersão02/src
        ///Users/gabriellacerda/GitHubGabrielLacerda/SuitTestsRenameMethod/OneRenameForTwoCallersInClassRefact/src/CodeDestiny
        Scanner pathProject02 = new Scanner(System.in);
        System.out.print("Informe o caminho do src do projeto clonado:");
        String pathhProject02 = pathProject02.nextLine();

        mainFluxograma01 main = new mainFluxograma01();

        finderPathsBinSrc.finderPathsSrcBin(pathhProject01);
        main.pathSRCVersao01 = finderPathsBinSrc.getPathSRC();

        main.pathBINVersao01 = finderPathsBinSrc.getPathBIN();

        finderPathsBinSrc.finderPathsSrcBin(pathhProject02);
        main.pathSRCVersao02 = finderPathsBinSrc.getPathSRC();
        main.pathBINVersao02 = finderPathsBinSrc.getPathBIN();


        //1.0 - Pegar os caminhos dos métodos da versão 01
        PathUtilVersion01 pathUtilVersion01 = new PathUtilVersion01();
        pathUtilVersion01.getPathFromRoot("methodsVersion01.txt");
        methodsFinder.finderMethodsPath(main.getPathSRCVersao01(),"");

        //2.0 - Pegar os caminhos dos métodos da versão 02
        PathUtilVersion02 pathUtilVersion02 = new PathUtilVersion02();
        pathUtilVersion02.getPathFromRoot("methodsVersion02.txt");

        methodsFinderV2.finderMethodsPath(main.getPathSRCVersao02());

        //3.0 - Filtra os métodos comuns
        /*filterPaths.notComunsMethodfilter("ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsFiltradosV1.txt",
                "ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\pathsFiltradosV2.txt");*/

        FiltroDeArquivos.filtroNotComunMethods(pathUtilVersion01.getPath(),
                pathUtilVersion02.getPath());

        //Ignora os metodos que não comuns
        PathUtilOmitedMethods pathUtilOmitedMethods = new PathUtilOmitedMethods();
        pathUtilOmitedMethods.getPathFromRoot("omited_methods.txt");
        PathUtilClasses pathUtilClasses = new PathUtilClasses();
        pathUtilClasses.getPathFromRoot("comuns_classes.txt");

        randoopHandler.createComanderRandoop(main.getPathBINVersao01(),pathUtilOmitedMethods.getPath(),
                pathUtilClasses.getPath(),main.getPathSRCVersao02());

        /*randoopScript.createComanderRandoop(pathBINVersao02,"ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\comuns_classes.txt",
                "ProjetoPIBIC\\src\\main\\java\\com\\JsonFiles\\omited_methods.txt",pathSRCVersao02);*/

        PathUtilScriptRandoopBat pathUtilScriptRandoopBat = new PathUtilScriptRandoopBat();
        pathUtilScriptRandoopBat.getPathFromRoot("scriptRandoop.bat");

        randoopHandler.executeComand(pathUtilScriptRandoopBat.getPath());
        randoopHandler.clearBat(pathUtilScriptRandoopBat.getPath());

    }

    //Modificar os txts para testar o randoop
}
