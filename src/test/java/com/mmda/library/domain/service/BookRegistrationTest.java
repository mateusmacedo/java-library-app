package com.mmda.library.domain.service;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doThrow;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmda.core.validation.ValidationException;
import com.mmda.core.validation.Validator;

public class BookRegistrationTest {

    @Mock
    private Validator<Object> validator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerBook_shouldReturnValidationException_whenDtoIsInvalid() {
        BookRegistration bookRegistration = new BookRegistration(validator);
        Object dto = new Object();

        doThrow(new ValidationException("Invalid DTO")).when(validator).validate(dto);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            bookRegistration.registerBook(dto);
        });

        assert exception.getMessage().equals("Invalid DTO");
    }
}
