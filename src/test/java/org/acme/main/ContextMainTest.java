package org.acme.main;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

@QuarkusTest
public class ContextMainTest {


    @Inject
    private Main main;

    @Test
    public void testContextMain() throws Exception {
        assertDoesNotThrow(() -> main.run("LSBmcm9tOgogICAgdXJpOiAiZGlyZWN0Om15LXJvdXRlIgogICAgc3RlcHM6CiAgICAgIC0gbG9nOgogICAgICAgICAgbWVzc2FnZTogIkxvZ2dpbmcgZnJvbSBteS1yb3V0ZS4uLiI="));
    }
}
