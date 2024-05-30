package com.mmda.library.domain.service;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mmda.core.repository.SearchException;
import com.mmda.core.repository.SearchRepository;
import com.mmda.library.domain.criteria.BookSearchCriteria;
import com.mmda.library.domain.model.Book;

@ExtendWith(MockitoExtension.class)
class BookSearchTest {
    @Mock
    private SearchRepository<Book> repository;

    @Mock
    private Book book;

    @Mock
    private List<Book> books;

    @InjectMocks
    private BookSearch bookSearch;

    @BeforeEach
    public void setUp() {
        bookSearch = new BookSearch(repository);
    }

    @AfterEach
    public void tearDown() {
        repository = null;
    }

    @Test
    void search_shouldReturnBook_whenCriteriaIsValid() throws SearchException {
        // Arrange
        String title = "Title";
        BookSearchCriteria criteria = new BookSearchCriteria(title, null, null, null);
        when(repository.search(criteria)).thenReturn(book);

        // Act
        Book result = bookSearch.search(criteria);

        // Assert
        assertEquals(book, result);
        verify(repository).search(criteria);
    }

    @Test
    void search_shouldReturnSearchException_whenRepositoryThrowsException() throws SearchException {
        // Arrange
        String title = "Title";
        BookSearchCriteria criteria = new BookSearchCriteria(title, null, null, null);
        doThrow(new SearchException("Error occurred during search")).when(repository).search(criteria);

        SearchException exception = assertThrows(SearchException.class, () -> {
            // Act
            bookSearch.search(criteria);
        });

        // Assert
        assertEquals("Error occurred during search", exception.getMessage());
        verify(repository).search(criteria);
    }

    @Test
    void searchAll_shouldReturnBooks_whenCriteriaIsValid() throws SearchException {
        // Arrange
        String title = "Title";
        BookSearchCriteria criteria = new BookSearchCriteria(title, null, null, null);
        when(repository.searchAll(criteria)).thenReturn(books);

        // Act
        List<Book> result = bookSearch.searchAll(criteria);

        // Assert
        assertEquals(books, result);
        verify(repository).searchAll(criteria);
    }

    @Test
    void searchAll_shouldReturnSearchException_whenRepositoryThrowsException() throws SearchException {
        // Arrange
        String title = "Title";
        BookSearchCriteria criteria = new BookSearchCriteria(title, null, null, null);
        doThrow(new SearchException("Error occurred during search")).when(repository).searchAll(criteria);

        SearchException exception = assertThrows(SearchException.class, () -> {
            // Act
            bookSearch.searchAll(criteria);
        });

        // Assert
        assertEquals("Error occurred during search", exception.getMessage());
        verify(repository).searchAll(criteria);
    }
}
