package persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import model.Pantry;
import model.Recipe;
import model.Review;

public class JsonTest {
    protected void checkRecipe(String title, ArrayList<String> ingrediants, ArrayList<String> instructions, 
                                int timeNeeded, Review review, Recipe recipe) {
        assertEquals(title, recipe.getTitle());
        assertEquals(ingrediants, recipe.getIngrediants());
        assertEquals(instructions, recipe.getInstructions());
        assertEquals(timeNeeded, recipe.getTime());
        assertEquals(review.getReview(), recipe.getReview().getReview());
        assertEquals(review.getIsTried(), recipe.getReview().getIsTried());
        assertEquals(review.getDateOfReview(), recipe.getReview().getDateOfReview());
    }

    protected void checkPantry(ArrayList<String> groceries, Pantry pantry) {
        assertEquals(groceries, pantry.getPantry());
    }
}
