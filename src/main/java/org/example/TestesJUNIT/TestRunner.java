package org.example.TestesJUNIT;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;


public class TestRunner {

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));

        // Execute o seu teste
        Result result = junit.run(); // Troque 'RegressionTest.class' pelo nome da sua classe

        // Imprime as falhas, caso haja
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        // Imprime se foi bem sucedido ou não
        System.out.println(result.wasSuccessful());
    }
}