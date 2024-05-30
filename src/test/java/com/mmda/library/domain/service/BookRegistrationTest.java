package com.mmda.library.domain.service;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.doThrow;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mmda.core.validation.ValidationException;
import com.mmda.core.validation.Validator;
import com.mmda.library.domain.dto.BookRegistrationDto;
import com.mmda.library.domain.model.Author;
import com.mmda.library.domain.valueobject.Category;
import com.mmda.library.domain.valueobject.Isbn;

public class BookRegistrationTest {

    @Mock
    private Validator<BookRegistrationDto> validator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void registerBook_shouldReturnValidationException_whenDtoIsInvalid() {
        String title = "Title";
        Author author = new Author("Name", "Surname");

        HashSet<Author> authors = new HashSet<>();
        authors.add(author);

        Isbn isbn = new Isbn("1234567890123");
        Category category = new Category("Category");

        HashSet<Category> categories = new HashSet<>();
        categories.add(category);

        Integer publicationYear = 2021;

        BookRegistrationDto dto = new BookRegistrationDto(
                title,
                authors,
                isbn,
                categories,
                publicationYear);

        BookRegistration bookRegistration = new BookRegistration(validator);

        doThrow(new ValidationException("Invalid DTO")).when(validator).validate(dto);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            bookRegistration.registerBook(dto);
        });

        assert exception.getMessage().equals("Invalid DTO");
    }
}
