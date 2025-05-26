package persistence;

import model.Event;
import model.EventLog;
import model.Recipe;
import model.RecipeBook;
import model.Review;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Reader that reads recipeBook from JSON in file
public class JsonReader {
    private String sourceFile;

    //EFFECTS: contructs reader to read into source
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    //EFFECT: reads recipebook from file and returns it
    // - throws IQException if error in reading data
    public RecipeBook readBook() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);

        EventLog.getInstance().logEvent(new Event("Recipe Book Loaded from File."));
        return parseRecipeBook(jsonObject);
    }

    //EFFECT: reads source file
    protected String readFile(String sourceFile) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }

        return builder.toString();
    }

    //EFFECT: parses recipebook from json object and returns it
    private RecipeBook parseRecipeBook(JSONObject jsonObject) {
        RecipeBook recipeBook = new RecipeBook();
        addRecipes(recipeBook, jsonObject);
        return recipeBook;
    }

    //EFFECT: parses recipes from json object and adds to recipeBook
    private void addRecipes(RecipeBook recipeBook, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("recipes");
        for (Object json : jsonArray) {
            JSONObject nextRecipe = (JSONObject) json;
            addRecipe(recipeBook, nextRecipe);
        }
    }

    //MODIFIES: recipeBook
    //EFFECT: Recreates each recipe in recipe book
    private void addRecipe(RecipeBook recipeBook, JSONObject jsonObject) {
        String title = jsonObject.getString(("title"));

        JSONArray jsonIngrediants = jsonObject.getJSONArray("ingrediants");
        ArrayList<String> ingrediants = new ArrayList<String>();
        for (int i = 0; i < jsonIngrediants.length(); i++) {
            ingrediants.add(jsonIngrediants.get(i).toString());
        }

        JSONArray jsonInstructions = jsonObject.getJSONArray("instructions");
        ArrayList<String> instructions = new ArrayList<String>();
        for (int i = 0; i < jsonInstructions.length(); i++) {
            instructions.add(jsonInstructions.get(i).toString());
        }      
        
        int timeNeeded = jsonObject.getInt("timeNeeded");
        
        Review review = new Review();
        JSONObject r = jsonObject.getJSONObject("review");
        JsonReaderReview newReview = new JsonReaderReview(sourceFile);

        review = newReview.parseReview(r);
        Recipe recipe = new Recipe(title, ingrediants, instructions, timeNeeded);
        recipe.updateReview(review);
        recipeBook.addRecipe(recipe);
    }
}
