package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Represents test for App History class
public class AppHistoryTest {

    MultipleMeals mm1;
    AppHistory ah;

    @BeforeEach
    void setup(){
        mm1 = new MultipleMeals();
        ah = new AppHistory(mm1,1500);
    }

    @Test
    void testConstructor(){
        assertEquals(mm1, ah.getMultipleMeals());
        assertEquals(1500, ah.getDailyCalLimit());
    }

    @Test
    void testToJson(){
        JSONObject json = ah.toJson();
        assertEquals(1500, json.getInt("Daily Calorie Limit"));
    }

    @Test
    void testMealsToJson(){
        JSONArray json = ah.mealsToJson();
        assertEquals(0, json.length());
    }

    @Test
    void testGetCalories(){
        assertEquals(1500, ah.getDailyCalLimit());
    }

    @Test
    void testGetMultipleMeals(){
        assertEquals(mm1, ah.getMultipleMeals());
    }
}
