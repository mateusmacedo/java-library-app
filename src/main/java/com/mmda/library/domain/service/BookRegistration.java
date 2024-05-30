package com.mmda.library.domain.service;

import com.mmda.core.validation.ValidationException;
import com.mmda.core.validation.Validator;
import com.mmda.library.domain.dto.BookRegistrationDto;

public class BookRegistration {
    private final Validator<BookRegistrationDto> validator;

    public BookRegistration(Validator<BookRegistrationDto> validator) {
        this.validator = validator;
    }

    public Object registerBook(BookRegistrationDto dto) throws ValidationException {
        Object result = new Object();
        validator.validate(dto);

        return result;
    }
}