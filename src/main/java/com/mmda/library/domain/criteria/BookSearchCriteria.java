package com.mmda.library.domain.criteria;

import java.util.Set;

import com.mmda.core.repository.SearchCriteria;
import com.mmda.library.domain.model.Book;
import com.mmda.library.domain.valueobject.Isbn;

public class BookSearchCriteria implements SearchCriteria<Book> {

    private final String title;
    private final Set<String> authors;
    private final Isbn isbn;
    private final Set<String> categories;

    public BookSearchCriteria(String title, Set<String> authors, Isbn isbn, Set<String> categories) {
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.categories = categories;
    }

    @Override
    public boolean matches(Book book) {
        boolean matches = true;
        if (title != null) {
            matches = matches && book.getTitle().equalsIgnoreCase(title);
        }
        if (authors != null && !authors.isEmpty()) {
            matches = matches && book.getAuthors().stream().anyMatch(author -> authors.contains(author.getName()));
        }
        if (isbn != null) {
            matches = matches && book.getIsbn().equals(isbn);
        }
        if (categories != null && !categories.isEmpty()) {
            matches = matches && book.getCategories().stream().anyMatch(category -> categories.contains(category.getValue()));
        }
        return matches;
    }
}
