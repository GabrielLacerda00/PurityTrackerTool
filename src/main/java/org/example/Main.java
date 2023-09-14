package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("============ PurityTrackerTool ==============");
        System.out.println("Escolha o fluxograma que deseja executar:");
        System.out.println("1. Fluxograma01");
        System.out.println("2. Fluxograma02");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                executeFluxograma01();
                break;
            case 2:
                executeFluxograma02();
                break;
            default:
                System.out.println("Opção inválida");
        }

        scanner.close();
    }

    public static void executeFluxograma01() throws IOException {
        mainFluxo01.Flux01Handler();
    }

    public static void executeFluxograma02() throws Exception {
        mainFlux02.fluxo02Handler();
    }
}
