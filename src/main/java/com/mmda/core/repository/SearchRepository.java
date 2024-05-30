package com.mmda.core.repository;

import java.util.List;

public interface SearchRepository<T> {
    T search(SearchCriteria<T> criteria) throws SearchException;
    List<T> searchAll(SearchCriteria<T> criteria) throws SearchException;
}
