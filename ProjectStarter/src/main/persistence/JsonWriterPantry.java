package persistence;

import java.io.*;

import org.json.JSONObject;

import model.Event;
import model.EventLog;
import model.Pantry;

// Writer that writes Pantry into JSON file
public class JsonWriterPantry {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    //EFFECT: contructs writer to write data into destination file
    public JsonWriterPantry(String destination) {
        this.destination = destination;
    }

    //MODIFIES: this
    //EFFECT: opens writer and throws exception if destination file not found
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    //MODIFIES: this
    //EFFECT: writes JSON representation fo workroom tp file
    public void write(Pantry pantry) {
        JSONObject json = pantry.toJson();
        saveToFile(json.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Pantry Saved to File."));
    }

    //MODIFIES: this
    //EFFECT: closes writer
    public void close() {
        writer.close();
    }

    //MODIFIES: this
    //EFFECT: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
