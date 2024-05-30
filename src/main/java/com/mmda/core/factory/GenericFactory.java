package com.mmda.core.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GenericFactory<T> implements Factory<T> {

    private final Class<T> clazz;

    public GenericFactory(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T create(Object... params) throws FactoryException {
        try {
            for (Constructor<?> constructor : clazz.getConstructors()) {
                if (constructor.getParameterCount() == params.length) {
                    return (T) constructor.newInstance(params);
                }
            }
            throw new FactoryException("No matching constructor found for parameters");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FactoryException("Error creating instance of " + clazz.getName(), e);
        }
    }
}
