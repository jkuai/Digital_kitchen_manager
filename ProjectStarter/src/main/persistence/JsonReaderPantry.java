package persistence;

import model.Event;
import model.EventLog;
import model.Pantry;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.json.*;

// Reader that reads recipeBook from JSON to file
public class JsonReaderPantry {
    private String sourcefile;

    //EFFECT: constructs reader to read into source
    public JsonReaderPantry(String sourceFile) {
        this.sourcefile = sourceFile;
    }

    //EFFECT: reads pantry from file and returns it
    // - throws IOException if error in reading data
    public Pantry readPantry() throws IOException {
        String jsonData = readFile(sourcefile);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Pantry Loaded from File."));
        return parsePantry(jsonObject);
    }

    //EFFECT: reads source file
    protected String readFile(String sourceFile) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(s -> builder.append(s));
        }

        return builder.toString();
    }

    //EFFECT: parses pantry from json object and returns it
    private Pantry parsePantry(JSONObject jsonObject) {
        Pantry pantry = new Pantry();
        JSONArray jsonArray = jsonObject.getJSONArray("pantry");
        ArrayList<String> groceries = new ArrayList<String>();

        for (int i = 0; i < jsonArray.length(); i++) {
            groceries.add(jsonArray.get(i).toString());
        }

        pantry.setPantry(groceries);
        return pantry;
    }
}
