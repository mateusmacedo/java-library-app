package com.mmda.library.domain.service;

import com.mmda.core.factory.Factory;
import com.mmda.core.repository.PersistenceRepository;
import com.mmda.core.validation.ValidationException;
import com.mmda.core.validation.Validator;
import com.mmda.library.domain.dto.BookRegistrationDto;
import com.mmda.library.domain.model.Book;

public class BookRegistration {
    private final Validator<BookRegistrationDto> validator;
    private final PersistenceRepository<Book> repository;
    private final Factory<Book> bookFactory;

    public BookRegistration(
        Validator<BookRegistrationDto> validator,
        PersistenceRepository<Book> repository,
        Factory<Book> bookFactory
        ) {
        this.validator = validator;
        this.repository = repository;
        this.bookFactory = bookFactory;
    }

    public Book registerBook(BookRegistrationDto dto) throws ValidationException {
        validator.validate(dto);
        Book book = bookFactory.create(
            dto.getTitle(),
            dto.getAuthors(),
            dto.getIsbn(),
            dto.getCategories(),
            dto.getPublicationYear()
        );
        return repository.save(book);
    }
}