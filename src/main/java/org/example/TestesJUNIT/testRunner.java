package org.example.TestesJUNIT;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class testRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestPurityToolRenames.class);
        Result result2 = JUnitCore.runClasses(TestRenameOneRenameTwoCallers.class);
        Result result3 = JUnitCore.runClasses(TestRenameOneRenameForMissingCallers.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        if (result.wasSuccessful() && result2.wasSuccessful() && result3.wasSuccessful()) {
            System.out.println("Todos os testes passaram com sucesso.");
        } else {
            System.out.println("Alguns testes falharam.");
        }
    }
}