package model;

import org.json.JSONObject;
import persistence.ConvertToJson;
import persistence.JsonWriter;

// Represents the daily calorie limit set by the user
public class DailyCalorieLimit {

    private int calorieLimit;

    // EFFECTS: Creates a new instance of daily calorie limit being 2000. User can modify it
    // to his/her liking
    public DailyCalorieLimit() {

        this.calorieLimit = 2000;
    }

    public int getCalorieLimit() {
        return calorieLimit;
    }

    public void setCalorieLimit(int calorieLimit) {
        this.calorieLimit = calorieLimit;
    }

}
