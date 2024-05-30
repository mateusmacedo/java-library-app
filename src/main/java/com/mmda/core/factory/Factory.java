package com.mmda.core.factory;

public interface Factory<T> {
    T create(Object... params) throws FactoryException;
}
