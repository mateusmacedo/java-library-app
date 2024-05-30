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

import com.mmda.core.factory.Factory;
import com.mmda.core.factory.FactoryException;
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

    @Mock
    private Factory<Book> factory;

    @InjectMocks
    private BookRegistration bookRegistration;

    @BeforeEach
    public void setUp() {
        bookRegistration = new BookRegistration(validator, repository, factory);
    }

    @AfterEach
    public void tearDown() {
        validator = null;
        repository = null;
    }

    @Test
    public void registerBook_shouldReturnValidationException_whenDtoIsInvalid() {
        // Arrange
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

        // Act
        ValidationException exception = assertThrows(ValidationException.class,
                () -> bookRegistration.registerBook(dto));

        // Assert
        assertEquals("Invalid DTO", exception.getMessage());
        verify(validator).validate(dto);
        verify(factory, never()).create(any(String.class), any(HashSet.class), any(Isbn.class),
                any(HashSet.class), any(Integer.class));
        verify(repository, never()).save(any(Book.class));
    }

    @Test
    public void registerBook_shouldReturnFactoryException_whenFactoryFails() {
        // Arrange
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

        doNothing().when(validator).validate(any(BookRegistrationDto.class));
        when(factory.create(any(String.class), any(HashSet.class), any(Isbn.class),
                any(HashSet.class), any(Integer.class))).thenThrow(new FactoryException("Factory error"));

        // Act
        RuntimeException exception = assertThrows(FactoryException.class,
                () -> bookRegistration.registerBook(dto));

        // Assert
        assertEquals("Factory error", exception.getMessage());
        verify(validator).validate(dto);
        verify(factory).create(title, authors, isbn, categories, publicationYear);
        verify(repository, never()).save(any(Book.class));
    }

    @Test
    public void registerBook_shouldReturnPersistenceException_whenRepositoryFails() {
        // Arrange
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
        Book expectedBook = new Book(
                title, authors, isbn, categories, publicationYear);

        doNothing().when(validator).validate(any(BookRegistrationDto.class));
        when(factory.create(any(String.class), any(HashSet.class), any(Isbn.class),
                any(HashSet.class), any(Integer.class))).thenReturn(expectedBook);
        when(repository.save(any(Book.class))).thenThrow(new RuntimeException("Repository error"));

        // Act
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> bookRegistration.registerBook(dto));

        // Assert
        assertEquals("Repository error", exception.getMessage());
        verify(validator).validate(dto);
        verify(factory).create(title, authors, isbn, categories, publicationYear);
        verify(repository).save(expectedBook);
    }

    @Test
    public void registerBook_shouldReturnBook_whenDtoIsValid() {
        // Arrange
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
        Book expectedBook = new Book(
                title, authors, isbn, categories, publicationYear);

        doNothing().when(validator).validate(any(BookRegistrationDto.class));
        when(factory.create(any(String.class), any(HashSet.class), any(Isbn.class),
                any(HashSet.class), any(Integer.class))).thenReturn(expectedBook);
        when(repository.save(any(Book.class))).thenReturn(expectedBook);

        // Act
        Book resultBook = bookRegistration.registerBook(dto);

        // Assert
        assertEquals(expectedBook, resultBook);
        verify(validator).validate(dto);
        verify(factory).create(title, authors, isbn, categories, publicationYear);
        verify(repository).save(expectedBook);
    }
}