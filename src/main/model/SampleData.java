package model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Represents a sample data set for food items
public class SampleData {
    private List<FoodItem> foodItems = new ArrayList<>();

    private FoodItem apple = new FoodItem(95, "apple");
    private FoodItem banana = new FoodItem(105, "banana");
    private FoodItem pizza = new FoodItem(1200, "pizza");
    private FoodItem omelette = new FoodItem(100, "omelette");
    private FoodItem cakeSlice = new FoodItem(235, "cake slice");
    private FoodItem cookie = new FoodItem(50, "cookie");
    private FoodItem donut = new FoodItem(250, "donut");
    private FoodItem frenchFries = new FoodItem(365, "french fries");
    private FoodItem iceCream = new FoodItem(137, "ice cream");


    public SampleData() {

        initialize();
    }


    // EFFECTS: Initializes the sample food items registered in the calorie tracker.
    public void initialize() {

        apple.setImage(new ImageIcon("src/main/resources/apple.jpg"));
        banana.setImage(new ImageIcon("src/main/resources/banana.jpg"));
        pizza.setImage(new ImageIcon("src/main/resources/pizza.jpg"));
        omelette.setImage(new ImageIcon("src/main/resources/omelette.jpg"));
        cakeSlice.setImage(new ImageIcon("src/main/resources/cakeSlice.jpg"));
        cookie.setImage(new ImageIcon("src/main/resources/cookie.jpg"));
        donut.setImage(new ImageIcon("src/main/resources/donut.jpg"));
        frenchFries.setImage(new ImageIcon("src/main/resources/frenchFries.jpg"));
        iceCream.setImage(new ImageIcon("src/main/resources/iceCream.jpg"));

        foodItems.add(apple);
        foodItems.add(banana);
        foodItems.add(pizza);
        foodItems.add(omelette);
        foodItems.add(cakeSlice);
        foodItems.add(cookie);
        foodItems.add(donut);
        foodItems.add(frenchFries);
        foodItems.add(iceCream);
    }


    // REQUIRES: Requires the food name to be written exactly
    // EFFECTS: Finds food item by looking for its name and returns it.
    public FoodItem findFoodByName(String name) {

        for (FoodItem f : foodItems) {
            if (f.getName().toLowerCase().equals(name.toLowerCase())) {
                return f;
            }
        }
        return null;
    }

    public List<FoodItem> getFoodItemList() {
        return foodItems;
    }

}
