package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

//Represents a single recipe 
public class Recipe implements Writable {

    private String title;
    private ArrayList<String> ingrediants;
    private ArrayList<String> instructions;
    private int timeNeeded;
    private Review isGood;

    //EFFECT: contructs a recipe with given title t, ingrediant list, 
    // instructions and time needed to complete
    // sets current review to "not yet made"
    public Recipe(String title, ArrayList<String> ingrediants,
                  ArrayList<String> instructions, int timeNeeded) {
        this.title = title;
        this.ingrediants = ingrediants;
        this.instructions = instructions;
        this.timeNeeded = timeNeeded;
        isGood = new Review();
    }

    //EFFECTS: returns name of recipe
    public String getTitle() {
        return title;
    }

    //EFFECTS: return list of ingrediants needed
    public ArrayList<String> getIngrediants() {
        return ingrediants;
    }

    //EFFECTS: return list of intructions
    public ArrayList<String> getInstructions() {
        return instructions;
    }

    //EFFECTS: return time needed/left to complete recipe
    public int getTime() {
        return timeNeeded;
    }

    //EFFECTS: returns current review status
    public Review getReview() {
        return isGood;
    }

    //MODIFIES: this
    //EFFECTS: updates review of recipe
    public void updateReview(String review, String date) {
        isGood.triedBefore();
        isGood.updateReview(review);
        isGood.updateDate(date);

        EventLog.getInstance().logEvent(new Event("Review added to: " + this.title));
    }

    //MODIFIES: this
    //EFFECTS: updates review of recipe with given review
    public void updateReview(Review newReview) {
        isGood = newReview;
        
        EventLog.getInstance().logEvent(new Event("Review added to: " + this.title));
    }

    //REQUIRES: pantry has all ingrediants needed to execute 
    //MODIFIES: this, pantry
    //EFFECTS: executes recipe r - removes used ingredients from 
    //pantry, returns new pantry
    public Pantry execute(Pantry pantry) {
        ArrayList<String> ingrediantsHave = pantry.getPantry();
        for (String s : getIngrediants()) {
            int index = 0;
            for (String used : ingrediantsHave) {
                if (used.equals(s)) {
                    ingrediantsHave.remove(index);
                    break;
                }
                index++;
            }
        }
        pantry.setPantry(ingrediantsHave);
        EventLog.getInstance().logEvent(new Event(this.title + " was executed."));
        return pantry;
    }

    //EFFECT: converts object to JsonObject by assigning keys to values
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("ingrediants", ingrediants);
        json.put("instructions", instructions);
        json.put("timeNeeded", timeNeeded);
        json.put("review", isGood.toJson());

        return json;
    }
}
