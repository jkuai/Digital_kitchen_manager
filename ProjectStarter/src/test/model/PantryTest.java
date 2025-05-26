package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PantryTest {
    Pantry pantry;

    @BeforeEach
    void runBefore() {
        pantry = new Pantry();
    }

    @Test
    void testGetPantry() {
        ArrayList<String> compare = new ArrayList<String>();
        assertEquals(compare, pantry.getPantry());
        pantry.add("eggs");
        compare.add("eggs");
        assertEquals(compare, pantry.getPantry());
    }
}
