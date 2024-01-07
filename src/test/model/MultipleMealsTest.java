package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MultipleMealsTest {

    MultipleMeals multipleMeals1;
    Meal m1;
    Meal m2;
    FoodItem f1;
    FoodItem f2;
    DailyCalorieLimit d1;

    @BeforeEach
    public void setup(){
        multipleMeals1 = new MultipleMeals();
        m1 = new Meal("Breakfast");
        m2 = new Meal("Lunch");
        f1 = new FoodItem(500, "Watermelon");
        f2 = new FoodItem(600,"Burger");
        d1 = new DailyCalorieLimit();
        d1.setCalorieLimit(1200);

    }

    @Test
    public void constructorTest(){
        assertTrue(multipleMeals1.getAllMeals().isEmpty());
    }

    @Test
    public void testAddSingleMeal(){
        multipleMeals1.addMeal(m1);
        assertEquals(1,multipleMeals1.getAllMeals().size());
        assertEquals("Breakfast",multipleMeals1.getAllMeals().get(0).getName());

    }

    @Test
    public void testAddMultipleMeals(){
        multipleMeals1.addMeal(m1);
        multipleMeals1.addMeal(m2);
        assertEquals(2,multipleMeals1.getAllMeals().size());
        assertEquals("Lunch",multipleMeals1.getAllMeals().get(1).getName());

    }

    @Test
    public void testRemoveMeal(){
        multipleMeals1.addMeal(m1);
        multipleMeals1.removeMeal("Breakfast");
        assertEquals(0, multipleMeals1.getAllMeals().size());

    }

    @Test
    public void testRemoveMealButCantFind(){
        multipleMeals1.addMeal(m1);
        multipleMeals1.removeMeal("BreakFAST");
        assertEquals(1, multipleMeals1.getAllMeals().size());

    }

    @Test
    public void testRemoveMealWhenAlreadyRemoved(){
        multipleMeals1.removeMeal("Lunch");
        assertEquals(0, multipleMeals1.getAllMeals().size());

    }

    @Test
    public void testTotalMealCalsSameFood(){
        multipleMeals1.addMeal(m1);
        m1.addFoodItem(f1, multipleMeals1, d1);
        assertEquals(500,multipleMeals1.getAllMealTotalCalories());

        m1.addFoodItem(f1, multipleMeals1, d1);
        assertEquals(1000,multipleMeals1.getAllMealTotalCalories());
    }

    @Test
    public void testTotalMealCalsDiffFoodItems(){
        multipleMeals1.addMeal(m1);
        m1.addFoodItem(f1, multipleMeals1, d1);
        assertEquals(500,multipleMeals1.getAllMealTotalCalories());

        m1.addFoodItem(f2, multipleMeals1, d1);
        assertEquals(1100,multipleMeals1.getAllMealTotalCalories());
    }

    @Test
    public void testFindMealByName(){

        multipleMeals1.addMeal(m1);

        assertEquals("Breakfast", multipleMeals1.findMealByName("Breakfast").getName());

        assertNull(multipleMeals1.findMealByName("LUNch"));
    }
}
