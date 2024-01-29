package it.pagopa.pn.authorization;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProvaTest {

    @Test
    public void classMethod() {
        Prova prova = new Prova();
        assertEquals("I'm alive", prova.testMethod());
    }
}
