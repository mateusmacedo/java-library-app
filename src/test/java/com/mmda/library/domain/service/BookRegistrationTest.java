package com.mmda.library.domain.service;

import java.util.HashSet;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.mmda.core.repository.PersistenceRepository;
import com.mmda.core.validation.ValidationException;
import com.mmda.core.validation.Validator;
import com.mmda.library.domain.dto.BookRegistrationDto;
import com.mmda.library.domain.model.Author;
import com.mmda.library.domain.model.Book;
import com.mmda.library.domain.valueobject.Category;
import com.mmda.library.domain.valueobject.Isbn;

public class BookRegistrationTest {

    @Mock
    private Validator<BookRegistrationDto> validator;

    @Mock
    private PersistenceRepository<Book> repository;

    private BookRegistration bookRegistration;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookRegistration = new BookRegistration(validator, repository);
    }

    @After
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
                title,
                authors,
                isbn,
                categories,
                publicationYear);

        doThrow(new ValidationException("Invalid DTO")).when(validator).validate(dto);

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            bookRegistration.registerBook(dto);
        });

        assert exception.getMessage().equals("Invalid DTO");
        verify(validator, times(1)).validate(dto);
        verify(repository, times(0)).save(any(Book.class));
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
            title,
            authors,
            isbn,
            categories,
            publicationYear
        );
        BookRegistrationDto dto = new BookRegistrationDto(
            title,
            authors,
            isbn,
            categories,
            publicationYear);

        doNothing().when(validator).validate(dto);
        when(repository.save(any(Book.class))).thenReturn(expectedBook);

        Book resultBook = bookRegistration.registerBook(dto);

        assertEquals(expectedBook.getTitle(), resultBook.getTitle());
        assertEquals(expectedBook.getAuthors(), resultBook.getAuthors());
        assertEquals(expectedBook.getIsbn(), resultBook.getIsbn());
        assertEquals(expectedBook.getCategories(), resultBook.getCategories());
        assertEquals(expectedBook.getPublicationYear(), resultBook.getPublicationYear());

        verify(validator, times(1)).validate(dto);
        verify(repository, times(1)).save(any(Book.class));
    }
}
