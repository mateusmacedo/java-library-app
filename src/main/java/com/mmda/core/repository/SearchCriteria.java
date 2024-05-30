package com.mmda.core.repository;

public interface SearchCriteria<T> {
    boolean matches(T entity);
}
