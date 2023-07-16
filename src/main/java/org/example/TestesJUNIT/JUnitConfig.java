package org.example.TestesJUNIT;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JUnitConfig {

    @BeforeAll
    public void beforeAll() {
        System.setProperty("junit.jupiter.execution.parallel.enabled", "false");
    }
}