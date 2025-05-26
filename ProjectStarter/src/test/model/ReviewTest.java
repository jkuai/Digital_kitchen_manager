package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ReviewTest {
    Review review;

    @BeforeEach
    void runBefore() {
        review = new Review();
    }

    @Test
    void testConstructor() {
        assertFalse(review.getIsTried());
        assertEquals("", review.getReview());
        assertEquals("", review.getDateOfReview());
    }

    @Test 
    void testUpdatingReview() {
        assertFalse(review.getIsTried());
        review.triedBefore();
        assertTrue(review.getIsTried());
        review.updateReview("is very good");
        assertEquals("is very good", review.getReview());
        review.updateDate("oct 3, 2024");
        assertEquals("oct 3, 2024", review.getDateOfReview());
    }
}
