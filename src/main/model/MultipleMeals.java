package model;

import org.json.JSONArray;
import persistence.ConvertToJson;

import java.util.ArrayList;
import java.util.List;

// Represents a list of all the custom meals the user has created
public class MultipleMeals {

    private List<Meal> allMeals;

    // EFFECTS: Creates a list of all meals the user has created
    public MultipleMeals() {
        allMeals = new ArrayList<>();
    }

    // REQUIRES: Same meal cannot be added twice
    // EFFECTS: Adds a custom meal.
    public void addMeal(Meal meal) {
        allMeals.add(meal);
    }

    // REQUIRES: Requires meal name to be written exactly
    // EFFECTS: Removes a meal from the list.
    public void removeMeal(String meal) {

        for (Meal m : allMeals) {
            if (m.getName().equals(meal)) {

                allMeals.remove(m);
                break;
            }
        }

    }

    // EFFECTS: Calculates total calories in all the meals so far
    public int getAllMealTotalCalories() {

        int totalCalories = 0;

        for (Meal m : allMeals) {
            totalCalories += m.getTotalMealCalories();

        }
        return totalCalories;
    }

    // REQUIRES: Requires meal name to be written exactly
    // EFFECTS: Takes string and returns the meal which has that name
    public Meal findMealByName(String name) {
        for (Meal m : allMeals) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        return null;
    }

    // EFFECTS: returns allMeals
    public List<Meal> getAllMeals() {
        return allMeals;
    }


}

