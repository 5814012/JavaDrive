package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConsolaJavaDriveTest {

    @Test
    void consolaIsCreated() {
        ConsolaJavaDrive consola = new ConsolaJavaDrive();
        assertNotNull(consola);
    }
}
