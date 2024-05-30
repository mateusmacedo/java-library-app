package com.mmda.library.domain.dto;

import java.util.Set;

import com.mmda.library.domain.model.Author;
import com.mmda.library.domain.valueobject.Category;
import com.mmda.library.domain.valueobject.Isbn;

public class BookRegistrationDto {
    private final String title;
    private final Set<Author> authors;
    private final Isbn isbn;
    private final Set<Category> categories;
    private final int publicationYear;

    public BookRegistrationDto(String title, Set<Author> authors, Isbn isbn, Set<Category> categories,
            int publicationYear) {
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.categories = categories;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public int getPublicationYear() {
        return publicationYear;
    }
}
