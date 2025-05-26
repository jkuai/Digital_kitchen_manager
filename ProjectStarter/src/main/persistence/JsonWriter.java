package persistence;

import java.io.*;

import org.json.JSONObject;

import model.Event;
import model.EventLog;
import model.RecipeBook;

// Writer that writes Recipebook into JSON file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECT: consturcts writer to write data into destination file
    public JsonWriter(String desitnation) {
        this.destination = desitnation;
    }

    //MODIFIES: this
    //EFFECT: opens writer and throws exception if destination file not found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECTS: writes JSON representaion of workroom to file
    public void write(RecipeBook recipeBook) {
        JSONObject json = recipeBook.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Recipe Book Saved to File."));
    }

    //MODIFES: this
    //EFFECTRS: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
