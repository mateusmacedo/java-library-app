package com.mmda.core.factory;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericFactoryTest {

    static class TestClass {
        private final int number;
        private final String text;

        public TestClass(int number, String text) {
            this.number = number;
            this.text = text;
        }

        public int getNumber() {
            return number;
        }

        public String getText() {
            return text;
        }
    }

    private GenericFactory<TestClass> factory;

    private FactoryException exception;

    @BeforeEach
    public void setUp() {
        factory = new GenericFactory<>(TestClass.class);
    }

    @AfterEach
    public void tearDown() {
        factory = null;
        exception = null;
    }

    @Test
    void create_shouldReturnInstance_whenParametersAreCorrect() {
        // Act
        TestClass instance = factory.create(123, "Hello World");

        // Assert
        assertNotNull(instance);
        assertEquals(123, instance.getNumber());
        assertEquals("Hello World", instance.getText());
    }

    @Test
    void create_shouldReturnFactoryExceptions_whenIncorrectParameters() {
        // Arrange and Act
        exception = assertThrows(FactoryException.class, () -> factory.create("Hello World", 123));

        // Assert
        assertNotNull(exception);
        assertEquals("Error creating instance of com.mmda.core.factory.GenericFactoryTest$TestClass", exception.getMessage());
    }

    @Test
    void create_shouldReturnFactoryException_whenNoMatchingConstructor() {
        // Arrange and Act
        exception = assertThrows(FactoryException.class, () -> factory.create(123));

        // Assert
        assertNotNull(exception);
        assertEquals("No matching constructor found for parameters", exception.getMessage());
    }
}