package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

// Represents graphical GUI interface for calorie calculator
public class CalorieUI extends JFrame {

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;
    private static final String JSON_STORE = "./data/appHistoryGUI.json";

    private MultipleMeals listOfMeals;
    private SampleData sampleData;
    private DailyCalorieLimit calorieLimit;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private AppHistory ah;
    private JFrame appFrame;
    private JPanel mealsPanel;
    private JPanel featurePanel;


    public CalorieUI() {

        constructFields();
        setFeatures();

    }

    // Constructs fields for Multiple Meals, the main frame of the app, Sample data of food items
    // the daily calorie limit, the two panels for the app, one where meals will be stores and the
    // other for the app features
    public void constructFields() {

        listOfMeals = new MultipleMeals();
        sampleData = new SampleData();
        calorieLimit = new DailyCalorieLimit();

        appFrame = new JFrame("Calorie App");
        appFrame.setSize(WIDTH, HEIGHT);
        appFrame.setLayout(new GridLayout(1, 2));
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mealsPanel = new JPanel(new GridLayout(0, 1));
        appFrame.add(mealsPanel);
        appFrame.setLocationRelativeTo(null);
        featurePanel = new JPanel(new GridLayout(0, 1));
        appFrame.add(featurePanel);


    }


    // Sets all features, such as creating meals, saving app, loading app and exiting app
    public void setFeatures() {
        //viewFoods();
        //setCalories();
        createMeal();
        //removeMeal();
        saveApp();
        loadApp();
        exitApp();
        appFrame.setVisible(true);

    }

    // Handles the creation of a button which creates a meal, and adds it to the app
    public void createMeal() {

        JButton createMealButton = new JButton("Create Meal");

        createMealButton.addActionListener(e -> {
            String mealName = JOptionPane.showInputDialog("Enter meal name:");
            if (mealName != null) {
                Meal m1 = new Meal(mealName);
                JTextArea mealInfo = new JTextArea();
                mealInfo.setEditable(false);
                mealInfo.setLineWrap(true);
                mealInfo.setWrapStyleWord(true);

                listOfMeals.getAllMeals().add(m1);
                displayMeals(m1, mealInfo);
            }
        });

        featurePanel.add(createMealButton);
    }

    // Creates a panel for the meal that we created
    public void displayMeals(Meal meal, JTextArea mealInfo) {

        JButton mealButton = new JButton(meal.getName());
        mealButton.addActionListener(e -> mealFeatures(meal, mealInfo));

        mealsPanel.add(mealButton);
        appFrame.revalidate();
        appFrame.repaint();

    }

    // Creates a frame which has an add food button and a text area which shows which food items
    // are there in the meal and the total calories in the meal
    public void mealFeatures(Meal m, JTextArea mealInfo) {
        JFrame mealFrame = new JFrame(m.getName() + " : " + String.valueOf(m.getTotalMealCalories()) + " kcal");
        mealFrame.setSize(500, 400);
        mealFrame.setLayout(new GridLayout(0, 2));

        JPanel mealFunctions = new JPanel(new GridLayout(0, 1));

        JButton addFoods = new JButton("Add food items");
        addFoods.addActionListener(e -> addFoodItems(m, mealInfo));

        JButton removeFoods = new JButton("Remove food items");
        removeFoods.addActionListener(e -> removeFoodItems(m, mealInfo));

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> {
            mealFrame.dispose();
        });
        mealFunctions.add(addFoods);
        mealFunctions.add(removeFoods);
        mealFunctions.add(okButton);
        mealFrame.add(mealFunctions);

        mealInfo.setText("");
        for (FoodItem f : m.getFoodItemsInMeal()) {
            mealInfo.append(foodToInfo(f));
        }
        mealFrame.add(mealInfo);
        mealFrame.setLocationRelativeTo(null);
        mealFrame.setVisible(true);
    }

    // Creates a jdialog which has all the food items available.
    // User can select which food items to add, following which the text area in the meal
    // frame will get updated. The food items are displayed along with their image, name and calories
    public void addFoodItems(Meal meal, JTextArea area) {
        JDialog foodOptions = new JDialog();
        foodOptions.setTitle("Select foods");
        List<FoodItem> foodItemList = sampleData.getFoodItemList();

        JPanel foodPanel = new JPanel(new GridLayout(0, 3));
        for (FoodItem f : foodItemList) {
            JButton nameButton = new JButton(f.getName());
            nameButton.addActionListener(e -> {
                area.append(f.getName() + " x1"
                        + " - " + f.getCalories() + " calories\n");

                meal.addFoodItem(f, listOfMeals, calorieLimit);
            });


            JLabel calorieLabel = new JLabel(String.valueOf(f.getCalories()));
            calorieLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon foodImage = scaleImage(f);
            JLabel imageLabel = new JLabel(foodImage);

            imageLabel.setSize(30, 30);
            foodPanel.add(imageLabel);
            foodPanel.add(nameButton);
            foodPanel.add(calorieLabel);
        }
        addFoodItemsExitFunctionality(foodOptions, foodPanel);
    }

    // Creates an ok button which when clicked, collapses the jdialog window for add food items
    public void addFoodItemsExitFunctionality(JDialog dialog, JPanel foodPanel) {
        JButton okButton = new JButton("Ok");
        okButton.addActionListener(e -> {
            dialog.dispose();
        });
        dialog.setLayout(new BorderLayout());
        dialog.add(foodPanel, BorderLayout.CENTER);
        dialog.add(okButton, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setSize(600, 600);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    // Creates a jdialog which has buttons for all food items in the meal. If any button is clicked
    // the corresponding food item will be removed from the meal as well the information area of the meal
    public void removeFoodItems(Meal m, JTextArea info) {

        Dialog removeFoodsDialog = new JDialog();
        removeFoodsDialog.setTitle("Remove Foods");
        JPanel removeFoodsPanel = new JPanel(new GridLayout(0, 1));

        for (FoodItem f : m.getFoodItemsInMeal()) {
            JButton removeButton = new JButton("Remove " + f.getName());
            removeButton.addActionListener(e -> {
                m.removeFoodItem(f);
                removeFoodFromInformationArea(m, info, f);
                removeFoodsDialog.dispose();
            });
            removeFoodsPanel.add(removeButton);

        }
        removeFoodsDialog.add(removeFoodsPanel);
        removeFoodsDialog.setSize(300, 200);
        removeFoodsDialog.setLocationRelativeTo(null);
        removeFoodsDialog.setVisible(true);

    }


    // Removes food information from the textarea of the meal completely, regardless of quantity
    public void removeFoodFromInformationArea(Meal m, JTextArea area, FoodItem f) {
        String currentText = area.getText();
        String foodInfoToRemove = foodToInfo(f);
        String newText = currentText.replace(foodInfoToRemove, "").replaceAll("\\s+", " ");
        area.setText(newText);

//        String trimmedText = area.getText();
//        trimmedText.trim();
//        area.setText((trimmedText));
    }


    // Creates a button which saves the current state of the app
    public void saveApp() {

        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(e -> {
            try {
                jsonWriter = new JsonWriter(JSON_STORE);
                ah = new AppHistory(listOfMeals, calorieLimit.getCalorieLimit());
                jsonWriter.open();
                jsonWriter.write(ah);
                jsonWriter.close();
                System.out.println("Saved app history to " + JSON_STORE);
            } catch (IOException m) {
                //
            }
        });

        featurePanel.add(saveButton);
    }

    // Creates a button which loads the previous state of the app
    public void loadApp() {
        JButton loadButton = new JButton("Load app");

        loadButton.addActionListener(e -> {
            try {
                jsonReader = new JsonReader(JSON_STORE);
                ah = jsonReader.read();
                listOfMeals = ah.getMultipleMeals();
                calorieLimit.setCalorieLimit(ah.getDailyCalLimit());
                mealsPanel.removeAll();

                appHistoryButtonsArea();
            } catch (IOException m) {
                //
            }
        });

        featurePanel.add(loadButton);
    }

    // Creates buttons and appropriate text areas for previous app history
    public void appHistoryButtonsArea() {
        for (Meal m : listOfMeals.getAllMeals()) {
            JTextArea previousMealsInfo = new JTextArea();
            for (FoodItem f : m.getFoodItemsInMeal()) {
                previousMealsInfo.append(foodToInfo(f));
            }
            previousMealsInfo.setEditable(false);
            previousMealsInfo.setLineWrap(true);
            previousMealsInfo.setWrapStyleWord(true);
            displayMeals(m, previousMealsInfo);
        }
    }

    // Creates button for exiting the app
    public void exitApp() {
        JButton exitButton = new JButton("Exit");

        exitButton.addActionListener(e -> {
            appFrame.dispose();
        });

        featurePanel.add(exitButton);
    }

    // Creates a scaled down version of the image of the food item
    public ImageIcon scaleImage(FoodItem f) {
        ImageIcon originalIcon = f.getImage();

        int width = 85; // New width
        int height = 75; // New height


        Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        return scaledIcon;
    }

    // Returns a string which has the food item name its quanity and its calories in order
    public String foodToInfo(FoodItem f) {

        String foodInfo = f.getName() + " x" + String.valueOf(f.getQuantity())
                + " - " + f.getCalories() + " calories\n";
        return foodInfo;
    }



}




