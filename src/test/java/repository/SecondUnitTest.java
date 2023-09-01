package repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecondUnitTest {

    @Test
    public void whenSomething_thenSomething() {
        assertTrue(true);
    }

    @Test
    public void whensomethingElse_thenSomethingElse() {
        assertTrue(true);
    }

    public static void main(String[] args) {
        Class<SecondUnitTest> clazz = SecondUnitTest.class;
        Package pkg = clazz.getPackage();
        String packageName = pkg.getName();

        System.out.println("Nome do pacote: " + packageName);
    }
}