package com.mmda.library.domain.service;

import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mmda.core.repository.PersistenceRepository;
import com.mmda.core.validation.ValidationException;
import com.mmda.core.validation.Validator;
import com.mmda.library.domain.dto.BookRegistrationDto;
import com.mmda.library.domain.model.Author;
import com.mmda.library.domain.model.Book;
import com.mmda.library.domain.valueobject.Category;
import com.mmda.library.domain.valueobject.Isbn;

@ExtendWith(MockitoExtension.class)
public class BookRegistrationTest {

    @Mock
    private Validator<BookRegistrationDto> validator;

    @Mock
    private PersistenceRepository<Book> repository;

    @InjectMocks
    private BookRegistration bookRegistration;

    @BeforeEach
    public void setUp() {
        bookRegistration = new BookRegistration(validator, repository);
    }

    @AfterEach
    public void tearDown() {
        validator = null;
        repository = null;
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
                title, authors, isbn, categories, publicationYear);

        doThrow(new ValidationException("Invalid DTO")).when(validator).validate(dto);

        ValidationException exception = assertThrows(ValidationException.class,
                () -> bookRegistration.registerBook(dto));

        assertEquals("Invalid DTO", exception.getMessage());
        verify(validator).validate(dto);
        verify(repository, never()).save(any(Book.class));
    }

    @Test
    public void registerBook_shouldReturnBook_whenDtoIsValid() {
        String title = "Title";
        Author author = new Author("Name", "Surname");
        HashSet<Author> authors = new HashSet<>();
        authors.add(author);
        Isbn isbn = new Isbn("1234567890123");
        Category category = new Category("Category");
        HashSet<Category> categories = new HashSet<>();
        categories.add(category);
        Integer publicationYear = 2021;

        Book expectedBook = new Book(
                title, authors, isbn, categories, publicationYear);
        BookRegistrationDto dto = new BookRegistrationDto(
                title, authors, isbn, categories, publicationYear);

        doNothing().when(validator).validate(any(BookRegistrationDto.class));
        when(repository.save(any(Book.class))).thenReturn(expectedBook);

        Book resultBook = bookRegistration.registerBook(dto);

        assertEquals(expectedBook, resultBook);
        verify(validator).validate(dto);
        verify(repository).save(any(Book.class));
    }
}