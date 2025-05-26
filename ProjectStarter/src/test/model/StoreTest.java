package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StoreTest {
    Store store;

    @BeforeEach
    void runBefore() {
        store = new Store();
    }

    @Test 
    void testFindGrocery() {
        assertEquals("eggs", store.findGrocery(0));
        assertEquals("rice", store.findGrocery(7));
    }

    @Test 
    void testConstructor() {
        assertEquals(12, store.getGroceries().size());
    }
}
