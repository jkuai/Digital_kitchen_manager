package model;

import java.util.ArrayList;

import org.json.JSONObject;

import persistence.Writable;

//Pantry class that stores all groceries 
public class Pantry implements Writable{
    private ArrayList<String> pantry;
    
    //EFFECTS: constructs empty list of groceries
    public Pantry() {
        pantry = new ArrayList<String>();
    }

    //EFFECTS: returns list of itenerary
    public ArrayList<String> getPantry() {
        return pantry; //stub
    }

    //MODIFIES: this
    //EFFECTS: updates lis of itenrary after a recipe is executed, 
    // or from memory
    public void setPantry(ArrayList<String> newList) {
        pantry = newList;
    }

    //MODITIES: this
    //EFFECTS: adds a string grocery to pantry list
    public void add(String grocery) {
        pantry.add(grocery);
        EventLog.getInstance().logEvent(new Event(grocery + " Added to Pantry."));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("pantry", pantry);

        return json;
    }
}
