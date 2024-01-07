package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.ConvertToJson;

// Represents the app History of a calorie app which contains the limit set and the meals created so far
public class AppHistory implements ConvertToJson {

    MultipleMeals allMeals;
    Integer dailyCalLimit;

    // Constructor the state of the app, which will have the all the meals and the user's daily calorie limit
    public AppHistory(MultipleMeals m, int dailyCal) {
        allMeals = m;
        dailyCalLimit = dailyCal;

    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("Daily Calorie Limit", dailyCalLimit);
        json.put("All meals", mealsToJson());
        return json;

    }

    // Creates a json array to represent the list of meals
    public JSONArray mealsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Meal m : allMeals.getAllMeals()) {
            jsonArray.put(m.toJson());
        }

        return jsonArray;
    }

    public Integer getDailyCalLimit() {
        return dailyCalLimit;
    }

    public MultipleMeals getMultipleMeals() {
        return allMeals;
    }
}
