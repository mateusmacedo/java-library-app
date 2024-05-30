package com.mmda.core.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenericSearchRepositoryTest {

    private GenericSearchRepository<String> repository;

    @BeforeEach
    public void setUp() {
        List<String> entities = new ArrayList<>();
        entities.add("Apple");
        entities.add("Banana");
        entities.add("Orange");
        repository = new GenericSearchRepository<>(entities);
    }

    @AfterEach
    public void tearDown() {
        repository = null;
    }

    @Test
    public void search_shouldReturnMatchingEntity_whenCriteriaMatches() {
        // Arrange
        SearchCriteria<String> criteria = entity -> entity.startsWith("B");
        // Act

        // Act
        String result;
        try {
            result = repository.search(criteria);
        } catch (SearchException ex) {
            result = null;
        }

        // Assert
        assertNotNull(result);
        assertEquals("Banana", result);
    }

    @Test
    public void search_shouldReturnNull_whenNoMatchingEntity() {
        // Arrange
        SearchCriteria<String> criteria = entity -> entity.startsWith("C");

        // Act
        String result;
        try {
            result = repository.search(criteria);
        } catch (SearchException e) {
            result = null;
        }

        // Assert
        assertNull(result);
    }

    @Test
    public void searchAll_shouldReturnMatchingEntities_whenCriteriaMatches() {
        // Arrange
        SearchCriteria<String> criteria = entity -> entity.contains("a");

        // Act
        List<String> results;
        try {
            results = repository.searchAll(criteria);
        } catch (SearchException e) {
            results = null;
        }

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals("Banana", results.get(0));
        assertEquals("Orange", results.get(1));
    }

    @Test
    public void searchAll_shouldReturnEmptyList_whenNoMatchingEntities() {
        // Arrange
        SearchCriteria<String> criteria = entity -> entity.contains("z");

        // Act
        List<String> results;
        try {
            results = repository.searchAll(criteria);
        } catch (SearchException e) {
            results = null;
        }

        // Assert
        assertNotNull(results);
        assertEquals(0, results.size());
    }
}