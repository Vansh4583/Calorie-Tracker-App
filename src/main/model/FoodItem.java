package model;

import org.json.JSONObject;
import persistence.ConvertToJson;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// Represents an individual food item with name and food count.
public class FoodItem implements ConvertToJson {

    private int calories;
    private String name;
    private int quantity;
    private ImageIcon image;

    // EFFECTS: Creates a new instance of a food item with given name and calorie count
    public FoodItem(int calories, String name) {

        this.calories = calories;
        this.name = name;
        this.quantity = 1;
        this.image = null;

    }

    // EFFECTS: Returns the name of the Food
    public String getName() {
        return name;

    }

    // EFFECTS: Sets the name of the food item to the given name
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: Returns the calories of the food
    public int getCalories() {
        return calories;

    }

    // EFFECTS: Sets the calories of the food item to the given number
    public void setCalories(int calories) {
        this.calories = calories;
    }

    // EFFECTS: Gets the number of times a particular foodItem is called
    public int getQuantity() {
        return quantity;
    }

    // EFFECTS: Sets the number of times the foodItem is called to the given number
    public void setQuantity(int quantity) {
        this.quantity = quantity;

    }

    // EFFECTS: Calculate the total calories in the given food item
    public int getTotalFoodCalories() {
        int totalCalories = quantity * calories;

        return totalCalories;
    }

    public void setImage(ImageIcon image) {

        this.image = image;
    }

    public ImageIcon getImage() {
        return image;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        json.put("Name", name);
        json.put("Calories", calories);
        json.put("Quantity", quantity);

        return json;
    }
}