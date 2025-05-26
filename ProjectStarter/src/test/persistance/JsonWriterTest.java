package persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Recipe;
import model.RecipeBook;
import model.Review;
import persistence.JsonReader;
import persistence.JsonWriter;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            RecipeBook recipeBook = new RecipeBook();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testWriterEmptyRecipeBook() {
        try {
            RecipeBook recipeBook = new RecipeBook();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyRecipeBook.json");
            writer.open();
            writer.write(recipeBook);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyRecipeBook.json");
            recipeBook = reader.readBook();
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    Recipe runBefore() {
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
    
        r1Ingrediants.add(ingrediant1);
        r1Ingrediants.add(ingrediant2);
        r1Instructions.add(step1);
        r1Instructions.add(step2);
        r1Instructions.add(step3);
        recipe1 = new Recipe(r1Title, r1Ingrediants, 
                            r1Instructions, timeOfRecipe);
        review = new Review();

        return recipe1;
    }

    @Test 
    void testWrtierGeneralRecipeBook() {
        try {
            RecipeBook recipeBook = new RecipeBook();
            Recipe r = runBefore();
            recipeBook.addRecipe(r);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralRecipeBook.json");
            writer.open();
            writer.write(recipeBook);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralRecipeBook.json");
            recipeBook = reader.readBook();
            ArrayList<String> recipes = recipeBook.listRecipes();
            assertEquals(1, recipes.size());

            checkRecipe(r.getTitle(), r.getIngrediants(), r.getInstructions(), 
                        r.getTime(), r.getReview(), recipeBook.getRecipe(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
