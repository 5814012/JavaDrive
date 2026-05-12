package app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConsolaJavaDriveTest {
    
    @Test
    void calcSquare() {
        ConsolaJavaDrive c = new ConsolaJavaDrive();
        assertEquals(4, c.square(2));
    }
}