package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

//a list of recipes
public class RecipeBook implements Writable {
    private ArrayList<Recipe> book;

    //EFFECTS: constructs an empty list of Recipes
    public RecipeBook() {
        book = new ArrayList<Recipe>();
    }

    //EFFECTS: lists out recipe titles in book
    public ArrayList<String> listRecipes() {
        ArrayList<String> listOfNames = new ArrayList<String>();
        for (Recipe r : book) {
            listOfNames.add(r.getTitle());
        }
        return listOfNames; 
    }

    //MODIFIES: this
    //EFFECTS: adds a recipe to list
    public void addRecipe(Recipe r) {
        book.add(r);

        EventLog.getInstance().logEvent(new Event("Recipe Added to Recipe Book."));
    }

    //EFFECTS: return recipe at index i, null if 
    public Recipe getRecipe(int i) {
        return book.get(i);
    }

    //REQUIRES: book list is not empty
    //          i > 0
    //EFFECTS: searches through list for recipe with title of 
    // "recipeTitle", returns page number (index + 1),
    // returns 0 if not found
    public int searchByTitle(String recipeTitle) {
        int index = 0;
        for (Recipe r : book) {
            if (recipeTitle.equals(r.getTitle())) {
                EventLog.getInstance().logEvent(new Event("Searched for: " + recipeTitle + ", was found."));
                return index + 1;
            }
            index++;
        }
        EventLog.getInstance().logEvent(new Event("Searched for: " + recipeTitle + ", was not found."));
        return 0;
    }

    //EFFECT: converts object to JsonObject by assigning keys to values
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("recipes", recipesToJson());
        return json;
    }

    //EFFECT: returm all recipes in this recipe book to json array
    private JSONArray recipesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Recipe r : book) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

}
