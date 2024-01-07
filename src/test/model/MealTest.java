package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MealTest {

    Meal m1;
    FoodItem f1;
    FoodItem f2;
    MultipleMeals multipleMeals;
    DailyCalorieLimit d1;
    DailyCalorieLimit d2;

    @BeforeEach
    public void setup(){

        m1 = new Meal("Breakfast");

        f1 = new FoodItem(500, "Watermelon");
        f2 = new FoodItem(600,"Burger");

        multipleMeals = new MultipleMeals();
        multipleMeals.addMeal(m1);

        d1 = new DailyCalorieLimit();
        d1.setCalorieLimit(1200);

        d2 = new DailyCalorieLimit();
        d2.setCalorieLimit(700);
    }

    @Test
    public void testConstructor(){

        assertEquals("Breakfast", m1.getName());
        assertEquals(0,m1.getTotalMealCalories());
    }

    @Test
    public void testAddFoodItem(){
        m1.addFoodItem(f1, multipleMeals, d1);

        List<FoodItem> success = m1.getFoodItemsInMeal();
        assertEquals(1, success.size());

        assertEquals("Watermelon", success.get(0).getName());

    }

    @Test
    public void testAddMultipleFoodItemWithinLimit(){
        m1.addFoodItem(f1, multipleMeals, d1);
        m1.addFoodItem(f2, multipleMeals, d1);

        List<FoodItem> success = m1.getFoodItemsInMeal();
        assertEquals(2, success.size());

        assertEquals("Burger", success.get(1).getName());
        assertEquals(1100, m1.getTotalMealCalories());

    }

    @Test
    public void testSameFoodQuantityIncrease(){
        m1.addFoodItem(f1, multipleMeals, d1);
        m1.addFoodItem(f1, multipleMeals, d1);

        List<FoodItem> success = m1.getFoodItemsInMeal();

        assertEquals(2, success.get(0).getQuantity());
        assertEquals(1000, success.get(0).getTotalFoodCalories());

    }

    @Test
    public void testAddMultipleFoodItemExceedsLimit(){
        m1.addFoodItem(f1, multipleMeals, d2);
        m1.addFoodItem(f1, multipleMeals, d2);

        List<FoodItem> success = m1.getFoodItemsInMeal();

        assertEquals(1, success.size());
        assertEquals(500, m1.getTotalMealCalories());



    }

    @Test
    public void testRemoveFoodItemSingleQuantity(){
        m1.addFoodItem(f1, multipleMeals, d1);
        m1.removeFoodItem(f1);

        assertTrue(m1.getFoodItemsInMeal().isEmpty());
    }

    @Test
    public void testRemoveFoodItemMultipleQuantity(){
        m1.addFoodItem(f1, multipleMeals, d1);
        m1.addFoodItem(f1, multipleMeals, d1);
        m1.removeFoodItem(f1);

        assertTrue(m1.getFoodItemsInMeal().isEmpty());
    }

    @Test
    public void testGetTotalCals(){

        m1.addFoodItem(f1, multipleMeals, d1);
        assertEquals(500, m1.getTotalMealCalories());

        m1.addFoodItem(f2, multipleMeals, d1);


        assertEquals(1100, m1.getTotalMealCalories());

    }

    @Test
    public void testSetter(){
        m1.setName("Dinner");
        assertEquals("Dinner",m1.getName());
    }
}
