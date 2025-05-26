package persistance;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.RecipeBook;
import model.Review;
import persistence.JsonReader;

class JsonReaderTest extends JsonTest {

    @Test
    void restReaderNonExistantFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            RecipeBook recipeBook = reader.readBook();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyRecipeBook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            RecipeBook recipeBook = reader.readBook();
            assertEquals(0, recipeBook.listRecipes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
        
    }

    @Test
    void testReaderGeneralRecipeBook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralRecipeBook.json");
        try {
            RecipeBook recipeBook = reader.readBook();
            List<String> recipes = recipeBook.listRecipes();
            assertEquals(2, recipes.size());

            ArrayList<String> check1Instruc = new ArrayList<String>();
            check1Instruc.add("m");
            ArrayList<String> check1Ingre = new ArrayList<String>();
            check1Ingre.add("eggs");
            Review r = new Review();
            r.triedBefore();
            checkRecipe("Eggs", check1Ingre, check1Instruc, 9, r, recipeBook.getRecipe(0));

            ArrayList<String> check2Instruc = new ArrayList<String>();
            check2Instruc.add("m");
            ArrayList<String> check2Ingre = new ArrayList<String>();
            check2Ingre.add("eggs");
            Review r2 = new Review();
            checkRecipe("Rice", check2Ingre, check2Instruc, 9, r2, recipeBook.getRecipe(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
