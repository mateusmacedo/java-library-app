package com.mmda.core.repository;

public interface PersistenceRepository<T> {
    T save(T entity) throws PersistenceException;
}
