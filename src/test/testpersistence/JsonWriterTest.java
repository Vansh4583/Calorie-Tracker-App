package testpersistence;

import model.AppHistory;
import model.Meal;
import model.MultipleMeals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class JsonWriterTest {

    MultipleMeals mm1;
    AppHistory ah;


    @BeforeEach
    void setup() {
        mm1 = new MultipleMeals();
        ah = new AppHistory(mm1, 2000);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void smallAppHistory() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterDemoAppHistory.json");
            writer.open();
            writer.write(ah);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDemoAppHistory.json");
            ah = reader.read();
            assertEquals(2000, ah.getDailyCalLimit());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }

    @Test
    void someAppHistory(){

        try {
            Meal m1 = new Meal("Brunch");
            mm1.addMeal(m1);

            JsonWriter writer = new JsonWriter("./data/testWriterDemoAppHistory2.json");
            writer.open();
            writer.write(ah);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterDemoAppHistory2.json");
            ah = reader.read();
            assertEquals(2000, ah.getDailyCalLimit());
            assertEquals(1, ah.getMultipleMeals().getAllMeals().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

    }

}



