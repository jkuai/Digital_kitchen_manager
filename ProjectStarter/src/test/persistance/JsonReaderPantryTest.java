package persistance;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Pantry;
import persistence.JsonReaderPantry;

public class JsonReaderPantryTest extends JsonTest {

    @Test
    void restReaderNonExistantFile() {
        JsonReaderPantry reader = new JsonReaderPantry("./data/noSuchFile.json");
        try {
            Pantry pantry = reader.readPantry();
            fail("IOException expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void testReaderEmptyPantry() {
        JsonReaderPantry reader = new JsonReaderPantry("./data/testReaderEmptyPantry.json");
        try {
            Pantry pantry = reader.readPantry();
            assertEquals(0, pantry.getPantry().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPantry() {
        JsonReaderPantry reader = new JsonReaderPantry("./data/testReaderGeneralPantry.json");
        try {
            Pantry pantry = reader.readPantry();
            ArrayList<String> groceries = pantry.getPantry();
            assertEquals(1, groceries.size());
            checkPantry(groceries, pantry);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
