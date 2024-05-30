package com.mmda.core.repository;

import java.util.List;
import java.util.stream.Collectors;

public class GenericSearchRepository<T> implements SearchRepository<T> {

    private final List<T> entities;

    public GenericSearchRepository(List<T> entities) {
        this.entities = entities;
    }

    @Override
    public T search(SearchCriteria<T> criteria) throws SearchException {
        try {
            return entities.stream()
                    .filter(criteria::matches)
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            throw new SearchException("Error occurred during search", e);
        }
    }

    @Override
    public List<T> searchAll(SearchCriteria<T> criteria) throws SearchException {
        try {
            return entities.stream()
                    .filter(criteria::matches)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new SearchException("Error occurred during search", e);
        }
    }
}
