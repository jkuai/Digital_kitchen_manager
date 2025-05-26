package persistance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Pantry;
import persistence.JsonReaderPantry;
import persistence.JsonWriterPantry;

public class JsonWriterPantryTest extends JsonTest{

    @Test 
    void testWriterInvalidFile() {
        try {
            Pantry pantry = new Pantry();
            JsonWriterPantry writer = new JsonWriterPantry("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch(IOException e) {
            //pass
        }
    }

    @Test
    void testWrtierEmptyPantry() {
        try {
            Pantry pantry = new Pantry();
            JsonWriterPantry writer = new JsonWriterPantry("./data/testWriterEmptyPantry.json");
            writer.open();
            writer.write(pantry);
            writer.close();

            JsonReaderPantry reader = new JsonReaderPantry("./data/testWriterEmptyPantry.json");
            pantry = reader.readPantry();
        } catch (IOException e) {
            fail("Exceptions should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPantry() {
        try {
            Pantry pantry = new Pantry();
            pantry.add("oil");
            JsonWriterPantry writer = new JsonWriterPantry("./data/testWriterGeneralPantry.json");
            writer.open();
            writer.write(pantry);
            writer.close();

            JsonReaderPantry reader = new JsonReaderPantry("./data/testWriterGeneralPantry.json");
            pantry = reader.readPantry();
            ArrayList<String> groceries = pantry.getPantry();
            assertEquals(1, groceries.size());

            checkPantry(groceries, pantry);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
