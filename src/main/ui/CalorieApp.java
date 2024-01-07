package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.Scanner;

// Calorie Tracker Application
public class CalorieApp {

    private static final String JSON_STORE = "./data/appHistory.json";
    private MultipleMeals allMeals = new MultipleMeals();
    private DailyCalorieLimit dailyCalorieLimit = new DailyCalorieLimit();
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private AppHistory ah;

    //EFFECTS: runs the application
    public CalorieApp() {
        runCalorieApp();
    }

    // EFFECTS: processes user input
    public void runCalorieApp() {
        boolean keepGoing = true;
        Scanner scanner = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            int choice = scanner.nextInt();
            if (choice == 7) {
                keepGoing = false;
            } else {
                processChoice(choice);
            }
        }
        System.out.println("\nGoodbye");
    }


    // EFFECTS: Displays the menu of options that the user has
    public void displayMenu() {

        System.out.println("\nCalorie Tracker App");
        System.out.println("1. View Food Items");
        System.out.println("2. Meal Features");
        System.out.println("3. Set Daily Calorie Goal");
        System.out.println("4. View Meals");
        System.out.println("5. Save Data");
        System.out.println("6. Load data");
        System.out.println("7. Exit");
        System.out.println("Enter your choice: ");
    }


    // EFFECTS: Process the choice inputted by the user
    public void processChoice(int i) {
        if (i == 1) {
            viewFoodItems();
        } else if (i == 2) {
            mealFeatures();
        } else if (i == 3) {
            setCalorieLimit();
        } else if (i == 4) {
            viewMeals();
        } else if (i == 5) {
            saveData();
        } else if (i == 6) {
            loadData();
        } else {
            System.out.println("Invalid input");
        }
    }


    // EFFECTS: Displays all the food items to the user
    public void viewFoodItems() {
        SampleData sample = new SampleData();

        for (FoodItem f : sample.getFoodItemList()) {
            String name = f.getName();
            int calories = f.getCalories();
            System.out.println(name + "," + calories);
        }
    }


    // EFFECTS: Creates a meal and implements the features related to it
    public void mealFeatures() {
        Scanner scanner = new Scanner(System.in);

        boolean keepGoing = true;
        while (keepGoing) {
            displayMealMenu();
            int choice = scanner.nextInt();

            if (choice == 1) {
                createMeal();
            } else if (choice == 2) {
                removeMeal();
            } else if (choice == 3) {
                addFoodItem();
            } else if (choice == 4) {
                removeFoodItem();
            } else if (choice == 5) {
                keepGoing = false;
            }
        }

    }

    // EFFECTS: Displays the features related to what the user can do with a  meal
    public void displayMealMenu() {
        System.out.println("1. Create Meal");
        System.out.println("2. Remove Meal");
        System.out.println("3. Add food item (Create a meal first)");
        System.out.println("4. Remove food item (You need to have created a meal first)");
        System.out.println("5. Exit");
        System.out.println("Enter your choice: ");

    }


    // EFFECTS: Handles creating a meal
    public void createMeal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the meal name: ");
        String name = scanner.next();

        Meal userMeal = new Meal(name);
        allMeals.addMeal(userMeal);
    }

    // REQUIRES: Meal name and food name should be written exactly with no errors
    // EFFECTS: Handles removing a meal.
    public void removeMeal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the EXACT name of meal you wish to remove: ");
        String name = scanner.next();

        allMeals.removeMeal(name);

    }

    // REQUIRES: Meal name and food name should be written exactly with no errors
    // EFFECTS: Handles removing a food item from a user meal.
    public void removeFoodItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the EXACT name of the food item you want to remove: ");
        String name = scanner.next().toLowerCase();

        SampleData sample = new SampleData();
        FoodItem requiredFood = sample.findFoodByName(name);

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter the EXACT name of the meal you want to remove the item from: ");
        String name2 = scanner2.next();

        Meal requiredMeal = allMeals.findMealByName(name2);

        requiredMeal.removeFoodItem(requiredFood);

    }

    // REQUIRES: User should have created a meal first
    // EFFECTS: Handles adding a food item.
    public void addFoodItem() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the EXACT name of the food item you want to add: ");
        String name = scanner.next().toLowerCase();

        SampleData sample = new SampleData();
        FoodItem requiredFood = sample.findFoodByName(name);

        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Enter the EXACT name of the meal you want to add the item to: ");
        String name2 = scanner2.next();

        Meal requiredMeal = allMeals.findMealByName(name2);

        requiredMeal.addFoodItem(requiredFood, allMeals, dailyCalorieLimit);
        int totalCalsAfterFood = allMeals.getAllMealTotalCalories() + requiredFood.getTotalFoodCalories();
        if (totalCalsAfterFood > dailyCalorieLimit.getCalorieLimit()) {
            System.out.println("Adding that will exceed your calorie limit!!");
        }

    }

    // REQUIRES: The daily calorie limit should be >= 0
    // EFFECTS: Sets the daily calorie limit
    public void setCalorieLimit() {
        System.out.print("Enter your calorie limit: ");
        Scanner scanner = new Scanner(System.in);
        int limit = scanner.nextInt();

        dailyCalorieLimit.setCalorieLimit(limit);
        System.out.println("Your new calorie limit for the day is " + limit + " calories");

        int caloriesRemaining = limit - allMeals.getAllMealTotalCalories();
        System.out.println("Number of calories left: " + caloriesRemaining);
    }

    // User can view the meals and what all food items it has along with total cals of each meal
    public void viewMeals() {
        String stringCalorie = Integer.toString(dailyCalorieLimit.getCalorieLimit());
        System.out.println("Limit -> " + stringCalorie);
        for (Meal m : allMeals.getAllMeals()) {
            String mealName = m.getName();
            int totalCals = m.getTotalMealCalories();

            StringBuilder mealFoodNames = new StringBuilder("(");
            for (int i = 0; i < m.getFoodItemsInMeal().size(); i++) {
                String foodName = m.getFoodItemsInMeal().get(i).getName();
                mealFoodNames.append(foodName);

                if (i < m.getFoodItemsInMeal().size() - 1) {
                    mealFoodNames.append(",");
                }
            }
            mealFoodNames.append(")");
            System.out.println(mealName + " -> Calorie Count: " + totalCals + " " + mealFoodNames);

        }

    }

    // User can save their data (the list of all meals they have created and their calorie limit)
    public void saveData() {
        try {
            jsonWriter = new JsonWriter(JSON_STORE);
            ah = new AppHistory(allMeals, dailyCalorieLimit.getCalorieLimit());
            jsonWriter.open();
            jsonWriter.write(ah);
            jsonWriter.close();
            System.out.println("Saved app history to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to save file");
        }

    }

    // User can load their saved data
    public void loadData() {
        try {
            jsonReader = new JsonReader(JSON_STORE);
            ah = jsonReader.read();
            allMeals = ah.getMultipleMeals();
            dailyCalorieLimit.setCalorieLimit(ah.getDailyCalLimit());

            System.out.println("Loaded app history from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to load file");
        }
    }


}
