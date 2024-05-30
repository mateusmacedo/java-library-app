package com.mmda.library.domain.service;

import com.mmda.core.validation.ValidationException;
import com.mmda.core.validation.Validator;

public class BookRegistration {
    private final Validator<Object> validator;

    public BookRegistration(Validator<Object> validator) {
        this.validator = validator;
    }

    public Object registerBook(Object dto) throws ValidationException {
        Object result = new Object();
        validator.validate(dto);

        return result;
    }
}