package com.mmda.library.domain.valueobject;

public class Isbn {
    private final String value;

    public Isbn(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
