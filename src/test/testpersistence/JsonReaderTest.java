package testpersistence;

import model.AppHistory;
import model.Meal;
import model.MultipleMeals;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {


    @Test
    void testReaderNonExistentFile() {

        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            AppHistory ah = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderSmallAppHistory() {
        JsonReader reader = new JsonReader("./data/testWriterDemoAppHistory.json");
        try {
            AppHistory bh  = reader.read();
            assertEquals(2000, bh.getDailyCalLimit());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneMealAppHistory() {
        JsonReader reader = new JsonReader("./data/testWriterDemoAppHistory2.json");
        try {
            AppHistory zh  = reader.read();

            assertEquals(1, zh.getMultipleMeals().getAllMeals().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testConvertFoods(){
        JSONArray json = new JSONArray();
        Meal m = new Meal("Breakfast");
        assertEquals(0, m.getFoodItemsInMeal().size());

    }
}
