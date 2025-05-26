package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecipeTest {
    Recipe recipe1;
    String r1Title = "Fried Egg";
    ArrayList<String> r1Ingrediants = new ArrayList<String>();
    String ingrediant1 = "egg";
    String ingrediant2 = "oil";
    ArrayList<String> r1Instructions = new ArrayList<String>();
    String step1 = "put oil in pan";
    String step2 = "add egg";
    String step3 = "fry both sides until slightly brown";
    int timeOfRecipe = 5;
    Review review;

    @BeforeEach
    void runBefore() {
        r1Ingrediants.add(ingrediant1);
        r1Ingrediants.add(ingrediant2);
        r1Instructions.add(step1);
        r1Instructions.add(step2);
        r1Instructions.add(step3);
        recipe1 = new Recipe(r1Title, r1Ingrediants, 
                            r1Instructions, timeOfRecipe);
        review = new Review();
    }

    @Test
    void testConstructor() {
        assertEquals("Fried Egg", recipe1.getTitle());
        assertEquals(r1Ingrediants, recipe1.getIngrediants());
        assertEquals(r1Instructions, recipe1.getInstructions());
        assertEquals(5, recipe1.getTime());
        Review compare = recipe1.getReview();
        assertEquals(review.getIsTried(), compare.getIsTried());
        assertEquals(review.getReview(), compare.getReview());
        assertEquals(review.getDateOfReview(), compare.getDateOfReview());
    }

    @Test
    void testUpdateReview() {
        review.triedBefore();
        review.updateReview("good");
        review.updateDate("sept 1, 2024");
        recipe1.updateReview("good", "sept 1, 2024");
        Review compare = recipe1.getReview();
        assertEquals(review.getIsTried(), compare.getIsTried());
        assertEquals(review.getReview(), compare.getReview());
        assertEquals(review.getDateOfReview(), compare.getDateOfReview());
    }

    @Test
    void testExecute() {
        Pantry pantry = new Pantry();
        pantry.add("kimchi");
        pantry.add("oil");
        pantry.add("egg");
        recipe1.execute(pantry);
        Pantry after = recipe1.execute(pantry);
        ArrayList<String> compare = new ArrayList<String>();
        compare.add("kimchi");
        assertEquals(after.getPantry(), compare);
    }
}