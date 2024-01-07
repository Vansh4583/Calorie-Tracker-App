package persistence;


import model.AppHistory;
import model.FoodItem;
import model.Meal;
import model.MultipleMeals;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader which reads the Calorie App from json data stored in file
// Code referenced from JsonSerializationDemo Starter
public class JsonReader {

    private String source;

    // Constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public AppHistory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAppHistory(jsonObject);
    }

    // Reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();

    }

    // Parses app history
    private AppHistory parseAppHistory(JSONObject jsonObject) {
        Integer dailyCal = jsonObject.getInt("Daily Calorie Limit");
        JSONArray jsonArray = jsonObject.getJSONArray("All meals");

        MultipleMeals multipleMeals = convertArrayToMultipleMeals(jsonArray);
        AppHistory ah = new AppHistory(multipleMeals, dailyCal);
        return ah;
    }

    // Converts the JsonArray we have into a MultipleMeals instance
    private MultipleMeals convertArrayToMultipleMeals(JSONArray jsonArray) {

        MultipleMeals allMeals = new MultipleMeals();
        for (Object obj : jsonArray) {

            Meal m = convertJsonToMeal(obj);
            allMeals.addMeal(m);
        }
        return allMeals;
    }

    // Creates a new meal from each Json Array object
    private Meal convertJsonToMeal(Object obj) {
        JSONObject meal = (JSONObject) obj;
        String name = meal.getString("Name");
        meal.getInt("Total calories");
        JSONArray jsonArray = meal.getJSONArray("Food items");

        Meal meal1 = new Meal(name);
        convertFoodsToJson(jsonArray, meal1);
        return meal1;

    }

    // Converts the objects of the objects of the array into food items and adds them to the meal
    private void convertFoodsToJson(JSONArray jsonArray, Meal m) {

        for (Object obj : jsonArray) {
            JSONObject json = (JSONObject) obj;
            String name = json.getString("Name");
            int calories = json.getInt("Calories");
            int quantity = json.getInt("Quantity");

            FoodItem f = new FoodItem(calories, name);
            f.setQuantity(quantity);

            m.getFoodItemsInMeal().add(f);

        }

    }


}
