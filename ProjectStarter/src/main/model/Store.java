package model;

import java.util.ArrayList;

//list of available groceries, each can be chosen and added to pantry list
public class Store {
    private ArrayList<String> groceries;

    //EFFECTS: constructs list of all groceries
    public Store() {
        groceries = new ArrayList<String>();

        groceries.add("eggs");
        groceries.add("rice");
        groceries.add("oil");
        groceries.add("toast");
        groceries.add("jam");
    }

    //REQUIRES: i >= groceries.size()
    //take in index and return the grocery at i
    public String findGrocery(int i) {
        return groceries.get(i);
    }

    //EFFECTS: returns list of all groceries
    public ArrayList<String> getGroceries() {
        return groceries;
    }
}
