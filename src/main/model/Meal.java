package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.ConvertToJson;

import java.util.ArrayList;
import java.util.List;


// Represents a meal with a unique name and list of foodItems which can be added to it
public class Meal implements ConvertToJson {

    private List<FoodItem> foodItems;
    private String name;
    private int caloriesInMeal;

    // EFFECTS: Constructs a meal with currently no foodItems added to it (empty list) and a unique name
    public Meal(String name) {
        foodItems = new ArrayList<>();
        this.name = name;
        this.caloriesInMeal = 0;

    }

    // EFFECTS: Returns the name of the meal
    public String getName() {
        return name;

    }

    // EFFECTS: Sets the name of the meal to the given name
    public void setName(String name) {
        this.name = name;
    }

    // REQUIRES : A single food item cannot have more calories than the total limit set.
    // Also, user should create a meal first, and it should have been added to the list of meals before
    // this method is called.
    // EFFECTS: Adds a food item to the list. Also restricts adding items if it goes over the
    // daily calorie limit.
    public void addFoodItem(FoodItem f, MultipleMeals m, DailyCalorieLimit d) {

        int totalCalsAfterFood = m.getAllMealTotalCalories() + f.getTotalFoodCalories();
        if (!(foodItems.contains(f)) && (totalCalsAfterFood <= d.getCalorieLimit())) {
            foodItems.add(f);
        } else if ((foodItems.contains(f)) && (totalCalsAfterFood <= d.getCalorieLimit())) {
            int quantity = f.getQuantity();
            quantity++;
            f.setQuantity(quantity);
        }
    }

    // EFFECTS:  Removes food item from the list, irrespective of the quantity.
    public void removeFoodItem(FoodItem f) {

        foodItems.remove(f);
        caloriesInMeal -= f.getTotalFoodCalories();

    }

    // EFFECTS: Gets the total number of calories in the meal
    public int getTotalMealCalories() {

        int caloriesInMeal = 0;

        for (FoodItem f : foodItems) {
            caloriesInMeal += f.getTotalFoodCalories();

        }
        return caloriesInMeal;
    }

    public List<FoodItem> getFoodItemsInMeal() {
        return foodItems;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("Name", name);
        json.put("Total calories", caloriesInMeal);
        json.put("Food items", foodsToJson());

        return json;
    }

    // Creates a json array to represent the list of food items in a meal
    private JSONArray foodsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (FoodItem f : getFoodItemsInMeal()) {
            jsonArray.put(f.toJson());
        }

        return jsonArray;
    }
}




