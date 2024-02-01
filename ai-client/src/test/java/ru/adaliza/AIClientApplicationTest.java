package ru.adaliza;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class AIClientApplicationTest {

    @Test
    void shouldStart_application(ApplicationContext context) {
        assertNotNull(context);
    }
}
