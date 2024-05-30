package com.mmda.library.domain.service;

import java.util.List;

import com.mmda.core.repository.SearchException;
import com.mmda.core.repository.SearchRepository;
import com.mmda.library.domain.criteria.BookSearchCriteria;
import com.mmda.library.domain.model.Book;

public class BookSearch {
    private final SearchRepository<Book> repository;

    public BookSearch(SearchRepository<Book> repository) {
        this.repository = repository;
    }

    public Book search(BookSearchCriteria criteria) throws SearchException {
        return repository.search(criteria);
    }

    public List<Book> searchAll(BookSearchCriteria criteria) throws SearchException {
        return repository.searchAll(criteria);
    }
}
